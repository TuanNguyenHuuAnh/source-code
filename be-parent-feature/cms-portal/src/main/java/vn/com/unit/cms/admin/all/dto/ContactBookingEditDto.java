package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ContactBookingEditDto {
	private Long id;
	private String content;
	private String comment;
	private String commentCode;
	private String phoneNumber;
	private String email;
	private Date fromDate;
	private Date toDate;
	private String processingStatus;
	
	public ContactBookingEditDto(){
		
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
		if(StringUtils.isEmpty(commentCode) || commentCode.equals("0")){
			this.commentCode = null;
		}else{
			this.commentCode = commentCode;
		}
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
}
