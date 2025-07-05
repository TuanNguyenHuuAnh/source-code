/*******************************************************************************
 * Class        :LdapAccountDto
 * Created date :2019/06/25
 * Lasted date  :2019/06/25
 * Author       :HungHT
 * Change log   :2019/06/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * LdapAccountDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class LdapAccountDto {

	private Long id;
	private String username;
	private String fullname;
	private String email;
	private String phone;
	private Long companyId;
	private String sessionKey;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String deletedBy;
	private Date deletedDate;
	private int status;

	/**
	* Set id
	* @param id
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setId(Long id) {
		this.id = id;
	}

	/**
	* Get id
	* @return Long
	* @author HungHT
	*/
	public Long getId() {
		return id;
	}

	/**
	* Set username
	* @param username
	*        type String
	* @return
	* @author HungHT
	*/
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	* Get username
	* @return String
	* @author HungHT
	*/
	public String getUsername() {
		return username;
	}

	/**
	* Set fullname
	* @param fullname
	*        type String
	* @return
	* @author HungHT
	*/
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	* Get fullname
	* @return String
	* @author HungHT
	*/
	public String getFullname() {
		return fullname;
	}

	/**
	* Set email
	* @param email
	*        type String
	* @return
	* @author HungHT
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	* Get email
	* @return String
	* @author HungHT
	*/
	public String getEmail() {
		return email;
	}

	/**
	* Set phone
	* @param phone
	*        type String
	* @return
	* @author HungHT
	*/
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	* Get phone
	* @return String
	* @author HungHT
	*/
	public String getPhone() {
		return phone;
	}

	/**
	* Set companyId
	* @param companyId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	* Get companyId
	* @return Long
	* @author HungHT
	*/
	public Long getCompanyId() {
		return companyId;
	}

	/**
	* Set sessionKey
	* @param sessionKey
	*        type String
	* @return
	* @author HungHT
	*/
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	* Get sessionKey
	* @return String
	* @author HungHT
	*/
	public String getSessionKey() {
		return sessionKey;
	}

	/**
	* Set createdBy
	* @param createdBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	* Get createdBy
	* @return String
	* @author HungHT
	*/
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	* Set createdDate
	* @param createdDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	* Get createdDate
	* @return Date
	* @author HungHT
	*/
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	* Set updatedBy
	* @param updatedBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	* Get updatedBy
	* @return String
	* @author HungHT
	*/
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	* Set updatedDate
	* @param updatedDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	* Get updatedDate
	* @return Date
	* @author HungHT
	*/
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	* Set deletedBy
	* @param deletedBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	/**
	* Get deletedBy
	* @return String
	* @author HungHT
	*/
	public String getDeletedBy() {
		return deletedBy;
	}

	/**
	* Set deletedDate
	* @param deletedDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	* Get deletedDate
	* @return Date
	* @author HungHT
	*/
	public Date getDeletedDate() {
		return deletedDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}