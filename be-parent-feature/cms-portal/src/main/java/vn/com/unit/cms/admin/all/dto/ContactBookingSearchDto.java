package vn.com.unit.cms.admin.all.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactBookingSearchDto {
	private List<String> searchKeyIds;
	private String searchValue;
	private String fullName;
	private String email;
	private String phoneNumber;
	private String idNumber;
	private String bookingSubject;
	private String placeBooking;
	private String bookingContent;
	private Integer pageSize;
	private Date fromDate;
	private Date toDate;
	private String processingStatus;
	
	public ContactBookingSearchDto(){
		searchKeyIds = new ArrayList<String>();
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
	public List<String> getSearchKeyIds() {
		return searchKeyIds;
	}
	public void setSearchKeyIds(List<String> searchKeyIds) {
		this.searchKeyIds = searchKeyIds;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDateSearch) {
		this.fromDate = fromDateSearch;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDateSearch) {
		this.toDate = toDateSearch;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
