/*******************************************************************************
 * Class        ApprovalModuleDto
 * Created date 2018/11/13
 * Lasted date  2018/11/13
 * Author       VinhLT
 * Change log   2018/11/1301-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

//import vn.com.unit.ep2p.asset.entity.JHistoryApprove;
//import vn.com.unit.ep2p.asset.entity.Step;

//import vn.com.unit.jcanary.entity.JHistoryApprove;
//import vn.com.unit.jcanary.entity.Step;

/**
 * ApprovalModuleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class ApprovalModuleDto {

    private ProcessDto processDto;

//	private List<Step> lstSteps;
//
//	private List<JHistoryApprove> lstJHistoryApprove;

    private AccountDetailDto creater;

    private Date createdDate;

    private AccountDetailDto updater;

    private Date updatedDate;

    private AccountDetailDto owner;

    private AccountDetailDto assignee;

    private String businessCode;

    private boolean isAllowActionStatusDraft;

    private String comments;

    private Long referenceId;

    private Integer currentStatus;

    private String currentStatusName;

    private String action;

    private Long stepId;

    private boolean isShowDataProfile = false;

    private boolean isShowComments = false;

    private boolean isShowHistory = false;

    private boolean isShowRequesterInfos = false;

    private boolean isShowView = false;

    private boolean isAllowActionStatusNonDraft = false;// case only PR phatvt

    /**
     * Get processDto
     * 
     * @return ProcessDto
     * @author VinhLT
     */
    public ProcessDto getProcessDto() {
        return processDto;
    }

    /**
     * Set processDto
     * 
     * @param processDto type ProcessDto
     * @return
     * @author VinhLT
     */
    public void setProcessDto(ProcessDto processDto) {
        this.processDto = processDto;
    }
