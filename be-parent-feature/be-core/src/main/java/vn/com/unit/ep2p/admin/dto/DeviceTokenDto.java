package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

public class DeviceTokenDto {

	private Long id;
	private Long accountId;
	private String deviceToken;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
	private String langCode;

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
	* Set accountId
	* @param accountId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	* Get accountId
	* @return Long
	* @author HungHT
	*/
	public Long getAccountId() {
		return accountId;
	}
		

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
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

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
}
