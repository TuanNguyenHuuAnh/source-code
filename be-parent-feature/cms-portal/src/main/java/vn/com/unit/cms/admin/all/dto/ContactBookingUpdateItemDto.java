package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import vn.com.unit.cms.admin.all.entity.ContactBookingUpdateItem;
import vn.com.unit.cms.admin.all.enumdef.ContactBookingStatusEnum;

public class ContactBookingUpdateItemDto {
	
	private int id;
	private Long bookingId;
	private String commentName;
	private String commentCode;
	private String status;
	private String statusTitle;
	private String processedUser;
	private String fullNameProcessedUser;
	private Date createDate;
	
	public ContactBookingUpdateItem createEntity(){
		ContactBookingUpdateItem entity = new ContactBookingUpdateItem();
		entity.setId(this.id);
		entity.setBookingId(this.bookingId);
		entity.setCommentName(this.commentName);
		entity.setCommentCode(this.commentCode);
		entity.setStatus(this.status);
		entity.setProcessedUser(this.processedUser);
		return entity;
	}
	
	public ContactBookingUpdateItemDto(){
		
	}
	
	public ContactBookingUpdateItemDto(ContactBookingUpdateItem entity){
		this.id = entity.getId();
		this.bookingId = entity.getBookingId();
		this.commentName = entity.getCommentName();
		this.commentCode = entity.getCommentCode();
		this.status = entity.getStatus();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;

		for(ContactBookingStatusEnum en : ContactBookingStatusEnum.class.getEnumConstants()){
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

	public String getCommentName() {
		return commentName;
	}

	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}
	
}
