/*******************************************************************************
 * Class        ：ActivitiWorkflowActionImpl
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：tantm
 * Change log   ：2020/11/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.sla.dto.CalculateElapsedMinutesParam;
import vn.com.unit.common.sla.dto.CreateSlaAlertParam;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.DocumentFilterActionService;
import vn.com.unit.core.service.JcaAccountOrgService;
import vn.com.unit.core.workflow.WorkflowAction;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.dto.DocFilterActionDto;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
import vn.com.unit.workflow.activiti.core.impl.ActivitiWorkflowParams;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.entity.JpmProcessInstAct;
import vn.com.unit.workflow.activiti.service.ActDocWorkflowListener;
import vn.com.unit.workflow.activiti.service.JpmProcessInstActService;
import vn.com.unit.workflow.constant.WorkflowConstant;
import vn.com.unit.workflow.constant.WorkflowExceptionCodeConstant;
import vn.com.unit.workflow.core.WorkflowIdentityService;
import vn.com.unit.workflow.core.WorkflowRuntimeService;
import vn.com.unit.workflow.core.WorkflowTaskService;
import vn.com.unit.workflow.dto.JpmParamDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.dto.JpmTaskDto;
import vn.com.unit.workflow.dto.TaskInfoDto;
import vn.com.unit.workflow.entity.JpmHiTask;
import vn.com.unit.workflow.entity.JpmTask;
import vn.com.unit.workflow.enumdef.ButtonType;
import vn.com.unit.workflow.enumdef.DocumentState;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;
import vn.com.unit.workflow.service.JpmButtonLangDeployService;
import vn.com.unit.workflow.service.JpmParamDeployService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmSlaConfigService;
import vn.com.unit.workflow.service.JpmStepDeployService;
import vn.com.unit.workflow.service.JpmTaskService;
import vn.com.unit.workflow.service.JpmTaskSlaAsyncService;

/**
 * ActivitiWorkflowActionImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActivitiWorkflowActionImpl implements WorkflowAction {

    /** The workflow identity service. */
    @Autowired
    private WorkflowIdentityService workflowIdentityService;

    /** The jpm process deploy service. */
    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    /** The jpm param deploy service. */
    @Autowired
    private JpmParamDeployService jpmParamDeployService;

    /** The jpm button lang deploy service. */
    @Autowired
    JpmButtonLangDeployService jpmButtonLangDeployService;

    /** The jpm task service. */
    @Autowired
    private JpmTaskService jpmTaskService;

    /** The common service. */
    @Autowired
    private JCommonService commonService;

    /** The workflow runtime service. */
    @Autowired
    private WorkflowRuntimeService workflowRuntimeService;

    /** The workflow task service. */
    @Autowired
    private WorkflowTaskService workflowTaskService;

    /** The activiti workflow params. */
    @Autowired
    private ActivitiWorkflowParams activitiWorkflowParams;

    /** The document filter action service. */
    @Autowired
    private DocumentFilterActionService documentFilterActionService;

    @Autowired
    private JpmStepDeployService jpmStepDeployService;

    @Autowired
    private JpmButtonForStepDeployService jpmButtonForStepDeployService;

    @Autowired
    private ActDocWorkflowListener docWorkflowListener;
    
    @Autowired
    private JcaAccountOrgService jcaAccountOrgService;
    
    @Autowired
    private JpmTaskSlaAsyncService taskSlaAsyncService;
    
    @Autowired
    private JpmSlaConfigService jpmSlaConfigService;

    @Autowired
    private JpmProcessInstActService jpmProcessInstActService;
    
    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;
    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.workflow.WorkflowAction#startWorkflowForDocument(vn.com.unit.core.dto.AbstractDocumentDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends AbstractDocumentDto> void startWorkflowForDocument(T documentDto) throws Exception {
        try {
            // Get params from Dto
//            Long ownerId = documentDto.getOwnerId();
//            List<Long> memberCc = documentDto.getMemberCcs();
            Long processDeployId = documentDto.getProcessDeployId();
            String docCode = documentDto.getDocCode();
            Long docId = documentDto.getDocId();
            Long buttonDeployId = documentDto.getButtonId();
            Long businessId = documentDto.getBusinessId();
            Long companyIdOfDoc = documentDto.getCompanyId();
            String systemCode = documentDto.getSystemCode();
            String appCode = documentDto.getAppCode();

            // Get info UserProfile
            Long userId = UserProfileUtils.getAccountId();
            String userName = UserProfileUtils.getUserNameLogin();

            Date sysDate = commonService.getSystemDate();
            String coreUserId = String.valueOf(userId);

            // Authenticated coreUserId
            workflowIdentityService.setAuthenticatedUserId(coreUserId);

            // Get jpmProcessDeploy is the lastest version.
            JpmProcessDeployDto jpmProcessDeployDto = jpmProcessDeployService.getJpmProcessDeployLasted(companyIdOfDoc, businessId);
            if (jpmProcessDeployDto == null) {
                // CONST NOT WORKFLOW
                throw new DetailException(WorkflowExceptionCodeConstant.E203703_WF_WORKFLOW_NOT_FOUND);
            }

            Long processDeployIdLastest = jpmProcessDeployDto.getProcessDeployId();
            if (!processDeployIdLastest.equals(processDeployId)) {
                // CONST WORKFLOW OLD
                throw new DetailException(WorkflowExceptionCodeConstant.E203704_WF_WORKFLOW_OLD);
            }

            String processDefinitionId = jpmProcessDeployDto.getProcessDefinitionId();
            // Get processInstanceId by BusinessKey
            String processInstanceId = workflowRuntimeService.getProcessInstanceIdByBusinessKey(docCode);

            // Set parameter for Listener
            // For JPM_TASK
            activitiWorkflowParams.putParam(WorkflowConstant.P_PROCESS_DEPLOY_ID, processDeployIdLastest);
            activitiWorkflowParams.putParam(WorkflowConstant.P_SYSTEM_CODE, systemCode);
            activitiWorkflowParams.putParam(WorkflowConstant.P_APP_CODE, appCode);
            activitiWorkflowParams.putParam(WorkflowConstant.P_DOC_ID, docId);
            activitiWorkflowParams.putParam(WorkflowConstant.P_BUTTON_ID, buttonDeployId);
            activitiWorkflowParams.putParam(WorkflowConstant.P_SYS_DATE, sysDate);
            
            JcaAccountOrgDto accDto = jcaAccountOrgService.getMainJcaAccountOrgDtoByAccountId(userId);
            if (accDto != null) {
                activitiWorkflowParams.putParam(WorkflowConstant.P_SUBMITTED_ORG_ID, accDto.getOrgId());
                activitiWorkflowParams.putParam(WorkflowConstant.P_SUBMITTED_POSITION_ID, accDto.getPositionId());
                
                activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_ORG_ID, accDto.getOrgId());
                activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_POSITION_ID, accDto.getPositionId());
            }
            
            activitiWorkflowParams.putParam(WorkflowConstant.P_SUBMITTED_ID, userId);
            activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_ID, userId);
            
            // For JPM_PROCESS_INST_ACT
            activitiWorkflowParams.putParam(WorkflowConstant.P_BUSINESS_ID, businessId);
            if (processInstanceId == null) {	
                processInstanceId = workflowRuntimeService.startProcessInstanceByProcessDefinitionIdAndBusinessKey(processDefinitionId,
                        docCode);
                documentDto.setProcessInstanceId(processInstanceId);
            }

            // Get Task information
            TaskInfoDto taskInfo = workflowTaskService.getTaskInfoDtoByProcessInstanceId(processInstanceId);
            String coreTaskId = taskInfo.getCoreTaskId();
            workflowTaskService.setAssignee(coreTaskId, coreUserId);

             String docInputJson = documentDto.getDocInputJson();
            Map<String, Object> paramsMap = this.getParamsWithValueByProcessDeployId(processDeployId, docInputJson);

            String executionId = taskInfo.getExecutionId();
            // CONST lang
            String nameInpassive = jpmButtonLangDeployService.getButtonNameInPassiveByButtonDeployIdAndLangCode(buttonDeployId,  UserProfileUtils.getLanguage());

            paramsMap.put(WorkflowConstant.SUBMITTED_BY, userName);
            paramsMap.put(WorkflowConstant.SUBMITTER, userId);
            paramsMap.put(WorkflowConstant.IS_RETURN, false);
            paramsMap.put(WorkflowConstant.MAP_KEY, nameInpassive);
            paramsMap.put(WorkflowConstant.ACT_ID_KEY, coreTaskId);
            workflowRuntimeService.setVariables(executionId, paramsMap);

            Long jpmTaskId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_JPM_TASK_ID);
            
            // call sla for step no 1
            JpmStepDeployDto jpmStepDeployDto = jpmStepDeployService.getJpmStepDeployDtoByProcessDeployIdAndStepCode(processDeployIdLastest,
                    taskInfo.getTaskDefinitionKey());
            
            JpmSlaConfigDto jpmSlaConfigDto = jpmSlaConfigService.getJpmSlaConfigDtoByProcessDeployIdAndStepDeployId(processDeployId, jpmStepDeployDto.getStepDeployId());
            if(null != jpmSlaConfigDto && null != jpmSlaConfigDto.getId() && jpmSlaConfigDto.isActived()) {
                Long slaConfigId = jpmSlaConfigDto.getId();
                Map<String, Object> mapData = new HashMap<>();
                mapData = getParamsWithJson(documentDto.getInputJsonEmail());
                
                mapData.put(CreateSlaAlertParam.BUSINESS_KEY, jpmTaskId);
                mapData.put(CreateSlaAlertParam.DOCUMENT_DATA, documentDto);
                mapData.put(CreateSlaAlertParam.ASSGINEE_ID_LIST, activitiWorkflowParams.getParam(WorkflowConstant.P_ASSGINEE_ID_LIST));
                mapData.put(CreateSlaAlertParam.SUBMITTED_ID_LIST, activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_ID_LIST));
                mapData.put(CreateSlaAlertParam.OWNER_ID_LIST, activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_ID_LIST));
                
