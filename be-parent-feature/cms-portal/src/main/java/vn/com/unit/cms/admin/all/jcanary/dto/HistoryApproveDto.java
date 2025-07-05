/*******************************************************************************
 * Class        HistoryApproveDto
 * Created date 2017/03/24
 * Lasted date  2017/03/24
 * Author       TranLTH
 * Change log   2017/03/2401-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.Date;

/**
 * HistoryApproveDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class HistoryApproveDto {
    private Long historyApproveId;
    
    private Long referenceId;
    
    private Long processId;
    
    private String approver;
    
    private Date approverDate;
    
    private String comment;
    
    private Long processStep;
    
    private String statusCode;
    
    private String statusName;
    
    private String referenceType;
    
    private String actionId;
    
    private int oldStep;
    
    private String actionName;
    
    private String statusNameOld;
    
    private Long accountId;

    /**
     * Get historyApproveId
     * @return Long
     * @author TranLTH
     */
    public Long getHistoryApproveId() {
        return historyApproveId;
    }

    /**
     * Set historyApproveId
     * @param   historyApproveId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setHistoryApproveId(Long historyApproveId) {
        this.historyApproveId = historyApproveId;
    }

    /**
     * Get processId
     * @return Long
     * @author TranLTH
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get approver
     * @return String
     * @author TranLTH
     */
    public String getApprover() {
        return approver;
    }

    /**
     * Set approver
     * @param   approver
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setApprover(String approver) {
        this.approver = approver;
    }

    /**
     * Get approverDate
     * @return Date
     * @author TranLTH
     */
    public Date getApproverDate() {
        return approverDate;
    }

    /**
     * Set approverDate
     * @param   approverDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setApproverDate(Date approverDate) {
        this.approverDate = approverDate;
    }

    /**
     * Get comment
     * @return String
     * @author TranLTH
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     * @param   comment
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get processStep
     * @return Long
     * @author TranLTH
     */
    public Long getProcessStep() {
        return processStep;
    }

    /**
     * Set processStep
     * @param   processStep
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setProcessStep(Long processStep) {
        this.processStep = processStep;
    }

    /**
     * Get statusCode
     * @return String
     * @author TranLTH
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * @param   statusCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get statusName
     * @return String
     * @author TranLTH
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Set statusName
     * @param   statusName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Get referenceId
     * @return Long
     * @author TranLTH
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * @param   referenceId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get referenceType
     * @return String
     * @author TranLTH
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Set referenceType
     * @param   referenceType
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public int getOldStep() {
		return oldStep;
	}

	public void setOldStep(int oldStep) {
		this.oldStep = oldStep;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getStatusNameOld() {
		return statusNameOld;
	}

	public void setStatusNameOld(String statusNameOld) {
		this.statusNameOld = statusNameOld;
	}

    /**
     * @return the accountId
     * @author taitm
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     *            the accountId to set
     * @author taitm
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
