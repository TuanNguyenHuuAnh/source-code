/*******************************************************************************
 * Class        ：ReqMytaskListAPI
 * Created date ：2019/08/02
 * Lasted date  ：2019/08/02
 * Author       ：KhuongTH
 * Change log   ：2019/08/02：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * ReqMytaskListAPI
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class ReqMytaskListAPI {

	private String filterType;
	
	private String keySearch;
	
	private Long serviceId;
	
	private Integer pageSize;
	
	private Integer pageIndex;
	
	@NotNull
	private int isPaging;
	
    private String actUserId;
    private Long accountId;
    private List<String> groupIdList;
    
    private Long companyId;

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getIsPaging() {
		return isPaging;
	}

	public void setIsPaging(int isPaging) {
		this.isPaging = isPaging;
	}

	public List<String> getGroupIdList() {
		return groupIdList;
	}

	public void setGroupIdList(List<String> groupIdList) {
		this.groupIdList = groupIdList;
	}

	public String getKeySearch() {
		return keySearch;
	}

	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
	}

	/**
	 * Get companyId
	 * @return Long
	 * @author taitt
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * Set companyId
	 * @param   companyId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * Get actUserId
	 * @return String
	 * @author taitt
	 */
	public String getActUserId() {
		return actUserId;
	}

	/**
	 * Set actUserId
	 * @param   actUserId
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setActUserId(String actUserId) {
		this.actUserId = actUserId;
	}

	/**
	 * Get accountId
	 * @return Long
	 * @author taitt
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * Set accountId
	 * @param   accountId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	

}
