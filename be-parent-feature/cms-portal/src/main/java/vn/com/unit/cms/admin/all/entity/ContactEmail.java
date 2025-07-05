package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_contact_mail")
public class ContactEmail extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTACT_MAIL")
	private Long id;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "subject")
	private String emailSubject;
	
	@Column(name = "content")
	private String emailContent;
	
	@Column(name = "motive")
	private Integer motive;
	
	@Column(name = "processing_status")
	private String processingStatus;
	
	@Column(name = "comment_name")
	private String comment;
	
	@Column(name = "comment_code")
	private String commentCode;
	
	@Column(name = "processed_user")
	private String processedUser;

	@Column(name = "service")
	private String service;

	@Column(name = "customer_type_id")
	private Long customerId;
	
	@Column(name = "PHONE")
	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public Integer getMotive() {
		return motive;
	}

	public void setMotive(Integer motive) {
		this.motive = motive;
	}

	public String getProcessedUser() {
		return processedUser;
	}

	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
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
		this.commentCode = commentCode;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
