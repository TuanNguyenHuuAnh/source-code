package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ContactEmailEditDto {
	private Long id;
	private String content;
	private String comment;
	private String commentCode;

	private Date fromDate;
	private Date toDate;
	private String processingStatus;
	private String service;
	private String motive;
	private String emailSubject;
	private String fullName;
	private Long customerId;

	public ContactEmailEditDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentCode() {
		return commentCode;
	}

	public void setCommentCode(String commentCode) {
		if (StringUtils.isEmpty(commentCode) || commentCode.equals("0")) {
			this.commentCode = null;
		} else {
			this.commentCode = commentCode;
		}
	}

	/**
	 * @return the fromDate
	 * @author taitm
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 * @author taitm
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 * @author taitm
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 * @author taitm
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the processingStatus
	 * @author taitm
	 */
	public String getProcessingStatus() {
		return processingStatus;
	}

	/**
	 * @param processingStatus
	 *            the processingStatus to set
	 * @author taitm
	 */
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
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
	 * @return the motive
	 * @author taitm
	 */
	public String getMotive() {
		return motive;
	}

	/**
	 * @param motive
	 *            the motive to set
	 * @author taitm
	 */
	public void setMotive(String motive) {
		this.motive = motive;
	}

	/**
	 * @return the emailSubject
	 * @author taitm
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject
	 *            the emailSubject to set
	 * @author taitm
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	/**
	 * @return the fullName
	 * @author taitm
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 * @author taitm
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the customerId
	 * @author taitm
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 * @author taitm
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
