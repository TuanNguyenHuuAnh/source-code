/*******************************************************************************
 * Class        ActDocWorkflowListenerImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhoaNA
 * Change log   2016/06/21 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.sla.dto.CalculateDueDateParam;
import vn.com.unit.common.sla.dto.CreateSlaAlertParam;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.dts.utils.DtsDateUtil;
import vn.com.unit.workflow.activiti.core.impl.ActivitiWorkflowParams;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.service.ActDocWorkflowListener;
import vn.com.unit.workflow.activiti.service.JpmProcessInstActService;
import vn.com.unit.workflow.constant.WorkflowConstant;
import vn.com.unit.workflow.core.WorkflowTaskService;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.dto.JpmTaskAssigneeDto;
import vn.com.unit.workflow.entity.JpmHiTask;
import vn.com.unit.workflow.entity.JpmTask;
import vn.com.unit.workflow.enumdef.StepCodeCommon;
import vn.com.unit.workflow.service.AuthorityAccountService;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;
import vn.com.unit.workflow.service.JpmHiTaskService;
import vn.com.unit.workflow.service.JpmSlaConfigService;
import vn.com.unit.workflow.service.JpmStepDeployService;
import vn.com.unit.workflow.service.JpmTaskAssigneeService;
import vn.com.unit.workflow.service.JpmTaskService;
import vn.com.unit.workflow.service.JpmTaskSlaAsyncService;

/**
 * ActDocWorkflowListenerImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service("docWorkflowListener")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActDocWorkflowListenerImpl implements ActDocWorkflowListener {

    @Autowired
    private JpmStepDeployService jpmStepDeployService;

    @Autowired
    private ActivitiWorkflowParams activitiWorkflowParams;

    @Autowired
    private JpmTaskService jpmTaskService;

    @Autowired
    private JpmHiTaskService jpmHiTaskService;

    @Autowired
    private WorkflowTaskService workflowTaskService;
    
    @Autowired
    private JpmTaskAssigneeService jpmTaskAssigneeService;
    
    @Autowired
    private JpmProcessInstActService jpmProcessInstActService;
    
    @Autowired
    private AuthorityAccountService authorityAccountService;
    
    @Autowired
    private JpmButtonForStepDeployService jpmButtonForStepDeployService;
    
    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;
    
    @Autowired
    private JpmSlaConfigService jpmSlaConfigService;
    
//    @Autowired
//    private JpmTaskSlaAsyncService taskSlaAsyncService;
    
    @Autowired
    private JCommonService commonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onCreateTask(Execution execution, DelegateTask task) {

        // Get StepDeployDto from StepCode
        Long processDeployId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_DEPLOY_ID);
        String stepCode = task.getTaskDefinitionKey();
        JpmStepDeployDto jpmStepDeployDto = jpmStepDeployService.getJpmStepDeployDtoByProcessDeployIdAndStepCode(processDeployId, stepCode);
        
        // If step is null then error
        Assert.notNull(jpmStepDeployDto, "ActDocWorkflowListener - onCreateTask - jpmStepDeploy can not null - params processDeployId: "
                + processDeployId + ", stepCode: " + stepCode);
        
        Long jpmTaskId = sqlManagerService.getNextValBySeqName(WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_TASK);
        // Return P_JPM_TASK_ID
        activitiWorkflowParams.putParam(WorkflowConstant.P_JPM_TASK_ID, jpmTaskId);
        
        // Call SLA
        JpmSlaConfigDto jpmSlaConfigDto = jpmSlaConfigService.getJpmSlaConfigDtoByProcessDeployIdAndStepDeployId(processDeployId, jpmStepDeployDto.getStepDeployId());
        if(null != jpmSlaConfigDto && null != jpmSlaConfigDto.getId() && jpmSlaConfigDto.isActived()) {
            Long slaConfigId = jpmSlaConfigDto.getId();
            Map<String, Object> mapData = new HashMap<>();
            mapData.put(CreateSlaAlertParam.BUSINESS_KEY, jpmTaskId);
            
            CalculateDueDateParam calcDueDateParam = new CalculateDueDateParam();
            calcDueDateParam.setSlaConfigId(slaConfigId);
            calcDueDateParam.setSubmitDate(commonService.getSystemDate());
            //taskSlaAsyncService.createTaskSla(jpmTaskId, calcDueDateParam);
            
        }

        Long commonStatusId = jpmStepDeployDto.getCommonStatusId();
        Long processStatusId = jpmStepDeployDto.getStatusDeployId();
        
        String commonStatusCode = jpmStepDeployDto.getCommonStatusCode();
        String processStatusCode = jpmStepDeployDto.getStatusCode();
        String processStatusName = jpmStepDeployDto.getStepName();
        
        activitiWorkflowParams.putParam(WorkflowConstant.P_COMMON_STATUS_CODE, commonStatusCode);
        activitiWorkflowParams.putParam(WorkflowConstant.P_PROCESS_STATUS_CODE, processStatusCode);
        activitiWorkflowParams.putParam(WorkflowConstant.P_PROCESS_STATUS_NAME, processStatusName);
        
        Long stepDeployId = jpmStepDeployDto.getStepDeployId();
        String coreTaskId = task.getId();
        
        //Get PERMISSION_DEPLOY_ID form buttonId and stepDeployId
        Long permissionDeployId = jpmButtonForStepDeployService.getPermissionDeployIdByProcessDeployIdAndStepDeployId(processDeployId, stepDeployId);
        int permissionType = workflowTaskService.getPermissionTypeByCoreTaskId(coreTaskId);
        
        // Set parameter to build JPM_TASK
        activitiWorkflowParams.putParam(WorkflowConstant.P_STEP_CODE, stepCode);
        activitiWorkflowParams.putParam(WorkflowConstant.P_STEP_DEPLOY_ID, stepDeployId);
        activitiWorkflowParams.putParam(WorkflowConstant.P_CORE_TASK_ID, coreTaskId);

        // Save JpmTask
        JpmTask jpmTask = this.buildNewJpmTask(jpmTaskId, permissionType, permissionDeployId);
        jpmTaskService.createTask(jpmTask);
    
        // Save JPM_TASK_ASSIGNEE
        this.saveNewJpmTaskAssignee(permissionType, permissionDeployId, jpmTask.getId());        
        
        // Save or update JPM_PROCESS_INST_ACT
        JpmProcessInstActDto jpmProcessInstActDto  = this.buildJpmProcessInstActDto(task.getProcessInstanceId(), commonStatusId, processStatusId);
        jpmProcessInstActService.saveJpmProcessInstActDto(jpmProcessInstActDto);
    }

    private JpmProcessInstActDto buildJpmProcessInstActDto(String processInstancceId, Long commonStatusId, Long processStatusId) {
        Long docId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_DOC_ID);
        Long processDeployId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_DEPLOY_ID);
        Long businessId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_BUSINESS_ID);
        
        int refType = 1;
        JpmProcessInstActDto jpmProcessInstActDto = jpmProcessInstActService.getJpmProcessInstActDtoByReference(docId, refType);
        if (null == jpmProcessInstActDto) {
            jpmProcessInstActDto = new JpmProcessInstActDto();
            jpmProcessInstActDto.setProcessDeployId(processDeployId);
            jpmProcessInstActDto.setBusinessId(businessId);
        }

        jpmProcessInstActDto.setProcessInstActId(processInstancceId);
        jpmProcessInstActDto.setReferenceId(docId);
        jpmProcessInstActDto.setReferenceType(refType);
        jpmProcessInstActDto.setCommonStatusId(commonStatusId);
        jpmProcessInstActDto.setProcessStatusId(processStatusId);
        
        return jpmProcessInstActDto;
    }

    private void saveNewJpmTaskAssignee(int permissionType, Long permissionDeployId, Long jpmTaskId) {
        Long processDeployId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_DEPLOY_ID);
        Long stepDeployId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_STEP_DEPLOY_ID);

        JpmTaskAssigneeDto jpmTaskAssigneeDto = new JpmTaskAssigneeDto();
        jpmTaskAssigneeDto.setPermissionType(permissionType);
        jpmTaskAssigneeDto.setPermissionDeployId(permissionDeployId);
        jpmTaskAssigneeDto.setStepDeployId(stepDeployId);
        jpmTaskAssigneeDto.setTaskId(jpmTaskId);
        jpmTaskAssigneeDto.setProcessDeployId(processDeployId);
        
        
        /** creeate task by submitted */
        Long submittedId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_ID);
        List<Long> submittedIds = new ArrayList<>();
        submittedIds.add(submittedId);
        jpmTaskAssigneeService.createTaskSubmitted(jpmTaskAssigneeDto, submittedIds);

        /** creeate task by owner */
        Long ownerId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_ID);
        List<Long> ownerIds = new ArrayList<>();
        ownerIds.add(ownerId);
        jpmTaskAssigneeService.createTaskOwner(jpmTaskAssigneeDto, ownerIds);
        
        /** creeate task by assignee */
        List<Long> assigneeIds;
		try {
			assigneeIds = authorityAccountService.getAssigneeIdsByStepDeployId(stepDeployId);
	        jpmTaskAssigneeService.createTaskAssignee(jpmTaskAssigneeDto, assigneeIds);
	        
	        activitiWorkflowParams.putParam(WorkflowConstant.P_ASSGINEE_ID_LIST, assigneeIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        activitiWorkflowParams.putParam(WorkflowConstant.P_SUBMITTED_ID_LIST, submittedIds);
        activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_ID_LIST, ownerIds);
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onCompleteTask(Execution execution, DelegateTask task) {
        String coreTaskId = task.getId();
        JpmTask jpmTask = jpmTaskService.getJpmTaskByCoreTaskId(coreTaskId);
        if (null != jpmTask) {
            Long jpmTaskId = jpmTask.getId();
            JpmHiTask hiTask = this.buildJpmHiTask(jpmTaskId);
            jpmTaskService.completeJpmTask(hiTask);
            
            jpmTaskAssigneeService.completeTaskAssignee(jpmTaskId);
        }
    }

    @Override
    public JpmHiTask buildJpmHiTask(Long taskId) {
        JpmHiTask hiTask = jpmHiTaskService.getJpmHiTaskById(taskId);
        
//        Long elapseTime = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_ACTUAL_ELAPSE_TIME);
//        Date startDate = (Date) activitiWorkflowParams.getParam(WorkflowConstant.P_ACTUAL_START_DATE);
//        Date endDate = (Date) activitiWorkflowParams.getParam(WorkflowConstant.P_ACTUAL_END_DATE);
        String note = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_NOTE);
        Long buttonId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_BUTTON_ID);
        
        Date completedDate = CommonDateUtil.getSystemDate();
        Long completedId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_COMPLETED_ID);
        Long completedOrgId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_COMPLETED_ORG_ID);
        Long completedPosId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_COMPLETED_POSITION_ID);
        
        hiTask.setCompletedDate(completedDate);
        hiTask.setCompletedId(completedId);
        hiTask.setCompletedOrgId(completedOrgId);
        hiTask.setCompletedPositionId(completedPosId);
        
        hiTask.setActionId(buttonId);
        
        // TASK_STATUS