//
//	/**
//	 * Get lstSteps
//	 * 
//	 * @return List<Step>
//	 * @author VinhLT
//	 */
//	public List<Step> getLstSteps() {
//		return lstSteps != null ? new ArrayList<>(lstSteps) : null;
//	}
//
//	/**
//	 * Set lstSteps
//	 * 
//	 * @param lstSteps type List<Step>
//	 * @return
//	 * @author VinhLT
//	 */
//	public void setLstSteps(List<Step> lstSteps) {
//		this.lstSteps = lstSteps != null ? new ArrayList<>(lstSteps) : null;
//	}
//
//	/**
//	 * Get lstJHistoryApprove
//	 * 
//	 * @return List<JHistoryApprove>
//	 * @author VinhLT
//	 */
//	public List<JHistoryApprove> getLstJHistoryApprove() {
//		return lstJHistoryApprove != null ? new ArrayList<>(lstJHistoryApprove) : null;
//	}
//
//	/**
//	 * Set lstJHistoryApprove
//	 * 
//	 * @param lstJHistoryApprove type List<JHistoryApprove>
//	 * @return
//	 * @author VinhLT
//	 */
//	public void setLstJHistoryApprove(List<JHistoryApprove> lstJHistoryApprove) {
//		this.lstJHistoryApprove = lstJHistoryApprove != null ? new ArrayList<>(lstJHistoryApprove) : null;
//	}

    /**
     * Get creater
     * 
     * @return AccountDetailDto
     * @author VinhLT
     */
    public AccountDetailDto getCreater() {
        return creater;
    }

    /**
     * Set creater
     * 
     * @param creater type AccountDetailDto
     * @return
     * @author VinhLT
     */
    public void setCreater(AccountDetailDto creater) {
        this.creater = creater;
    }

    /**
     * Get updater
     * 
     * @return AccountDetailDto
     * @author VinhLT
     */
    public AccountDetailDto getUpdater() {
        return updater;
    }

    /**
     * Set updater
     * 
     * @param updater type AccountDetailDto
     * @return
     * @author VinhLT
     */
    public void setUpdater(AccountDetailDto updater) {
        this.updater = updater;
    }

    /**
     * Get owner
     * 
     * @return AccountDetailDto
     * @author VinhLT
     */
    public AccountDetailDto getOwner() {
        return owner;
    }

    /**
     * Set owner
     * 
     * @param owner type AccountDetailDto
     * @return
     * @author VinhLT
     */
    public void setOwner(AccountDetailDto owner) {
        this.owner = owner;
    }

    /**
     * Get assignee
     * 
     * @return AccountDetailDto
     * @author VinhLT
     */
    public AccountDetailDto getAssignee() {
        return assignee;
    }

    /**
     * Set assignee
     * 
     * @param assignee type AccountDetailDto
     * @return
     * @author VinhLT
     */
    public void setAssignee(AccountDetailDto assignee) {
        this.assignee = assignee;
    }

    /**
     * Get createdDate
     * 
     * @return Date
     * @author VinhLT
     */
    public Date getCreatedDate() {
        return createdDate != null ? (Date) createdDate.clone() : null;
    }

    /**
     * Set createdDate
     * 
     * @param createdDate type Date
     * @return
     * @author VinhLT
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate != null ? (Date) createdDate.clone() : null;
    }

    /**
     * Get updatedDate
     * 
     * @return Date
     * @author VinhLT
     */
    public Date getUpdatedDate() {
        return updatedDate != null ? (Date) updatedDate.clone() : null;
    }

    /**
     * Set updatedDate
     * 
     * @param updatedDate type Date
     * @return
     * @author VinhLT
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate != null ? (Date) updatedDate.clone() : null;
    }

    /**
     * Get businessCode
     * 
     * @return String
     * @author VinhLT
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * Set businessCode
     * 
     * @param businessCode type String
     * @return
     * @author VinhLT
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * Get isAllowActionStatusDraft
     * 
     * @return boolean
     * @author VinhLT
     */
    public boolean getIsAllowActionStatusDraft() {
        return isAllowActionStatusDraft;
    }

    /**
     * Set isAllowActionStatusDraft
     * 
     * @param isAllowActionStatusDraft type boolean
     * @return
     * @author VinhLT
     */
    public void setIsAllowActionStatusDraft(boolean isAllowActionStatusDraft) {
        this.isAllowActionStatusDraft = isAllowActionStatusDraft;
    }

    /**
     * Get comments
     * 
     * @return String
     * @author VinhLT
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set comments
     * 
     * @param comments type String
     * @return
     * @author VinhLT
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Get referenceId
     * 
     * @return Long
     * @author VinhLT
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * 
     * @param referenceId type Long
     * @return
     * @author VinhLT
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get action
     * 
     * @return String
     * @author VinhLT
     */
    public String getAction() {
        return action;
    }

    /**
     * Set action
     * 
     * @param action type String
     * @return
     * @author VinhLT
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Get currentStatus
     * 
     * @return Integer
     * @author VinhLT
     */
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Set currentStatus
     * 
     * @param currentStatus type Integer
     * @return
     * @author VinhLT
     */
    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * Get stepId
     * 
     * @return Long
     * @author VinhLT
     */
    public Long getStepId() {
        return stepId;
    }

    /**
     * Set stepId
     * 
     * @param stepId type Long
     * @return
     * @author VinhLT
     */
    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    /**
     * Get currentStatusName
     * 
     * @return String
     * @author VinhLT
     */
    public String getCurrentStatusName() {
        return currentStatusName;
    }

    /**
     * Set currentStatusName
     * 
     * @param currentStatusName type String
     * @return
     * @author VinhLT
     */
    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    /**
     * Get isShowDataProfile
     * 
     * @return boolean
     * @author VinhLT
     */
    public boolean getIsShowDataProfile() {
        return isShowDataProfile;
    }

    /**
     * Set isShowDataProfile
     * 
     * @param isShowDataProfile type boolean
     * @return
     * @author VinhLT
     */
    public void setIsShowDataProfile(boolean isShowDataProfile) {
        this.isShowDataProfile = isShowDataProfile;
    }

    /**
     * Get isShowComments
     * 
     * @return boolean
     * @author VinhLT
     */
    public boolean getIsShowComments() {
        return isShowComments;
    }

    /**
     * Set isShowComments
     * 
     * @param isShowComments type boolean
     * @return
     * @author VinhLT
     */
    public void setIsShowComments(boolean isShowComments) {
        this.isShowComments = isShowComments;
    }

    /**
     * Get isShowHistory
     * 
     * @return boolean
     * @author VinhLT
     */
    public boolean getIsShowHistory() {
        return isShowHistory;
    }

    /**
     * Set isShowHistory
     * 
     * @param isShowHistory type boolean
     * @return
     * @author VinhLT
     */
    public void setIsShowHistory(boolean isShowHistory) {
        this.isShowHistory = isShowHistory;
    }

    /**
     * Get isShowRequesterInfos
     * 
     * @return boolean
     * @author VinhLT
     */
    public boolean getIsShowRequesterInfos() {
        return isShowRequesterInfos;
    }

    /**
     * Set isShowRequesterInfos
     * 
     * @param isShowRequesterInfos type boolean
     * @return
     * @author VinhLT
     */
    public void setIsShowRequesterInfos(boolean isShowRequesterInfos) {
        this.isShowRequesterInfos = isShowRequesterInfos;
    }

    /**
     * Get isShowView
     * 
     * @return boolean
     * @author VinhLT
     */
    public boolean getIsShowView() {
        return isShowView;
    }

    /**
     * Set isShowView
     * 
     * @param isShowView type boolean
     * @return
     * @author VinhLT
     */
    public void setIsShowView(boolean isShowView) {
        this.isShowView = isShowView;
    }

    public boolean getIsAllowActionStatusNonDraft() {
        return isAllowActionStatusNonDraft;
    }

    /**
     * Set isAllowActionStatusDraft
     * 
     * @param isAllowActionStatusDraft type boolean
     * @return
     * @author VinhLT
     */
    public void setIsAllowActionStatusNonDraft(boolean isAllowActionStatusNonDraft) {
        this.isAllowActionStatusNonDraft = isAllowActionStatusNonDraft;
    }

}
