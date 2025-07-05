package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_contact_booking_update_item")
public class ContactBookingUpdateItem extends AbstractTracking{
	
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTACT_BOOKING_UPDATE_ITEM")
	private int id;
	@Column(name = "m_contact_booking_id")
	private Long bookingId;
	@Column(name = "comment_name")
	private String commentName;
	@Column(name = "comment_code")
	private String commentCode;
	@Column(name = "processing_status")
	private String status;
	@Column(name = "processed_user")
	private String processedUser;
	
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
	public String getCommentName() {
		return commentName;
	}
	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}
}
