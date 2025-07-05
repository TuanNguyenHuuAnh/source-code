package vn.com.unit.ep2p.dto;

import java.util.Date;

public class PPLJpmStepSurveyDto {

    private Long surveyId;

    private String stepName;

    private String userName;
    
    private Long userId;

    private String actionName;

    private String executor;
    
    private Long executorId;

    private String commentContent;

    private String opinion;

    private Date executeDate;

    private Long stepId;

    private Long actionId;

    private String actTaskId;

    private String actTaskParentId;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    /**
     * @return the stepId
     */
    public Long getStepId() {
        return stepId;
    }

    /**
     * @param stepId
     *            the stepId to set
     */
    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    /**
     * @return the actTaskId
     */
    public String getActTaskId() {
        return actTaskId;
    }

    /**
     * @param actTaskId
     *            the actTaskId to set
     */
    public void setActTaskId(String actTaskId) {
        this.actTaskId = actTaskId;
    }

    public String getActTaskParentId() {
        return actTaskParentId;
    }

    public void setActTaskParentId(String actTaskParentId) {
        this.actTaskParentId = actTaskParentId;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getExecutorId() {
		return executorId;
	}

	public void setExecutorId(Long executorId) {
		this.executorId = executorId;
	}

}
