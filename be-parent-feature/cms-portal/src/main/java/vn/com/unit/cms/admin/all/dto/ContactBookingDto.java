package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.ContactBooking;
import vn.com.unit.cms.admin.all.enumdef.ContactBookingStatusEnum;

public class ContactBookingDto {
	private Long id;
	private String fullName;
	private String email;
	private String phoneNumber;
	private String idNumber;
	private Date timeBooking;
	private Date dateBooking;
	private String bookingSubject;
	private String placeBooking;
	private Long placeBookingBranchId;
	private String bookingContent;
	private String comment;
	private String commentCode;
	private String processingStatus;
	private String processedUser;
	private String statusName;
	private String statusTitle;
	private Date createDate;
	private List<ContactBookingUpdateItemDto> updateHistory;
	private int stt;
	private Date updateDate;
	private String dateBookingExport;
	private String timeBookingExport;
	private String createDateExport;
	
	private String url;
	
	public ContactBookingDto(){
		
	}
	
	public ContactBookingDto(ContactBooking entity) {
		this.id = entity.getId();
		this.fullName = entity.getFullName();
		this.email = entity.getEmail();
		this.phoneNumber = entity.getPhoneNumber();
		this.idNumber = entity.getIdNumber();
		this.timeBooking = entity.getTimeBooking();
		this.dateBooking = entity.getDateBooking();
		this.bookingSubject = entity.getBookingSubject();
		this.placeBooking = entity.getPlaceBooking();
		this.placeBookingBranchId = entity.getPlaceBookingBranchId();
		this.bookingContent = entity.getBookingContent();
		this.processedUser = entity.getProcessedUser();
		this.comment = entity.getCommentName();
		this.setProcessingStatus(entity.getProcessingStatus());
		this.createDate = entity.getCreateDate();
		this.updateDate = entity.getUpdateDate();
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
		for(ContactBookingStatusEnum en : ContactBookingStatusEnum.class.getEnumConstants()){
			if(en.getStatusName().equals(this.processingStatus)){
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

	public Long getPlaceBookingBranchId() {
		return placeBookingBranchId;
	}

	public void setPlaceBookingBranchId(Long placeBookingBranchId) {
		this.placeBookingBranchId = placeBookingBranchId;
	}

	public String getStatusTitle() {
		return statusTitle;
	}

	public void setStatusTitle(String strStatusName) {
		this.statusTitle = strStatusName;
	}

	public String getProcessedUser() {
		return processedUser;
	}

	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
	}

	public List<ContactBookingUpdateItemDto> getUpdateHistory() {
		return updateHistory;
	}

	public void setUpdateHistory(List<ContactBookingUpdateItemDto> updateHistory) {
		this.updateHistory = updateHistory;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

    /**
     * Get stt
     * @return int
     * @author TranLTH
     */
    public int getStt() {
        return stt;
    }

    /**
     * Set stt
     * @param   stt
     *          type int
     * @return
     * @author  TranLTH
     */
    public void setStt(int stt) {
        this.stt = stt;
    }

    /**
     * Get updateDate
     * @return Date
     * @author TranLTH
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Set updateDate
     * @param   updateDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getCommentCode() {
		return commentCode;
	}

	public void setCommentCode(String commentCode) {
		this.commentCode = commentCode;
	}

	public Date getTimeBooking() {
		return timeBooking;
	}

	public void setTimeBooking(Date timeBooking) {
		this.timeBooking = timeBooking;
	}

	public String getDateBookingExport() {
		return dateBookingExport;
	}

	public void setDateBookingExport(String dateBookingExport) {
		this.dateBookingExport = dateBookingExport;
	}

	public String getTimeBookingExport() {
		return timeBookingExport;
	}

	public void setTimeBookingExport(String timeBookingExport) {
		this.timeBookingExport = timeBookingExport;
	}

	public String getCreateDateExport() {
		return createDateExport;
	}

	public void setCreateDateExport(String createDateExport) {
		this.createDateExport = createDateExport;
	}
}