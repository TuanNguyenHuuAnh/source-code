package vn.com.unit.cms.admin.all.dto;

import java.util.LinkedHashMap;

public class EmailCommonDto {
    private Long id;

    private String subject;

    // tên chức năng
    private String actionName;

    private String buttonId;

    private String buttonAction;

    private Long processId;

    private String currItem;

    private String referenceType;

    private Integer status;

    private Integer oldStatus;

    private String url;

    private LinkedHashMap<String, String> content;

    private String comment;

    /**
     * @return the id
     * @author taitm
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     * @author taitm
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the subject
     * @author taitm
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     * @author taitm
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the actionName
     * @author taitm
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @param actionName
     *            the actionName to set
     * @author taitm
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * @return the buttonId
     * @author taitm
     */
    public String getButtonId() {
        return buttonId;
    }

    /**
     * @param buttonId
     *            the buttonId to set
     * @author taitm
     */
    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    /**
     * @return the buttonAction
     * @author taitm
     */
    public String getButtonAction() {
        return buttonAction;
    }

    /**
     * @param buttonAction
     *            the buttonAction to set
     * @author taitm
     */
    public void setButtonAction(String buttonAction) {
        this.buttonAction = buttonAction;
    }

    /**
     * @return the processId
     * @author taitm
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * @param processId
     *            the processId to set
     * @author taitm
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * @return the currItem
     * @author taitm
     */
    public String getCurrItem() {
        return currItem;
    }

    /**
     * @param currItem
     *            the currItem to set
     * @author taitm
     */
    public void setCurrItem(String currItem) {
        this.currItem = currItem;
    }

    /**
     * @return the referenceType
     * @author taitm
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * @param referenceType
     *            the referenceType to set
     * @author taitm
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * @return the status
     * @author taitm
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     * @author taitm
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the oldStatus
     * @author taitm
     */
    public Integer getOldStatus() {
        return oldStatus;
    }

    /**
     * @param oldStatus
     *            the oldStatus to set
     * @author taitm
     */
    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    /**
     * @return the url
     * @author taitm
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     * @author taitm
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the content
     * @author taitm
     */
    public LinkedHashMap<String, String> getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     * @author taitm
     */
    public void setContent(LinkedHashMap<String, String> content) {
        this.content = content;
    }

    /**
     * @return the comment
     * @author taitm
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     *            the comment to set
     * @author taitm
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
