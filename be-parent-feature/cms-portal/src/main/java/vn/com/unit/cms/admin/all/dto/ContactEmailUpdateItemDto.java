package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import vn.com.unit.cms.admin.all.entity.ContactEmailUpdateItem;
import vn.com.unit.cms.admin.all.enumdef.ContactEmailStatusEnum;

public class ContactEmailUpdateItemDto {
	
	private Long id;
	private Long emailId;
	private String comment;
	private String commentCode;
	private String status;
	private String statusTitle;
	private String processedUser;
	private String fullNameProcessedUser;
	private Date createDate;
	
	public ContactEmailUpdateItem createEntity(){
		ContactEmailUpdateItem entity = new ContactEmailUpdateItem();
		entity.setId(this.id);
		entity.setEmailId(this.emailId);
		entity.setComment(this.comment);
		entity.setCommentCode(this.commentCode);
		entity.setStatus(this.status);
		entity.setProcessedUser(this.processedUser);
		return entity;
	}
	
	public ContactEmailUpdateItemDto(){
		
	}
	
	public ContactEmailUpdateItemDto(ContactEmailUpdateItem entity){
		this.id = entity.getId();
		this.emailId = entity.getEmailId();
		this.comment = entity.getComment();
		this.commentCode = entity.getCommentCode();
		this.status = entity.getStatus();
	}
	
	/**
	 * @return the id
	 * @author taitm
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @author taitm
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long bookingId) {
		this.emailId = bookingId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;

		for(ContactEmailStatusEnum en : ContactEmailStatusEnum.class.getEnumConstants()){
			if(en.getStatusName().equals(this.status)){
				this.statusTitle = en.getStatusAlias();
				break;
			}
		}
	
	}
	
	public String getProcessedUser() {
		return processedUser;
	}
	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
	}
	public String getFullNameProcessedUser() {
		return fullNameProcessedUser;
	}
	public void setFullNameProcessedUser(String fullNameProcessedUser) {
		this.fullNameProcessedUser = fullNameProcessedUser;
	}

	public String getStatusTitle() {
		return statusTitle;
	}

	public void setStatusTitle(String statusName) {
		this.statusTitle = statusName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCommentCode() {
		return commentCode;
	}

	public void setCommentCode(String commentCode) {
		this.commentCode = commentCode;
	}
	
}
