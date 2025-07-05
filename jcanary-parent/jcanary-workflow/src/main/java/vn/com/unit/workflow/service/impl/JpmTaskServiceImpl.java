/*******************************************************************************
 * Class        ：JpmTaskServiceImpl
 * Created date ：2020/11/19
 * Lasted date  ：2020/11/19
 * Author       ：tantm
 * Change log   ：2020/11/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.workflow.constant.WorkflowConstant;
import vn.com.unit.workflow.dto.AuthorityAccountDto;
import vn.com.unit.workflow.dto.JpmHiTaskDto;
import vn.com.unit.workflow.dto.JpmTaskDto;
import vn.com.unit.workflow.entity.JpmHiTask;
import vn.com.unit.workflow.entity.JpmTask;
import vn.com.unit.workflow.repository.JpmTaskRepository;
import vn.com.unit.workflow.service.AuthorityAccountService;
import vn.com.unit.workflow.service.JpmHiTaskService;
import vn.com.unit.workflow.service.JpmTaskService;

/**
 * JpmTaskServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmTaskServiceImpl implements JpmTaskService {

    @Autowired
    private JpmTaskRepository jpmTaskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpmHiTaskService jpmHiTaskService;

    @Autowired
    private JCommonService commonService;
    
    @Autowired
    private AuthorityAccountService authorityAccountService;
    
    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;
    
    @Override
    public JpmTask getJpmTaskByCoreTaskId(String coreTaskId) {
        return jpmTaskRepository.getJpmTaskByCoreTaskId(coreTaskId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTask(JpmTask jpmTask) {
        
        // save task entity
        jpmTask = this.saveJpmTask(jpmTask);

        // save task history
        JpmHiTask hiTask = objectMapper.convertValue(jpmTask, JpmHiTask.class);
        jpmHiTaskService.saveJpmHiTask(hiTask);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmTask saveJpmTask(JpmTask jpmTask) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = commonService.getSystemDate();
        Long id = jpmTask.getId();
        if (null != id) {
            JpmTask oldJpmTask = jpmTaskRepository.findOne(id);
            if (null != oldJpmTask) {
                jpmTask.setCreatedId(oldJpmTask.getCreatedId());
                jpmTask.setCreatedDate(oldJpmTask.getCreatedDate());
                jpmTask.setUpdatedId(userId);
                jpmTask.setUpdatedDate(sysDate);
                jpmTaskRepository.update(jpmTask);
            } else {
                jpmTask.setCreatedId(userId);
                jpmTask.setCreatedDate(sysDate);
                jpmTask.setUpdatedId(userId);
                jpmTask.setUpdatedDate(sysDate);
                jpmTaskRepository.create(jpmTask);
            }
        } else {
            jpmTask.setCreatedId(userId);
            jpmTask.setCreatedDate(sysDate);
            jpmTask.setUpdatedId(userId);
            jpmTask.setUpdatedDate(sysDate);
            jpmTaskRepository.create(jpmTask);
        }
        return jpmTask;
    }

    @Override
    public void completeJpmTask(JpmHiTask hiTask) {
        try {
			String jsonData = this.buildJsonDataActionTask(hiTask);
			hiTask.setJsonData(jsonData);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        jpmHiTaskService.saveJpmHiTask(hiTask);

        // delete active Task
        jpmTaskRepository.delete(hiTask.getId());

    }

    @Override
    public void updatePlanSlaJpmTask(Long jpmTaskId, Date startDate, Date dueDate, int estimateTime, int callandarType,
            String estimateUnitTime, Long totalTime, Long slaConfigId) {
        jpmTaskRepository.updateSlaDueDateTask(jpmTaskId, startDate, dueDate, estimateTime, callandarType, estimateUnitTime, totalTime, slaConfigId);
        jpmHiTaskService.updateSlaDueDateHiTask(jpmTaskId, startDate, dueDate, estimateTime, callandarType, estimateUnitTime, totalTime, slaConfigId);

    }
    
    
    private String buildJsonDataActionTask(JpmHiTask hiTask) throws JsonProcessingException {
    	
    	JpmHiTaskDto jpmTaskDto = objectMapper.convertValue(hiTask, JpmHiTaskDto.class);
    	
    	/** set value complete */
    	Long asssigneeId = jpmTaskDto.getCompletedId();
    	Long asssigneeOrgId = jpmTaskDto.getCompletedOrgId();
    	Long asssigneePosId = jpmTaskDto.getCompletedPositionId();
    	AuthorityAccountDto asssigneeInfo = authorityAccountService.getInfoAccountById(asssigneeId);
        String asssigneeOrgName = null;
        if (asssigneeOrgId != null) {
            asssigneeOrgName = authorityAccountService.getNameOrgById(asssigneeOrgId);
        }
        String asssigneePosName = null;
        if (asssigneePosId != null) {
            asssigneePosName = authorityAccountService.getNamePositionById(asssigneePosId);
        }
    	
    	jpmTaskDto.setCompletedAvatar(asssigneeInfo.getAccountAvatar());
    	jpmTaskDto.setCompletedAvatarRepoId(asssigneeInfo.getAccountAvatarRepoId());
    	jpmTaskDto.setCompletedCode(asssigneeInfo.getAccountCode());
    	jpmTaskDto.setCompletedEmail(asssigneeInfo.getAccountEmail());
    	jpmTaskDto.setCompletedFullName(asssigneeInfo.getAccountFullName());
    	jpmTaskDto.setCompletedId(asssigneeId);
    	jpmTaskDto.setCompletedName(asssigneeInfo.getAccountName());
    	jpmTaskDto.setCompletedOrgId(asssigneeOrgId);
    	jpmTaskDto.setCompletedOrgName(asssigneeOrgName);
    	jpmTaskDto.setCompletedPositionId(asssigneePosId);
    	jpmTaskDto.setCompletedPositionName(asssigneePosName);
    	
    	/** set value submitted */
        Long submittedId = jpmTaskDto.getSubmittedId();
        Long submittedOrgId = jpmTaskDto.getSubmittedOrgId();
        Long submittedPosId = jpmTaskDto.getSubmittedPositionId();
        AuthorityAccountDto submittedInfo = authorityAccountService.getInfoAccountById(submittedId);
        String submittedOrgName = null;
        if (submittedOrgId != null) {
            submittedOrgName = authorityAccountService.getNameOrgById(submittedOrgId);
        }
        
        String submittedPosName =  null;
        if (submittedPosId != null) {
            submittedPosName = authorityAccountService.getNamePositionById(submittedPosId);
        }
        
        jpmTaskDto.setSubmittedAvatar(submittedInfo.getAccountAvatar());
        jpmTaskDto.setSubmittedAvatarRepoId(submittedInfo.getAccountAvatarRepoId());
        jpmTaskDto.setSubmittedCode(submittedInfo.getAccountCode());
        jpmTaskDto.setSubmittedEmail(submittedInfo.getAccountEmail());
        jpmTaskDto.setSubmittedFullName(submittedInfo.getAccountFullName());
        jpmTaskDto.setSubmittedId(submittedId);
        jpmTaskDto.setSubmittedName(submittedInfo.getAccountName());
        jpmTaskDto.setSubmittedOrgId(submittedOrgId);
        jpmTaskDto.setSubmittedOrgName(submittedOrgName);
        jpmTaskDto.setSubmittedPositionId(submittedPosId);
        jpmTaskDto.setSubmittedPositionName(submittedPosName);
        
    	/** set value owner */
        Long ownerId = jpmTaskDto.getOwnerId();
        Long ownerOrgId = jpmTaskDto.getOwnerOrgId();
        Long ownerPosId = jpmTaskDto.getOwnerPositionId();
        AuthorityAccountDto ownerInfo = authorityAccountService.getInfoAccountById(ownerId);
        String ownerOrgName = null;
        if (ownerOrgId != null) {
            ownerOrgName = authorityAccountService.getNameOrgById(ownerOrgId);
        }
        
        String ownerPosName = null;
        if (ownerPosId != null) {
            ownerPosName =  authorityAccountService.getNamePositionById(ownerPosId);
        }
        
        jpmTaskDto.setOwnerAvatar(ownerInfo.getAccountAvatar());
        jpmTaskDto.setOwnerAvatarRepoId(ownerInfo.getAccountAvatarRepoId());
        jpmTaskDto.setOwnerCode(ownerInfo.getAccountCode());
        jpmTaskDto.setOwnerEmail(ownerInfo.getAccountEmail());
        jpmTaskDto.setOwnerFullName(ownerInfo.getAccountFullName());
        jpmTaskDto.setOwnerId(ownerId);
        jpmTaskDto.setOwnerName(ownerInfo.getAccountName());
        jpmTaskDto.setOwnerOrgId(ownerOrgId);
        jpmTaskDto.setOwnerOrgName(ownerOrgName);
        jpmTaskDto.setOwnerPositionId(ownerPosId);
        jpmTaskDto.setOwnerPositionName(ownerPosName);       
        
    	return CommonJsonUtil.convertObjectToJSON(jpmTaskDto);
    }

    @Override
    public JpmTaskDto getCurrentTaskByDocId(Long docId) {
        return jpmTaskRepository.getCurrentTaskByDocId(docId);
    }

    @Override
    public DbRepository<JpmTask, Long> initRepo() {
        return jpmTaskRepository;
    }

}
