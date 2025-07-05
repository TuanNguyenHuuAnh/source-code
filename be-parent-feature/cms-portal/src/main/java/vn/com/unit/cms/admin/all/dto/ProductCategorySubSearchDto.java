/*******************************************************************************
 * Class        ：ProductCategorySubSearchDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.util.Util;

/**
 * ProductCategorySubSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductCategorySubSearchDto {

	/** code */
	private String code;

	/** description */
	private String description;

	/** title */
	private String title;

	/** languageCode */
	private String languageCode;

	/** customerTypeName */
	private String customerTypeName;

	private String categoryName;

	private Long customerTypeId;

	private Long categoryId;

	/** fieldValues */
	private List<String> fieldValues;

	/** fieldSearch */
	private String fieldSearch;

	/** url */
	private String url;

	private String statusText;

	private List<SortOrderDto> sortOderList;

	private Integer pageSize;

	private Integer enabled;

	private Integer priority;

	private String statusName;

	private String businessCode;

	/**
	 * Get code
	 * 
	 * @return String
	 * @author hand
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * 
	 * @param code type String
	 * @return
	 * @author hand
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get description
	 * 
	 * @return String
	 * @author hand
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description
	 * 
	 * @param description type String
	 * @return
	 * @author hand
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get title
	 * 
	 * @return String
	 * @author hand
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * 
	 * @param title type String
	 * @return
	 * @author hand
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get languageCode
	 * 
	 * @return String
	 * @author hand
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Set languageCode
	 * 
	 * @param languageCode type String
	 * @return
	 * @author hand
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Get customerTypeName
	 * 
	 * @return String
	 * @author hand
	 */
	public String getCustomerTypeName() {
		return customerTypeName;
	}

	/**
	 * Set customerTypeName
	 * 
	 * @param customerTypeName type String
	 * @return
	 * @author hand
	 */
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	/**
	 * Get fieldValues
	 * 
	 * @return List<String>
	 * @author hand
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}

	/**
	 * Set fieldValues
	 * 
	 * @param fieldValues type List<String>
	 * @return
	 * @author hand
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	/**
	 * Get fieldSearch
	 * 
	 * @return String
	 * @author hand
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}

	/**
	 * Set fieldSearch
	 * 
	 * @param fieldSearch type String
	 * @return
	 * @author hand
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = CmsUtils.trimForSearch(fieldSearch);
	}

	/**
	 * Get url
	 * 
	 * @return String
	 * @author hand
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set url
	 * 
	 * @param url type String
	 * @return
	 * @author hand
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get sortOderList
	 * 
	 * @return List<SortOrderDto>
	 * @author TranLTH
	 */
	public List<SortOrderDto> getSortOderList() {
		return sortOderList;
	}

	/**
	 * Set sortOderList
	 * 
	 * @param sortOderList type List<SortOrderDto>
	 * @return
	 * @author TranLTH
	 */
	public void setSortOderList(List<SortOrderDto> sortOderList) {
		this.sortOderList = sortOderList;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the businessCode
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * @param businessCode the businessCode to set
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

}