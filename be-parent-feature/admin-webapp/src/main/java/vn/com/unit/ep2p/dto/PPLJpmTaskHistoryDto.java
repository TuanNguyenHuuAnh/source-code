/*******************************************************************************
 * Class        ：JpmTaskHistoryDto
 * Created date ：2019/07/27
 * Lasted date  ：2019/07/27
 * Author       ：KhuongTH
 * Change log   ：2019/07/27：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * JpmTaskHistoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class PPLJpmTaskHistoryDto {

    private Long actionId;
    private String actionName;
    private String actionTimeWithFormat;
    private Date actionTime;
    private String stepName;
    private Integer stepNo;
    private String taskComment;
    private String buttonName;
    private String actTaskId;
    private String langCode;
    private String stepCode;

    private String assigneeUser;
    private String assigneeFullname;
    private String assigneeEmail;
    private String assigneeNumberPhone;
    private Long assigneeDepartmentId;
    private String assigneeDepartmentName;
    private Long assigneePositionId;
    private String assigneePositionName;
    private String assigneeCmnd;
    private String avatar;
    private Long avatarRepoId;

    private Date endTime;
    private Long duration;
    private String endTimeWithFormat;
    private boolean showTaskComment;
    
    private Integer charNumOfTaskComment;
    private String taskCommentFully;

    public String getAssigneeUser() {
        return assigneeUser;
    }

    public void setAssigneeUser(String assigneeUser) {
        this.assigneeUser = assigneeUser;
    }

    public String getAssigneeFullname() {
        return assigneeFullname;
    }

    public void setAssigneeFullname(String assigneeFullname) {
        this.assigneeFullname = assigneeFullname;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = StringUtils.isNotBlank(taskComment) ? taskComment : StringUtils.EMPTY;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getAvatarRepoId() {
        return avatarRepoId;
    }

    public void setAvatarRepoId(Long avatarRepoId) {
        this.avatarRepoId = avatarRepoId;
    }

    public Integer getStepNo() {
        return stepNo;
    }

    public void setStepNo(Integer stepNo) {
        this.stepNo = stepNo;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    /**
     * Get actionTimeWithFormat
     * 
     * @return String
     * @author HungHT
     */
    public String getActionTimeWithFormat() {
        return actionTimeWithFormat;
    }

    /**
     * Get buttonName
     * 
     * @return String
     * @author HungHT
     */
    public String getButtonName() {
        return buttonName;
    }

    /**
     * Set buttonName
     * 
     * @param buttonName
     *            type String
     * @return
     * @author HungHT
     */
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getAssigneeNumberPhone() {
        return assigneeNumberPhone;
    }

    public void setAssigneeNumberPhone(String assigneeNumberPhone) {
        this.assigneeNumberPhone = assigneeNumberPhone;
    }

    public Long getAssigneeDepartmentId() {
        return assigneeDepartmentId;
    }

    public void setAssigneeDepartmentId(Long assigneeDepartmentId) {
        this.assigneeDepartmentId = assigneeDepartmentId;
    }

    public String getAssigneeDepartmentName() {
        return assigneeDepartmentName;
    }

    public void setAssigneeDepartmentName(String assigneeDepartmentName) {
        this.assigneeDepartmentName = assigneeDepartmentName;
    }

    public Long getAssigneePositionId() {
        return assigneePositionId;
    }

    public void setAssigneePositionId(Long assigneePositionId) {
        this.assigneePositionId = assigneePositionId;
    }

    public String getAssigneePositionName() {
        return assigneePositionName;
    }

    public void setAssigneePositionName(String assigneePositionName) {
        this.assigneePositionName = assigneePositionName;
    }

    public String getActTaskId() {
        return actTaskId;
    }

    public void setActTaskId(String actTaskId) {
        this.actTaskId = actTaskId;
    }

    public void setActionTimeWithFormat(String actionTimeWithFormat) {
        this.actionTimeWithFormat = actionTimeWithFormat;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    /**
     * @return the assigneeCmnd
     */
    public String getAssigneeCmnd() {
        return assigneeCmnd;
    }

    /**
     * @param assigneeCmnd
     *            the assigneeCmnd to set
     */
    public void setAssigneeCmnd(String assigneeCmnd) {
        this.assigneeCmnd = assigneeCmnd;
    }

    /**
     * @return the stepCode
     */
    public String getStepCode() {
        return stepCode;
    }

    /**
     * @param stepCode
     *            the stepCode to set
     */
    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getEndTimeWithFormat() {
        return endTimeWithFormat;
    }

    public void setEndTimeWithFormat(String endTimeWithFormat) {
        this.endTimeWithFormat = endTimeWithFormat;
    }

    public boolean getShowTaskComment() {
        return showTaskComment;
    }

    public void setShowTaskComment(boolean showTaskComment) {
        this.showTaskComment = showTaskComment;
    }
    
    public Integer getCharNumOfTaskComment() {
        return charNumOfTaskComment;
    }

    public void setCharNumOfTaskComment(Integer charNumOfTaskComment) {
        this.charNumOfTaskComment = charNumOfTaskComment;
    }
    
    public String getTaskCommentFully() {
        return taskCommentFully;
    }
    
    public void setTaskCommentFully(String taskCommentFully) {
        this.taskCommentFully = taskCommentFully;
    }

}
