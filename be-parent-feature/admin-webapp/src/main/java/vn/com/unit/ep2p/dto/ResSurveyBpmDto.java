/*******************************************************************************
 * Class        ：ResSurveyBpmDto
 * Created date ：2020/03/11
 * Lasted date  ：2020/03/11
 * Author       ：taitt
 * Change log   ：2020/03/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ResSurveyBpmDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResSurveyBpmDto {

	private String actionName;
	
	private Long actionId;

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
}
