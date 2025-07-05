/*******************************************************************************
 * Class        ：ReqSearchDocumentAPI
 * Created date ：2019/08/01
 * Lasted date  ：2019/08/01
 * Author       ：KhuongTH
 * Change log   ：2019/08/01：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * ReqSearchDocumentAPI
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class ResSearchDocumentDto {

	private Long totalDocuments;
	
	private List<ResDocumentDetailDto> data;

	public Long getTotalDocuments() {
		return totalDocuments;
	}

	public void setTotalDocuments(Long totalDocuments) {
		this.totalDocuments = totalDocuments;
	}

	public List<ResDocumentDetailDto> getData() {
		return data;
	}

	public void setData(List<ResDocumentDetailDto> data) {
		this.data = data;
	}

}