//        Date dueDate = hiTask.getPlanDueDate();
//        if (dueDate != null && endDate != null) {
//            hiTask.setActualElapseTime(elapseTime);
//            hiTask.setActualStartDate(startDate);
//            hiTask.setActualEndDate(endDate);
//            if (endDate.after(dueDate)) {
//                hiTask.setTaskStatus(WorkflowConstant.EXPIRED_SLA);
//            } else {
//                hiTask.setTaskStatus(WorkflowConstant.NONE_EXPIRED_FIELD);
//            }
//        }
        
        // NOTE
        Date date = new Date();
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat(DtsDateUtil.YYYYMMDDHHMMSS_HYPHEN); 
        	sdf.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
        	String dateInString = sdf.format(new Date());
        	Date dateVietNam = new SimpleDateFormat(DtsDateUtil.YYYYMMDDHHMMSS_HYPHEN).parse(dateInString);
        	date = dateVietNam;
        } catch (ParseException ex) {
        
        }
        
        hiTask.setNote(note);
        hiTask.setActualEndDate(date);

        return hiTask;
    }

    private JpmTask buildNewJpmTask(Long jpmTaskId, int permissionType, Long permissionDeployId) {

        String coreTaskId = (String)activitiWorkflowParams.getParam(WorkflowConstant.P_CORE_TASK_ID);
        Long processDeployId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_DEPLOY_ID);
        Long stepDeployId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_STEP_DEPLOY_ID);
        String stepDeployCode = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_STEP_CODE);
        
        String taskType = null;
        Long docId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_DOC_ID);
        Long submittedId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_ID);
        Long submittedOrgId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_ORG_ID);
        Long submittedPosId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_POSITION_ID);
        Long ownerId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_ID);
        Long ownerOrgId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_ORG_ID);
        Long ownerPosId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_POSITION_ID);
        
        Date sysDate = (Date) activitiWorkflowParams.getParam(WorkflowConstant.P_SYS_DATE);
        String systemCode = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_SYSTEM_CODE);
        String appCode = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_APP_CODE);
        
        Long buttionId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_BUTTON_ID);


        JpmTask jpmTask = new JpmTask();
        jpmTask.setId(jpmTaskId);
        jpmTask.setCoreTaskId(coreTaskId);
        jpmTask.setProcessDeployId(processDeployId);
        jpmTask.setStepDeployId(stepDeployId);
        jpmTask.setPermissionDeployId(permissionDeployId);
        jpmTask.setStepDeployCode(stepDeployCode);
        jpmTask.setPermissionType(permissionType);
        jpmTask.setStepDeployCode(stepDeployCode);
        jpmTask.setTaskType(taskType);
        jpmTask.setDocId(docId);
        
        jpmTask.setSubmittedId(submittedId);
        jpmTask.setSubmittedDate(sysDate);
        jpmTask.setSubmittedOrgId(submittedOrgId);
        jpmTask.setSubmittedPositionId(submittedPosId);
        
        jpmTask.setOwnerId(ownerId);
        jpmTask.setOwnerOrgId(ownerOrgId);
        jpmTask.setOwnerPositionId(ownerPosId);

        jpmTask.setSystemCode(systemCode);
        jpmTask.setAppCode(appCode);
        
        jpmTask.setTaskStatus(null);
        
        jpmTask.setActionId(buttionId);

        return jpmTask;
    }

    @Override
    public void onComplete(Execution execution) {
        // Get StepDeployDto from StepCode
        Long processDeployId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_DEPLOY_ID);
        String stepCode = StepCodeCommon.FINISHED.toString();

        
        JpmStepDeployDto jpmStepDeployDto = jpmStepDeployService.getJpmStepDeployDtoByProcessDeployIdAndStepCode(processDeployId, stepCode);
        String processInstanceId = execution.getProcessInstanceId();

        Long commonStatusId = jpmStepDeployDto.getCommonStatusId();
        Long processStatusId = jpmStepDeployDto.getProcessStatusId();
        
        String commonStatusCode = jpmStepDeployDto.getCommonStatusCode();
        String processStatusCode = jpmStepDeployDto.getProcessStatusCode();
        String processStatusName = jpmStepDeployDto.getStepName();
        // Save or update JPM_PROCESS_INST_ACT
        JpmProcessInstActDto jpmProcessInstActDto  = this.buildJpmProcessInstActDto(processInstanceId, commonStatusId, processStatusId);
        jpmProcessInstActService.saveJpmProcessInstActDto(jpmProcessInstActDto);
        
        
        activitiWorkflowParams.putParam(WorkflowConstant.P_COMMON_STATUS_CODE, commonStatusCode);
        activitiWorkflowParams.putParam(WorkflowConstant.P_PROCESS_STATUS_CODE, processStatusCode);
        activitiWorkflowParams.putParam(WorkflowConstant.P_PROCESS_STATUS_NAME, processStatusName);

    }
}
