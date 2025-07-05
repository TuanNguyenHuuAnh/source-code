/*******************************************************************************
 * Class        ：RoomClientSearchDto
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

/**
 * RoomClientSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class RoomClientSearchDto {
	/** status */
	private Integer status;

	/** agent */
	private String agent;

	/** agentGroup */
	private String agentGroup;

	/** fieldValues */
	private List<String> fieldValues;

	/** fieldSearch */
	private String fieldSearch;

	private String fromDateSearch;

	private String toDateSearch;

	private Date fromDate;

	private Date toDate;

	private String productCategoryName;

	private String fullname;
	
	private String service;
	
	private Integer pageSize;
	
	private String lang;
	
	private String phone;
	
	private String email;

	/**
	 * Get status
	 * 
	 * @return int
	 * @author phunghn
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Set status
	 * 
	 * @param status type int
	 * @return
	 * @author phunghn
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Get agent
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * Set agent
	 * 
	 * @param agent type String
	 * @return
	 * @author phunghn
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * Get agentGroup
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getAgentGroup() {
		return agentGroup;
	}

	/**
	 * Set agentGroup
	 * 
	 * @param agentGroup type String
	 * @return
	 * @author phunghn
	 */
	public void setAgentGroup(String agentGroup) {
		this.agentGroup = agentGroup;
	}

	/**
	 * Get fieldValues
	 * 
	 * @return List<String>
	 * @author phunghn
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}

	/**
	 * Set fieldValues
	 * 
	 * @param fieldValues type List<String>
	 * @return
	 * @author phunghn
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	/**
	 * Get fieldSearch
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}

	/**
	 * Set fieldSearch
	 * 
	 * @param fieldSearch type String
	 * @return
	 * @author phunghn
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	/**
	 * Get fromDateSearch
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getFromDateSearch() {
		return fromDateSearch;
	}

	/**
	 * Set fromDateSearch
	 * 
	 * @param fromDateSearch type String
	 * @return
	 * @author phunghn
	 */
	public void setFromDateSearch(String fromDateSearch) {
		this.fromDateSearch = fromDateSearch;
	}

	/**
	 * Get toDateSearch
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getToDateSearch() {
		return toDateSearch;
	}

	/**
	 * Set toDateSearch
	 * 
	 * @param toDateSearch type String
	 * @return
	 * @author phunghn
	 */
	public void setToDateSearch(String toDateSearch) {
		this.toDateSearch = toDateSearch;
	}

	/**
	 * Get fromDate
	 * 
	 * @return Date
	 * @author phunghn
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * Set fromDate
	 * 
	 * @param fromDate type Date
	 * @return
	 * @author phunghn
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Get toDate
	 * 
	 * @return Date
	 * @author phunghn
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * Set toDate
	 * 
	 * @param toDate type Date
	 * @return
	 * @author phunghn
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the productCategoryName
	 */
	public String getProductCategoryName() {
		return productCategoryName;
	}

	/**
	 * @param productCategoryName the productCategoryName to set
	 */
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	/**
	 * @return the service
	 * @author taitm
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 * @author taitm
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return the pageSize
	 * @author taitm
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 * @author taitm
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
