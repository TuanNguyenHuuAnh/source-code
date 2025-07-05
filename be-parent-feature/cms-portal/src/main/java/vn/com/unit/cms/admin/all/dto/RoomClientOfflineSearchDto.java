/*******************************************************************************
 * Class        ：RoomClientOfflineSearchDto
 * Created date ：2017/05/15
 * Lasted date  ：2017/05/15
 * Author       ：phunghn
 * Change log   ：2017/05/15：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

/**
 * RoomClientOfflineSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class RoomClientOfflineSearchDto {
	/** fullname */
	String fullname;

	/** email */
	String email;

	/** phone */
	String phone;

	private String language;

	/** fieldValues */
	private List<String> fieldValues;

	/** fieldSearch */
	private String fieldSearch;

	private Date createdDate;

	private Integer pageSize;

	private Date toDate;

	private Date fromDate;

	/**
	 * Get fullname
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Set fullname
	 * 
	 * @param fullname type String
	 * @return
	 * @author phunghn
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Get email
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email
	 * 
	 * @param email type String
	 * @return
	 * @author phunghn
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get phone
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set phone
	 * 
	 * @param phone type String
	 * @return
	 * @author phunghn
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

}
