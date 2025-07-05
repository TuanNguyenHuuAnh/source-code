/*******************************************************************************
 * Class        ：RoomClientOfflineDto
 * Created date ：2017/05/15
 * Lasted date  ：2017/05/15
 * Author       ：phunghn
 * Change log   ：2017/05/15：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * RoomClientOfflineDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */

public class RoomClientOfflineDto {

	/** id */
	private Long id;

	/** email */
	private String email;

	/** fullname */
	private String fullname;

	/** phone */
	private String phone;

	/** message */
	private String message;

	/** createDate */
	private Date createdDate;

	/** createBy */
	private String createdBy;

	/** updateDate */
	private Date updatedDate;

	/** updateBy */
	private String updatedBy;

	/** deleteDate */
	private Date deletedDate;

	/** deleteBy */
	private String deletedBy;

	private String messageFeedback;

	private String agentName;

	private String service;

	private String nickname;

	private int stt;

	/**
	 * Get id
	 * 
	 * @return int
	 * @author phunghn
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id type int
	 * @return
	 * @author phunghn
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Get message
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set message
	 * 
	 * @param message type String
	 * @return
	 * @author phunghn
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get createdDate
	 * 
	 * @return Date
	 * @author phunghn
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Set createdDate
	 * 
	 * @param createdDate type Date
	 * @return
	 * @author phunghn
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Get createdBy
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy
	 * 
	 * @param createdBy type String
	 * @return
	 * @author phunghn
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get updatedDate
	 * 
	 * @return Date
	 * @author phunghn
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * Set updatedDate
	 * 
	 * @param updatedDate type Date
	 * @return
	 * @author phunghn
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * Get deletedDate
	 * 
	 * @return Date
	 * @author phunghn
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * Set deletedDate
	 * 
	 * @param deletedDate type Date
	 * @return
	 * @author phunghn
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * Get deletedBy
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getDeletedBy() {
		return deletedBy;
	}

	/**
	 * Set deletedBy
	 * 
	 * @param deletedBy type String
	 * @return
	 * @author phunghn
	 */
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	/**
	 * Get updatedBy
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updatedBy
	 * 
	 * @param updatedBy type String
	 * @return
	 * @author phunghn
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Get messageFeedback
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getMessageFeedback() {
		return messageFeedback;
	}

	/**
	 * Set messageFeedback
	 * 
	 * @param messageFeedback type String
	 * @return
	 * @author phunghn
	 */
	public void setMessageFeedback(String messageFeedback) {
		this.messageFeedback = messageFeedback;
	}

	/**
	 * Get agentName
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * Set agentName
	 * 
	 * @param agentName type String
	 * @return
	 * @author phunghn
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return the service
	 * @author taitm
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 * @author taitm
	 */
	public void setService(String service) {
		this.service = service;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}
}
