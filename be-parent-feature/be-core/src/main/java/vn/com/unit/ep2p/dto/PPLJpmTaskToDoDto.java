/*******************************************************************************
 * Class        ：JpmTaskToDoDto
 * Created date ：2019/07/29
 * Lasted date  ：2019/07/29
 * Author       ：KhuongTH
 * Change log   ：2019/07/29：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * JpmTaskToDoDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class PPLJpmTaskToDoDto {
	
	private Long taskId;
	
	private String actTaskId;
	
	private Long formId;
	
	private Long mainFileId;
	
	private Long docId;

	private String docTitle;
	
	private String formFileName;
	
	private String docType;
	
	private String processName;
	
	private String docState;
	
	private String docStateCode;
	
	private String processStatus;
	
	private String priority;
	
	private String priorityCode;
	
	private String priorityClass;
	
	private Date submittedDate;
	
	private String submittedBy;
	
	private Long companyId;
	
	private String companyName;
	
	private String orgName;
	
	private String formName;
	
	private Long stepId;
	
	private Long processId;
	
	private String processType;
	
	private Long actionId;
	
	private String actionValue;
	
	private Long asigneeId;
	
	private Long ownerId;
	
	private Long orgId;
	
	private String approvalUser;
	
	private Boolean isOverdue;
	
	private String docTitleIsRefer;
	
	private String isReference;
	
	private String docCode;
	
	private boolean isArchive;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public String getActTaskId() {
		return actTaskId;
	}

	public void setActTaskId(String actTaskId) {
		this.actTaskId = actTaskId;
	}
	
	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
	 * @return the mainFileId
	 */
	public Long getMainFileId() {
		return mainFileId;
	}

	/**
	 * @param mainFileId the mainFileId to set
	 */
	public void setMainFileId(Long mainFileId) {
		this.mainFileId = mainFileId;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	/**
	 * @return the formFileName
	 */
	public String getFormFileName() {
		return formFileName;
	}

	/**
	 * @param formFileName the formFileName to set
	 */
	public void setFormFileName(String formFileName) {
		this.formFileName = formFileName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocState() {
		return docState;
	}

	public void setDocState(String docState) {
		this.docState = docState;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getPriorityClass() {
		return priorityClass;
	}

	public void setPriorityClass(String priorityClass) {
		this.priorityClass = priorityClass;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}
    
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getActionValue() {
		return actionValue;
	}

	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}

	public Long getAsigneeId() {
		return asigneeId;
	}

	public void setAsigneeId(Long asigneeId) {
		this.asigneeId = asigneeId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the approveUser
	 */
	public String getApprovalUser() {
		return approvalUser;
	}

	/**
	 * @param approveUser the approveUser to set
	 */
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	/**
	 * @return the isOverDue
	 */
	public Boolean getIsOverdue() {
		return isOverdue;
	}

	/**
	 * @param isOverDue the isOverDue to set
	 */
	public void setIsOverdue(Boolean isOverdue) {
		this.isOverdue = isOverdue;
	}

    
    /**
     * Get docStateCode
     * @return String
     * @author taitt
     */
    public String getDocStateCode() {
        return docStateCode;
    }

    
    /**
     * Set docStateCode
     * @param   docStateCode
     *          type String
     * @return
     * @author  taitt
     */
    public void setDocStateCode(String docStateCode) {
        this.docStateCode = docStateCode;
    }

	public String getDocTitleIsRefer() {
		return docTitleIsRefer;
	}

	public void setDocTitleIsRefer(String docTitleIsRefer) {
		this.docTitleIsRefer = docTitleIsRefer;
	}

	public String getIsReference() {
		return isReference;
	}

	public void setIsReference(String isReference) {
		this.isReference = isReference;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
    
    public boolean getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(boolean isArchive) {
        this.isArchive = isArchive;
    }
	
}
