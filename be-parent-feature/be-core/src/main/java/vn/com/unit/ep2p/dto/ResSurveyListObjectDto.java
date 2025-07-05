/*******************************************************************************
 * Class        ：ResSurveyListObjectDto
 * Created date ：2020/03/11
 * Lasted date  ：2020/03/11
 * Author       ：taitt
 * Change log   ：2020/03/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * ResSurveyListObjectDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ResSurveyListObjectDto {

	private Long totalData;
	
	private List<ResSurveyDataListDto> data;

	public Long getTotalData() {
		return totalData;
	}

	public void setTotalData(Long totalData) {
		this.totalData = totalData;
	}

	public List<ResSurveyDataListDto> getData() {
		return data;
	}

	public void setData(List<ResSurveyDataListDto> data) {
		this.data = data;
	}

	
}