//                CalculateDueDateParam calcDueDateParam = new CalculateDueDateParam();
//                calcDueDateParam.setSlaConfigId(slaConfigId);
//                calcDueDateParam.setSubmitDate(sysDate);
//                //calcDueDateParam.setMapData(mapData);
//                taskSlaAsyncService.createTaskSla(jpmTaskId, calcDueDateParam);
                
                CreateSlaAlertParam createSlaAlertParam = new CreateSlaAlertParam();
                createSlaAlertParam.setSlaConfigId(slaConfigId);
                createSlaAlertParam.setSubmitDate(sysDate);
                createSlaAlertParam.setMapData(mapData);
                taskSlaAsyncService.excuteSlaForTask(jpmTaskId, createSlaAlertParam);
            }
            
            /** END */

            documentDto.setCoreTaskId(coreTaskId);
            ActionDto actionDto = jpmButtonForStepDeployService.getActionDtoByCoreTaskIdAndButtonId(documentDto.getProcessDeployId(),
                    documentDto.getCoreTaskId(), documentDto.getButtonId(), documentDto.getCommonStatusCode());

            /** BEGIN complete efo document filter */
            DocFilterActionDto docFilterActionDto = this.createParamDocFilterActionDto(docId, jpmTaskId);
            documentFilterActionService.submitDocFilter(docFilterActionDto);
            /** END */
            
            // complete task and create new task
            this.completeTask(documentDto, actionDto);

        } catch (Exception e) {
            if (e instanceof DetailException) {
                DetailException detailException = (DetailException) e;
                String exceptionErrorCode = detailException.getExceptionErrorCode();
                throw new DetailException(exceptionErrorCode);
            } else {
                throw new DetailException(WorkflowExceptionCodeConstant.E203602_WF_SUBMIT_PROCESS_ERROR);
            }
        } finally {
            workflowIdentityService.setAuthenticatedUserId(null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.workflow.WorkflowAction#completeTask(vn.com.unit.core.dto.AbstractDocumentDto, vn.com.unit.core.dto.ActionDto)
     */
    @Override
    public <T extends AbstractDocumentDto> void completeTask(T documentDto, ActionDto actionDto) throws DetailException {
        try {
            // Get params from Dto
            Long processDeployId = documentDto.getProcessDeployId();
            Long buttonDeployId = documentDto.getButtonId();
            String coreTaskId = documentDto.getCoreTaskId();
            String note = documentDto.getNote() == null ? actionDto.getConfirmNote() : documentDto.getNote();

            activitiWorkflowParams.putParam(WorkflowConstant.P_BUTTON_ID, buttonDeployId);
            activitiWorkflowParams.putParam(WorkflowConstant.P_STEP_CODE_PREV,documentDto.getProcessStatusCodePrev());
//            Long ownerId = documentDto.getOwnerId();
//            List<Long> memberCc = documentDto.getMemberCcs();
//            Long companyIdOfDoc = documentDto.getCompanyId();

            // Get info UserProfile
            Long userId = UserProfileUtils.getAccountId();
            String coreUserId = String.valueOf(userId);

            Date sysDate = commonService.getSystemDate();

            // Authenticated coreUserId
            workflowIdentityService.setAuthenticatedUserId(coreUserId);
            //set complete user id
            activitiWorkflowParams.putParam(WorkflowConstant.P_COMPLETED_ID, userId);

            TaskInfoDto taskInfoDto = workflowTaskService.getTaskInfoDtoByCoreTaskId(coreTaskId);
            if (null == taskInfoDto) {
                throw new DetailException(WorkflowExceptionCodeConstant.E203601_WF_INVALID_ACCESS_ERROR);
            }

            String executionId = taskInfoDto.getExecutionId();
            String processInstanceId = taskInfoDto.getProcessInstanceId();
            String actionType = actionDto.getButtonType();
            String systemCode = documentDto.getSystemCode();
            String appCode = documentDto.getAppCode();

            
            if (ButtonType.PENDING.toString().equals(actionType)) {
            	//save task

            	JpmTaskDto taskDto = jpmTaskService.getCurrentTaskByDocId(documentDto.getDocId());
    	        Long jpmTaskId = sqlManagerService.getNextValBySeqName(WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_TASK);

    	        //Get PERMISSION_DEPLOY_ID form buttonId and stepDeployId
    	        Long permissionDeployId = jpmButtonForStepDeployService.getPermissionDeployIdByProcessDeployIdAndStepDeployId(processDeployId, taskDto.getStepDeployId());
    	        int permissionType = workflowTaskService.getPermissionTypeByCoreTaskId(coreTaskId);

                JpmTask task = jpmTaskService.getJpmTaskByCoreTaskId(coreTaskId);
    	        JpmTask jpmTask = new JpmTask();
    	        jpmTask.setId(jpmTaskId);
    	        jpmTask.setCoreTaskId(coreTaskId);
    	        jpmTask.setProcessDeployId(processDeployId);
    	        jpmTask.setStepDeployId(taskDto.getStepDeployId());
    	        jpmTask.setStepDeployCode(taskDto.getStepDeployCode());
    	        jpmTask.setPermissionDeployId(permissionDeployId);
    	        jpmTask.setPermissionType(permissionType);
    	        jpmTask.setTaskType(taskDto.getTaskType());
    	        jpmTask.setDocId(taskDto.getDocId());
    	        
    	        jpmTask.setSubmittedId(userId);
    	        jpmTask.setSubmittedDate(sysDate);
    	        
    	        jpmTask.setOwnerId(taskDto.getOwnerId());

    	        jpmTask.setSystemCode(systemCode);
    	        jpmTask.setAppCode(appCode);
    	        
    	        jpmTask.setTaskStatus(null);
    	        
    	        jpmTask.setActionId(buttonDeployId);
    	        
    	        jpmTask.setActualEndDate(sysDate);

                activitiWorkflowParams.putParam(WorkflowConstant.P_BUTTON_ID, buttonDeployId);
                activitiWorkflowParams.putParam(WorkflowConstant.P_NOTE, note);
                activitiWorkflowParams.putParam(WorkflowConstant.P_COMPLETED_ORG_ID, taskDto.getCompletedOrgId());
                activitiWorkflowParams.putParam(WorkflowConstant.P_COMPLETED_POSITION_ID, taskDto.getCompletedPositionId());
    	        //jpmTaskService.createTask(jpmTask);
    	        JpmHiTask jpmHiTask = docWorkflowListener.buildJpmHiTask(task.getId());
    	        jpmHiTask.setId(jpmTaskId);
    	        jpmTaskService.completeJpmTask(jpmHiTask);
                // TODO
            } else if (!ButtonType.REJECT.toString().equals(actionType)) {

                boolean isReturn = false;
                if (ButtonType.RETURN.toString().equals(actionType)) {
                    isReturn = true;
                }
                String docInputJson = documentDto.getDocInputJson();
                Map<String, Object> paramsMap = this.getParamsWithValueByProcessDeployId(processDeployId, docInputJson);

                // CONST lang
                String nameInpassive = jpmButtonLangDeployService.getButtonNameInPassiveByButtonDeployIdAndLangCode(buttonDeployId,
                        UserProfileUtils.getLanguage());

                // Set parameter for Listener
                // For JPM_TASK
                activitiWorkflowParams.putParam(WorkflowConstant.P_PROCESS_DEPLOY_ID, processDeployId);
                activitiWorkflowParams.putParam(WorkflowConstant.P_DOC_ID, documentDto.getDocId());
                activitiWorkflowParams.putParam(WorkflowConstant.P_SYSTEM_CODE, systemCode);
                activitiWorkflowParams.putParam(WorkflowConstant.P_APP_CODE, appCode);
                activitiWorkflowParams.putParam(WorkflowConstant.P_SYS_DATE, sysDate);
                activitiWorkflowParams.putParam(WorkflowConstant.P_BUTTON_ID, buttonDeployId);
                activitiWorkflowParams.putParam(WorkflowConstant.P_NOTE, note);
                
                // For replated workflow task
                JcaAccountOrgDto accDto = jcaAccountOrgService.getMainJcaAccountOrgDtoByAccountId(userId);
                if (accDto != null) {
                    activitiWorkflowParams.putParam(WorkflowConstant.P_SUBMITTED_ORG_ID, accDto.getOrgId());
                    activitiWorkflowParams.putParam(WorkflowConstant.P_SUBMITTED_POSITION_ID, accDto.getPositionId());
                    
                    activitiWorkflowParams.putParam(WorkflowConstant.P_COMPLETED_ORG_ID, accDto.getOrgId());
                    activitiWorkflowParams.putParam(WorkflowConstant.P_COMPLETED_POSITION_ID, accDto.getPositionId());
                    
                    activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_ID, accDto.getUserId());
                    activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_ORG_ID, accDto.getOrgId());
                    activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_POSITION_ID, accDto.getPositionId());
                } else if (accDto == null) {
                    // Nếu accDto == null thì lấy id của user hiện tại làm owner
                    activitiWorkflowParams.putParam(WorkflowConstant.P_OWNER_ID, userId);
                }
                
                activitiWorkflowParams.putParam(WorkflowConstant.P_SUBMITTED_ID, userId);
                
                //TODO chua get duoc ownerDto nen dung tam accDto
//                JpmTaskAssigneeDto assignee = null;//jpmTaskAssigneeService.getJpmTaskAssigneeDtoByTaskId();
//                JcaAccountOrgDto ownerDto = jcaAccountOrgService.getMainJcaAccountOrgDtoByAccountId(userId);

                
                paramsMap.put(WorkflowConstant.SUBMITTER, userId);
                paramsMap.put(WorkflowConstant.IS_RETURN, isReturn);
                paramsMap.put(WorkflowConstant.MAP_KEY, nameInpassive);
                paramsMap.put(WorkflowConstant.ACT_ID_KEY, coreTaskId);
                paramsMap.put(WorkflowConstant.APPROVED, actionDto.getButtonValue());
                workflowRuntimeService.setVariables(executionId, paramsMap);

                workflowTaskService.setAssignee(coreTaskId, coreUserId);
                workflowTaskService.addComment(coreTaskId, processInstanceId, note);

                JpmTask jpmTask = jpmTaskService.getJpmTaskByCoreTaskId(coreTaskId);
                Long jpmTaskId = jpmTask.getId();
                
                // Complete task activiti
                workflowTaskService.complete(coreTaskId);
                
                //SLA when complete task
                CalculateElapsedMinutesParam param = new CalculateElapsedMinutesParam();
                param.setCompleteDate(sysDate);
                //taskSlaAsyncService.completeTaskSla(jpmTaskId, param);
                
                /** BEGIN complete efo document filter */
                DocFilterActionDto docFilterActionDto = this.createParamDocFilterActionDto(documentDto.getDocId(), jpmTaskId);
                Long taskNewId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_JPM_TASK_ID);
                
                if (accDto != null) {
                    docFilterActionDto.setAssigneeOrgId(accDto.getOrgId());
                    docFilterActionDto.setAssigneePositionId(accDto.getOrgId());
                }
            	docFilterActionDto.setAssigneeId(userId);
            	docFilterActionDto.setSubmittedDate(sysDate);
            	
                documentFilterActionService.completeDocFilter(docFilterActionDto, taskNewId);
                /** END */

                // Update commonStatusCode and processStatusCode
                String commonStatusCode = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_COMMON_STATUS_CODE);
                String processStatusCode = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_STATUS_CODE);
                String processStatusName = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_STATUS_NAME);
                documentDto.setCommonStatusCode(commonStatusCode);
                documentDto.setProcessStatusCode(processStatusCode);
                documentDto.setProcessStatusName(processStatusName);

                if (!DocumentState.COMPLETE.toString().equals(commonStatusCode)) {
                    coreTaskId = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_CORE_TASK_ID);
