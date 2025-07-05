/*******************************************************************************
 * Class        ：ReqSearchDocumentAPI
 * Created date ：2019/08/01
 * Lasted date  ：2019/08/01
 * Author       ：KhuongTH
 * Change log   ：2019/08/01：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import javax.validation.constraints.NotNull;

/**
 * ReqSearchDocumentAPI
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class ReqSearchDocumentAPI {

	private String keySearch;
	
	private Long serviceId;
	
	private int pageSize;
	
	private int pageIndex;

	@NotNull
	private int isPaging;

	public String getKeySearch() {
		return keySearch;
	}

	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getIsPaging() {
		return isPaging;
	}

	public void setIsPaging(int isPaging) {
		this.isPaging = isPaging;
	}
}
