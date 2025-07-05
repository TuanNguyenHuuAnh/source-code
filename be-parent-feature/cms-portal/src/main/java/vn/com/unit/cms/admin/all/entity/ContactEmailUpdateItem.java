package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_contact_email_update_item")
public class ContactEmailUpdateItem extends AbstractTracking{
	
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTACT_EMAIL_UPDATE_ITEM")
	private Long id;
	@Column(name = "m_contact_email_id")
	private Long emailId;
	@Column(name = "comment_name")
	private String comment;
	@Column(name = "comment_code")
	private String commentCode;
	@Column(name = "processing_status")
	private String status;
	@Column(name = "processed_user")
	private String processedUser;
	
	public Long getId() {
		return id;
	}
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
	}
	public String getProcessedUser() {
		return processedUser;
	}
	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
	}
	public String getCommentCode() {
		return commentCode;
	}
	public void setCommentCode(String commentCode) {
		this.commentCode = commentCode;
	}
}
