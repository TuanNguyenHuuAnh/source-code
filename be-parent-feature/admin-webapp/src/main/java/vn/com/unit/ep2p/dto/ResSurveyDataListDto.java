/*******************************************************************************
 * Class        ：ResSurveyDataListDto
 * Created date ：2020/03/11
 * Lasted date  ：2020/03/11
 * Author       ：taitt
 * Change log   ：2020/03/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ResSurveyDataListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResSurveyDataListDto {

	private String stepName;
	
	private Long totalData;
	
	private Object data;

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public Long getTotalData() {
		return totalData;
	}

	public void setTotalData(Long totalData) {
		this.totalData = totalData;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
