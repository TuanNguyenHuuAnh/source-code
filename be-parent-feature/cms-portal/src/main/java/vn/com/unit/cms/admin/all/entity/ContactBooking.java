package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_contact_booking")
public class ContactBooking extends AbstractTracking{
	
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTACT_BOOKING")
	private Long id;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "email")
	private String email;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "id_number")
	private String idNumber;
	@Column(name = "time_booking")
	private Date timeBooking;
	@Column(name = "date_booking")
	private Date dateBooking;
	@Column(name = "booking_subject")
	private String bookingSubject;
	@Column(name = "place_booking_branch_id")
	private Long placeBookingBranchId;
	@Column(name = "place_booking")
	private String placeBooking;
	@Column(name = "booking_content")
	private String bookingContent;
	@Column(name = "comment_name")
	private String commentName;
	@Column(name = "comment_code")
	private String commentCode;
	@Column(name = "processing_status")
	private String processingStatus;
	@Column(name = "processed_user")
	private String processedUser;
	
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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getIdNumber() {
		return idNumber;
	}
	
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public Date getDateBooking() {
		return dateBooking;
	}
	
	public void setDateBooking(Date dateBooking) {
		this.dateBooking = dateBooking;
	}
	
	public String getBookingSubject() {
		return bookingSubject;
	}
	
	public void setBookingSubject(String bookingSubject) {
		this.bookingSubject = bookingSubject;
	}
	
	public String getPlaceBooking() {
		return placeBooking;
	}
	
	public void setPlaceBooking(String placeBooking) {
		this.placeBooking = placeBooking;
	}
	
	public String getBookingContent() {
		return bookingContent;
	}
	
	public void setBookingContent(String bookingContent) {
		this.bookingContent = bookingContent;
	}
	
	public String getProcessingStatus() {
		return processingStatus;
	}
	
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public Long getPlaceBookingBranchId() {
		return placeBookingBranchId;
	}

	public void setPlaceBookingBranchId(Long placeBookingBranchId) {
		this.placeBookingBranchId = placeBookingBranchId;
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

	public Date getTimeBooking() {
		return timeBooking;
	}

	public void setTimeBooking(Date timeBooking) {
		this.timeBooking = timeBooking;
	}
}
