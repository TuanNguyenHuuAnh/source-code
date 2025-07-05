package vn.com.unit.ep2p.dto;

import java.util.List;

public class ResDocumentListDto {
	
	/** data */
	List<ResDocumentDetailDto> data;
	
	private Long totalData;

	public List<ResDocumentDetailDto> getData() {
		return data;
	}

	public void setData(List<ResDocumentDetailDto> data) {
		this.data = data;
	}

	/**
	 * Get totalData
	 * @return Long
	 * @author taitt
	 */
	public Long getTotalData() {
		return totalData;
	}

	/**
	 * Set totalData
	 * @param   totalData
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setTotalData(Long totalData) {
		this.totalData = totalData;
	}	
	
}
