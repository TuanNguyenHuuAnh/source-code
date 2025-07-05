/*******************************************************************************
 * Class        ：DocumentTypeSearchDto
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * NewsTypeSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentTypeSearchDto {

	/** code */
	private String code;

	/** description */
	private String description;

	/** label */
	private String title;

	/** languageCode */
	private String languageCode;

	private String customerTypeCode;

	private String name;

	/** fieldValues */
	private List<String> fieldValues;

	/** fieldSearch */
	private String fieldSearch;

	/** url */
	private String url;

	private Integer pageSize;

	private Long customerTypeId;

	private Integer enabled;

	private Integer status;

	private String statusName;

	private String token;

	private String comment;
	/**
	 * Get languageCode
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Set languageCode
	 * 
	 * @param languageCode type String
	 * @return
	 * @author thuydtn
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Get code
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * 
	 * @param code type String
	 * @return
	 * @author thuydtn
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get fieldValues
	 * 
	 * @return List<String>
	 * @author thuydtn
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}

	/**
	 * Set fieldValues
	 * 
	 * @param fieldValues type List<String>
	 * @return
	 * @author thuydtn
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	/**
	 * Get fieldSearch
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}

	/**
	 * Set fieldSearch
	 * 
	 * @param fieldSearch type String
	 * @return
	 * @author thuydtn
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = CmsUtils.trimForSearch(fieldSearch);
	}

	/**
	 * Get url
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set url
	 * 
	 * @param url type String
	 * @return
	 * @author thuydtn
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get description
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description
	 * 
	 * @param description type String
	 * @return
	 * @author thuydtn
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get title
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * 
	 * @param title type String
	 * @return
	 * @author thuydtn
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get customerCode
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	/**
	 * Set customerCode
	 * 
	 * @param customerCode type String
	 * @return
	 * @author thuydtn
	 */
	public void setCustomerTypeCode(String customerCode) {
		this.customerTypeCode = customerCode;
	}

	/**
	 * Get name
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * 
	 * @param name type String
	 * @return
	 * @author thuydtn
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the statusName
	 * @author taitm
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 * @author taitm
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
