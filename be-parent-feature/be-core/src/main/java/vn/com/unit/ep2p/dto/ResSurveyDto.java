/*******************************************************************************
 * Class        ：ResSurveyDto
 * Created date ：2020/03/11
 * Lasted date  ：2020/03/11
 * Author       ：taitt
 * Change log   ：2020/03/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ResSurveyDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResSurveyDto {

	private String ownerName;
	
	private Long ownerId;
	
	private String executorName;
	
	private Long executorId;
	
	private String actionName;
	
	private Long actionId;
	
	private String executeDate;
	
	private String comment;
	
	private Long surveyId;
	
	private String isExecuted;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	public Long getExecutorId() {
		return executorId;
	}

	public void setExecutorId(Long executorId) {
		this.executorId = executorId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public String getIsExecuted() {
		return isExecuted;
	}

	public void setIsExecuted(String isExecuted) {
		this.isExecuted = isExecuted;
	}
}