//                    TaskInfoDto taskInfo = workflowTaskService.getTaskInfoDtoByCoreTaskId(coreTaskId);

                    // SLA when create task
                   // createdSLA(documentDto, processDeployId, taskInfo, sysDate);
                }
            } else {
                documentDto.setCommonStatusCode(DocumentState.REJECT.toString());
                documentDto.setProcessStatusCode(DocumentState.REJECT.toString());
                documentDto.setProcessInstanceId(null);

                activitiWorkflowParams.putParam(WorkflowConstant.P_BUTTON_ID, buttonDeployId);
                activitiWorkflowParams.putParam(WorkflowConstant.P_NOTE, note);
                workflowRuntimeService.deleteProcessInstance(processInstanceId, note);
                JpmTask jpmTask = jpmTaskService.getJpmTaskByCoreTaskId(coreTaskId);
                Long taskId = jpmTask.getId();
                JpmHiTask jpmHiTask = docWorkflowListener.buildJpmHiTask(taskId);
                // SLA when create task
                coreTaskId = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_CORE_TASK_ID);
                //comment because exception
                TaskInfoDto taskInfo = new TaskInfoDto();
                if (taskInfo != null) {
                    taskInfo.setTaskDefinitionKey("reject_998");
                    // createdSLA(documentDto, processDeployId, taskInfo, sysDate);
                }
                
                jpmTaskService.completeJpmTask(jpmHiTask);
                


                JpmStepDeployDto jpmStepDeployDto = jpmStepDeployService.getJpmStepDeployDtoByProcessDeployIdAndStepCode(processDeployId,
                		"reject_998");
                Long commonStatusId = jpmStepDeployDto.getCommonStatusId();
                // Save or update JPM_PROCESS_INST_ACT

                activitiWorkflowParams.putParam(WorkflowConstant.P_PROCESS_DEPLOY_ID, processDeployId);
                activitiWorkflowParams.putParam(WorkflowConstant.P_DOC_ID, documentDto.getDocId());
                JpmProcessInstActDto jpmProcessInstActDto  = this.buildJpmProcessInstActDto(processInstanceId, commonStatusId, jpmStepDeployDto.getStatusDeployId());
                JpmProcessInstAct en = new JpmProcessInstAct();
    			NullAwareBeanUtils.copyPropertiesWONull(jpmProcessInstActDto, en);
    			en.setProcessDeployId(jpmProcessInstActDto.getProcessDeployId().toString());
    			
                jpmProcessInstActService.saveJpmProcessInstAct(en);
                
            }
        } catch (Exception e) {
            if (e instanceof DetailException) {
                DetailException detailException = (DetailException) e;
                String exceptionErrorCode = detailException.getExceptionErrorCode();
                throw new DetailException(exceptionErrorCode);
            } else {
                throw new DetailException(WorkflowExceptionCodeConstant.E203602_WF_SUBMIT_PROCESS_ERROR);
            }
        } finally {
            workflowIdentityService.setAuthenticatedUserId(null);
        }
    }

    @SuppressWarnings("unused")
	private <T extends AbstractDocumentDto> void createdSLA(T documentDto, Long processDeployId, TaskInfoDto taskInfo, Date sysDate) {
        Long jpmTaskId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_JPM_TASK_ID);
        JpmStepDeployDto jpmStepDeployDto = jpmStepDeployService.getJpmStepDeployDtoByProcessDeployIdAndStepCode(processDeployId,
                taskInfo.getTaskDefinitionKey());
        JpmSlaConfigDto jpmSlaConfigDto = jpmSlaConfigService.getJpmSlaConfigDtoByProcessDeployIdAndStepDeployId(processDeployId, jpmStepDeployDto.getStepDeployId());
        if(null != jpmSlaConfigDto && null != jpmSlaConfigDto.getId() && jpmSlaConfigDto.isActived()) {
            Long slaConfigId = jpmSlaConfigDto.getId();
            Map<String, Object> mapData = new HashMap<>();
            mapData = getParamsWithJson(documentDto.getInputJsonEmail());
            
            mapData.put(CreateSlaAlertParam.BUSINESS_KEY, jpmTaskId);
            mapData.put(CreateSlaAlertParam.DOCUMENT_DATA, documentDto);
            mapData.put(CreateSlaAlertParam.ASSGINEE_ID_LIST, activitiWorkflowParams.getParam(WorkflowConstant.P_ASSGINEE_ID_LIST));
            mapData.put(CreateSlaAlertParam.SUBMITTED_ID_LIST, activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_ID_LIST));
            mapData.put(CreateSlaAlertParam.OWNER_ID_LIST, activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_ID_LIST));
            
            CreateSlaAlertParam createSlaAlertParam = new CreateSlaAlertParam();
            createSlaAlertParam.setSlaConfigId(slaConfigId);
            createSlaAlertParam.setSubmitDate(sysDate);
            createSlaAlertParam.setMapData(mapData);
            taskSlaAsyncService.excuteSlaForTask(jpmTaskId, createSlaAlertParam);
        }
    }
    
    /**
     * <p>
     * Get params with value by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param docInputJson
     *            type {@link String}
     * @return {@link Map<String,Object>}
     * @author taitt
     */
    private Map<String, Object> getParamsWithValueByProcessDeployId(Long processDeployId, String docInputJson) {
        Map<String, Object> paramsMap = new HashMap<>();

        List<JpmParamDeployDto> paramList = null;
        if (processDeployId != null) {
            paramList = jpmParamDeployService.getParamDeployDtosByProcessDeployId(processDeployId);
        }

        if (CommonCollectionUtil.isNotEmpty(paramList) && CommonStringUtil.isNotEmpty(docInputJson)) {
            JSONObject jsonObject = new JSONObject(docInputJson);
            for (JpmParamDeployDto param : paramList) {
                String paramName = param.getFormFieldName();
                String fieldName = param.getFieldName();

                if (!jsonObject.isNull(paramName)) {
                    String value = jsonObject.get(paramName).toString();
                    String type = param.getDataType();
                    Object valueObject = this.parseValueByDataType(value, type);

                    paramsMap.put(fieldName, valueObject);
                } else {
                    paramsMap.put(fieldName, null);
                }
            }
        }

        return paramsMap;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getParamsWithJson(String docInputJson) {
        Map<String, Object> paramsMap = new HashMap<>();


        if (CommonStringUtil.isNotEmpty(docInputJson)) {
            JSONObject jsonObject = new JSONObject(docInputJson);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                paramsMap.put(key, jsonObject.get(key));
            }
        }

        return paramsMap;
    }
    
    /**
     * <p>
     * Parses the value by data type.
     * </p>
     *
     * @param value
     *            type {@link String}
     * @param type
     *            type {@link String}
     * @return {@link Object}
     * @author taitt
     */
    private Object parseValueByDataType(String value, String type) {
        Object result = null;

        switch (type) {
        case WorkflowConstant.DATA_TYPE_BOOLEAN:
            Boolean valueBoolean = null;
            try {
                valueBoolean = Boolean.valueOf(value);
            } catch (Exception e) {
                valueBoolean = null;
            }
            result = valueBoolean;
            break;
        case WorkflowConstant.DATA_TYPE_DATE:
            Date valueDate = null;
            try {
                valueDate = CommonDateUtil.parseDate(value, CoreConstant.DDMMYYYY_SLASH);
            } catch (Exception e) {
                valueDate = null;
            }
            result = valueDate;
            break;
        case WorkflowConstant.DATA_TYPE_DOUBLE:
            Double valueDouble = null;
            try {
                valueDouble = Double.valueOf(value);
            } catch (Exception e) {
                valueDouble = null;
            }
            result = valueDouble;
            break;
        case WorkflowConstant.DATA_TYPE_FLOAT:
            Float valueFloat = null;
            try {
                valueFloat = Float.valueOf(value);
            } catch (Exception e) {
                valueFloat = null;
            }
            result = valueFloat;
            break;
        case WorkflowConstant.DATA_TYPE_INTEGER:
            Integer valueInteger = null;
            try {
                valueInteger = Integer.valueOf(value);
            } catch (Exception e) {
                valueInteger = null;
            }
            result = valueInteger;
            break;
        case WorkflowConstant.DATA_TYPE_LONG:
            Long valueLong = null;
            try {
                valueLong = Long.valueOf(value);
            } catch (Exception e) {
                valueLong = null;
            }
            result = valueLong;
            break;
        default:
            result = value;
        }

        return result;
    }

    /**
     * <p>
     * Creates the param doc filter action dto.
     * </p>
     *
     * @param docId
     *            type {@link Long}
     * @param submitterId
     *            type {@link Long}
     * @param ownerId
     *            type {@link Long}
     * @param functionId
     *            type {@link Long}
     * @param accList
     *            type {@link List<Long>}
     * @return {@link DocFilterActionDto}
     * @author taitt
     */
    private DocFilterActionDto createParamDocFilterActionDto(Long docId,Long taskId) {
        DocFilterActionDto docFilterActionDto = new DocFilterActionDto();
        
        Date sysDate = CommonDateUtil.getSystemDate();
        
        Long submittedId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_ID);
        Long submittedOrgId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_ORG_ID);
        Long submittedPosId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_SUBMITTED_POSITION_ID);
        Long ownerId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_ID);
        Long ownerOrgId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_ORG_ID);
        Long ownerPosId = (Long) activitiWorkflowParams.getParam(WorkflowConstant.P_OWNER_POSITION_ID);
        String processStatusCode = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_STATUS_CODE);
//        String processStatusName = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_PROCESS_STATUS_NAME);

        
    	docFilterActionDto.setSubmittedId(submittedId);
    	docFilterActionDto.setSubmittedDate(sysDate);
    	docFilterActionDto.setSubmittedOrgId(submittedOrgId);
    	docFilterActionDto.setSubmittedPositionId(submittedPosId);

    	docFilterActionDto.setOwnerId(ownerId);
    	docFilterActionDto.setOwnerOrgId(ownerOrgId);
    	docFilterActionDto.setOwnerPositionId(ownerPosId);
    	
        docFilterActionDto.setTaskId(taskId);
        docFilterActionDto.setDocId(docId);
        
        docFilterActionDto.setProcessStatusCode(processStatusCode);
        
        return docFilterActionDto;
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
        jpmProcessInstActDto.setCommonStatusId(commonStatusId);
        jpmProcessInstActDto.setProcessStatusId(processStatusId);
        
        return jpmProcessInstActDto;
    }

}