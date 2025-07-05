/*******************************************************************************
 * Class        AbstractProcessDto
 * Created date 2018/11/13
 * Lasted date  2018/11/13
 * Author       VinhLT
 * Change log   2018/11/1301-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.ArrayList;
import java.util.Date;

import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;

//import vn.com.unit.constant.Message;
//import vn.com.unit.constant.MessageList;

/**
 * AbstractProcessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public abstract class AbstractProcessDto {

    private Long processId;

    private Long stepId;

    private Integer stepNo;
    
    private String statusCode;
    
    private String statusName;

    private String owner;

    private String assignee;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private Date deletedDate;

    private String deletedBy;

    private MessageList messageList;

    private String comments;

    private String businessCode;

    private String refAttachment;

    private int deleteFlag = 0;

    private String companyCode;

    private String processStatusCode;

    private MessageList messConcurrent;

    private boolean isSendMail = true;
    
    private String languageCode;

    /**
     * Get processId
     * 
     * @return Long
     * @author VinhLT
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * 
     * @param processId type Long
     * @return
     * @author VinhLT
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get stepNo
     * 
     * @return Integer
     * @author VinhLT
     */
    public final Integer getStepNo() {
        return stepNo;
    }

    /**
     * Set stepNo
     * 
     * @param stepNo type Integer
     * @return
     * @author VinhLT
     */
    public final void setStepNo(Integer stepNo) {
        this.stepNo = stepNo;
    }

    /**
     * Get owner
     * 
     * @return String
     * @author VinhLT
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set owner
     * 
     * @param owner type String
     * @return
     * @author VinhLT
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get assignee
     * 
     * @return String
     * @author VinhLT
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * Set assignee
     * 
     * @param assignee type String
     * @return
     * @author VinhLT
     */
    public void setAssignee(String assignee) {
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
     * Get createdBy
     * 
     * @return String
     * @author VinhLT
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdBy
     * 
     * @param createdBy type String
     * @return
     * @author VinhLT
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
     * Get updatedBy
     * 
     * @return String
     * @author VinhLT
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set updatedBy
     * 
     * @param updatedBy type String
     * @return
     * @author VinhLT
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get messageList
     * 
     * @return MessageList
     * @author VinhLT
     */
    public MessageList getMessageList() {
        return messageList;
    }

    /**
     * Set messageList
     * 
     * @param messageList type MessageList
     * @return
     * @author VinhLT
     */
    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }

    /**
     * addMessage
     *
     * @param status
     * @param content
     * @author VinhLT
     */
    public void addMessage(String status, String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(status, String.valueOf(content));

    }

    /**
     * addMessageSuccess
     *
     * @param content
     * @author VinhLT
     */
    public void addMessageSuccess(String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(Message.SUCCESS, String.valueOf(content));

    }

    /**
     * addMessageError
     *
     * @param content
     * @author VinhLT
     */
    public void addMessageError(String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(Message.ERROR, String.valueOf(content));

    }

    /**
     * addMessageWarning
     *
     * @param content
     * @author VinhLT
     */
    public void addMessageWarning(String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(Message.WARNING, String.valueOf(content));

    }

    /**
     * clearMessageList
     *
     * @author VinhLT
     */
    public void clearMessageList() {
        if (messageList != null) {
            messageList.setMessages(new ArrayList<>());
        }
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
     * Get refAttachment
     * 
     * @return String
     * @author VinhLT
     */
    public String getRefAttachment() {
        return refAttachment;
    }

    /**
     * Set refAttachment
     * 
     * @param refAttachment type String
     * @return
     * @author VinhLT
     */
    public void setRefAttachment(String refAttachment) {
        this.refAttachment = refAttachment;
    }

    /**
     * Get deletedDate
     * 
     * @return Date
     * @author VinhLT
     */
    public Date getDeletedDate() {
        return deletedDate;
    }

    /**
     * Set deletedDate
     * 
     * @param deletedDate type Date
     * @return
     * @author VinhLT
     */
    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    /**
     * Get deletedBy
     * 
     * @return String
     * @author VinhLT
     */
    public String getDeletedBy() {
        return deletedBy;
    }

    /**
     * Set deletedBy
     * 
     * @param deletedBy type String
     * @return
     * @author VinhLT
     */
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    /**
     * Get deleteFlag
     * 
     * @return int
     * @author VinhLT
     */
    public int getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Set deleteFlag
     * 
     * @param deleteFlag type int
     * @return
     * @author VinhLT
     */
    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * Get companyCode
     * 
     * @return String
     * @author VinhLT
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * Set companyCode
     * 
     * @param companyCode type String
     * @return
     * @author VinhLT
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getProcessStatusCode() {
        return processStatusCode;
    }

    public void setProcessStatusCode(String processStatusCode) {
        this.processStatusCode = processStatusCode;
    }

    /**
     * Get messConcurrent
     * 
     * @return MessageList
     * @author VinhLT
     */
    public MessageList getMessConcurrent() {
        return messConcurrent;
    }

    /**
     * Set messConcurrent
     * 
     * @param messConcurrent type MessageList
     * @return
     * @author VinhLT
     */
    public void setMessConcurrent(MessageList messConcurrent) {
        this.messConcurrent = messConcurrent;
    }

    /**
     * @return the isSkipNextStep
     * @author taitm
     * @date Mar 4, 2020
     */
    public boolean isSendMail() {
        return isSendMail;
    }

    /**
     * @param isSkipNextStep the isSkipNextStep to set
     * @author taitm
     * @date Mar 4, 2020
     */
    public void setSendMail(boolean isSkipNextStep) {
        this.isSendMail = isSkipNextStep;
    }

    /**
     * @return the languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @param languageCode the languageCode to set
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
