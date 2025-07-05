package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.ContactEmail;
import vn.com.unit.cms.admin.all.enumdef.ContactEmailStatusEnum;

public class ContactEmailDto {
	private Long id;
	private String fullName;
	private String email;
	private String emailSubject;
	private String emailContent;
	private Integer motive;
	private String motiveName;
	private String statusName;
	private String statusTitle;
	private String processedUser;
	private Date createDate;
	private String createDateString;
	private String processingStatus;
	private List<ContactEmailUpdateItemDto> updateHistory;
	private String comment;
	private String commentCode;
	private Date updateDate;
	private int stt;
	private String service;
	private String serviceName;
	private String url;
	private Long customerId;
	private String customerName;
	private Date fromDate;
	private Date toDate;

	public ContactEmailDto() {

	}

	public ContactEmailDto(ContactEmail entity) {
		this.id = entity.getId();
		this.fullName = entity.getFullName();
		this.email = entity.getEmail();
		this.emailSubject = entity.getEmailSubject();
		this.emailContent = entity.getEmailContent();
		this.motive = entity.getMotive();
		this.setProcessingStatus(entity.getProcessingStatus());
		this.setProcessedUser(entity.getProcessedUser());
		this.createDate = entity.getCreateDate();
		this.comment = entity.getComment();
		this.commentCode = entity.getCommentCode();
		this.updateDate = entity.getUpdateDate();
		this.service = entity.getService();
		this.customerId = entity.getCustomerId();
	}

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

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
		for (ContactEmailStatusEnum en : ContactEmailStatusEnum.class.getEnumConstants()) {
			if (en.getStatusName().equals(this.processingStatus)) {
				this.statusName = en.getStatusAlias();
				break;
			}
		}
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public Integer getMotive() {
		return motive;
	}

	public void setMotive(Integer motive) {
		this.motive = motive;
	}

	public String getStatusTitle() {
		return statusTitle;
	}

	public void setStatusTitle(String statusTitle) {
		this.statusTitle = statusTitle;
	}

	public List<ContactEmailUpdateItemDto> getUpdateHistory() {
		return updateHistory;
	}

	public void setUpdateHistory(List<ContactEmailUpdateItemDto> updateHistory) {
		this.updateHistory = updateHistory;
	}

	public String getProcessedUser() {
		return processedUser;
	}

	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
	}

	/**
	 * Get comment
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set comment
	 * 
	 * @param comment
	 *            type String
	 * @return
	 * @author TranLTH
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Get updateDate
	 * 
	 * @return Date
	 * @author TranLTH
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Set updateDate
	 * 
	 * @param updateDate
	 *            type Date
	 * @return
	 * @author TranLTH
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Get stt
	 * 
	 * @return int
	 * @author TranLTH
	 */
	public int getStt() {
		return stt;
	}

	/**
	 * Set stt
	 * 
	 * @param stt
	 *            type int
	 * @return
	 * @author TranLTH
	 */
	public void setStt(int stt) {
		this.stt = stt;
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

	/**
	 * @return the customerName
	 * @author taitm
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 * @author taitm
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	 * @return the motiveName
	 * @author taitm
	 */
	public String getMotiveName() {
		return motiveName;
	}

	/**
	 * @param motiveName
	 *            the motiveName to set
	 * @author taitm
	 */
	public void setMotiveName(String motiveName) {
		this.motiveName = motiveName;
	}

	/**
	 * @return the serviceName
	 * @author taitm
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 * @author taitm
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the createDateString
	 * @author taitm
	 */
	public String getCreateDateString() {
		return createDateString;
	}

	/**
	 * @param createDateString
	 *            the createDateString to set
	 * @author taitm
	 */
	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

}