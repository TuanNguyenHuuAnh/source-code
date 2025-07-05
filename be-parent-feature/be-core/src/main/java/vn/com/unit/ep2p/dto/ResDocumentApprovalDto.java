package vn.com.unit.ep2p.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResDocumentApprovalDto {

	private String approver;
		
	private String approverName;
	
	private String position;
	
	private String phoneNumber;	

	private String department;
	
	private String actionName;
	
//	private String taskStatus;
	
	private String stepName;
	
	private String statusDate;
	
	@JsonInclude(Include.NON_NULL)
	private Date dated;
	
	@JsonInclude(Include.NON_NULL)
	private String actTaskId;
	
	@JsonInclude(Include.NON_NULL)
	private Date submitDate;
	
	@JsonInclude(Include.NON_NULL)
	private Long approverId;
		
	private String comments;
	
	private boolean showTaskComment;

    
    /**
     * Get approver
     * @return String
     * @author taitt
     */
    public String getApprover() {
        return approver;
    }

    
    /**
     * Set approver
     * @param   approver
     *          type String
     * @return
     * @author  taitt
     */
    public void setApprover(String approver) {
        this.approver = approver;
    }


    /**getStepName
     * 
     * @return String
     * 		
     * @author KhuongTH
     */
    public String getStepName() {
		return stepName;
	}

	/**setStepName
	 * 
	 * @param step void
	 * 		
	 * @author KhuongTH
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}


	/**
     * Get statusDate
     * @return String
     * @author taitt
     */
    public String getStatusDate() {
        return statusDate;
    }

    
    /**
     * Set statusDate
     * @param   statusDate
     *          type String
     * @return
     * @author  taitt
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    
    /**
     * Get dated
     * @return Date
     * @author taitt
     */
    public Date getDated() {
        return dated;
    }

    
    /**
     * Set dated
     * @param   dated
     *          type Date
     * @return
     * @author  taitt
     */
    public void setDated(Date dated) {
        this.dated = dated;
    }

    
    /**
     * Get comments
     * @return String
     * @author taitt
     */
    public String getComments() {
        return comments;
    }

    
    /**
     * Set comments
     * @param   comments
     *          type String
     * @return
     * @author  taitt
     */
    public void setComments(String comments) {
        this.comments = comments;
    }


	public String getApproverName() {
		return approverName;
	}


	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	

	public String getActionName() {
		return actionName;
	}


	public void setActionName(String actionName) {
		this.actionName = actionName;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//
//	public String getTaskStatus() {
//		return taskStatus;
//	}
//
//
//	public void setTaskStatus(String taskStatus) {
//		this.taskStatus = taskStatus;
//	}


	/**
	 * Get actTaskId
	 * @return String
	 * @author taitt
	 */
	public String getActTaskId() {
		return actTaskId;
	}


	/**
	 * Set actTaskId
	 * @param   actTaskId
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setActTaskId(String actTaskId) {
		this.actTaskId = actTaskId;
	}


	/**
	 * Get submitDate
	 * @return Date
	 * @author taitt
	 */
	public Date getSubmitDate() {
		return submitDate;
	}


	/**
	 * Set submitDate
	 * @param   submitDate
	 *          type Date
	 * @return
	 * @author  taitt
	 */
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}


	/**
	 * Get approverId
	 * @return Long
	 * @author taitt
	 */
	public Long getApproverId() {
		return approverId;
	}


	/**
	 * Set approverId
	 * @param   approverId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}


    
    public boolean isShowTaskComment() {
        return showTaskComment;
    }


    
    public void setShowTaskComment(boolean showTaskComment) {
        this.showTaskComment = showTaskComment;
    }
	
}
