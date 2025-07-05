/*******************************************************************************
 * Class        ：ResSurveyDataDto
 * Created date ：2020/03/11
 * Lasted date  ：2020/03/11
 * Author       ：taitt
 * Change log   ：2020/03/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * ResSurveyDataDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResSurveyDataDto {

	private List<ResSurveyDto> surveyItems;
	
	private List<ResSurveyBpmDto> bpmButtons;

	public List<ResSurveyDto> getSurveyItems() {
		return surveyItems;
	}

	public void setSurveyItems(List<ResSurveyDto> surveyItems) {
		this.surveyItems = surveyItems;
	}

	public List<ResSurveyBpmDto> getBpmButtons() {
		return bpmButtons;
	}

	public void setBpmButtons(List<ResSurveyBpmDto> bpmButtons) {
		this.bpmButtons = bpmButtons;
	}
}
