package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

public class SocketServerDto {
	private String username;
	private String fullname;
	private String socketId;
	private String email;
	private String phone;
	private Date createdDate;

	/**
	 * @return the username
	 * @author taitm
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 * @author taitm
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the fullname
	 * @author taitm
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname the fullname to set
	 * @author taitm
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the socketId
	 * @author taitm
	 */
	public String getSocketId() {
		return socketId;
	}

	/**
	 * @param socketId the socketId to set
	 * @author taitm
	 */
	public void setSocketId(String socketId) {
		this.socketId = socketId;
	}

	/**
	 * @return the email
	 * @author taitm
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 * @author taitm
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 * @author taitm
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 * @author taitm
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
