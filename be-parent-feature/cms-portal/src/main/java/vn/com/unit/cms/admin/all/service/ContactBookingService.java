package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.cms.admin.all.dto.ContactBookingDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingEditDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingSearchDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingUpdateItemDto;
import vn.com.unit.cms.admin.all.dto.ContactCommentDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.common.dto.SearchKeyDto;

public interface ContactBookingService {

	/**
	 * getBookingList
	 * @return
	 */
	List<ContactBookingDto> getBookingList();

	/**
	 * getBookingDetail
	 * @param bookingId
	 * @return
	 */
	ContactBookingDto getBookingDetail(Long bookingId, Locale locale);

	/**
	 * genSearchKeyList
	 * @param locale
	 * @return
	 */
	List<SearchKeyDto> genSearchKeyList(Locale locale);

	/**
	 * getBookingList
	 * @param searchDto
	 * @param page
	 * @return
	 */
	PageWrapper<ContactBookingDto> getBookingList(ContactBookingSearchDto searchDto, int page);
	
	/**
	 * processTransferBooking
	 * @param editModel
	 * @param bookingId
	 * @param locale
	 * @return
	 */
	ContactBookingDto processTransferBooking(ContactBookingEditDto editModel, Long bookingId, Locale locale);

	/**
	 * deleteBooking
	 * @param bookingId
	 * @return
	 */
	boolean deleteBooking(Long bookingId);

	/**
	 * getList
	 * @return
	 */
	List<ContactBookingDto> getList();

	/**
	 * getFullBookingList
	 * @param searchDto
	 * @return
	 */
	List<ContactBookingDto> getFullBookingList(ContactBookingSearchDto searchDto);

	/**
	 * getUpdateHistory
	 * @param bookingId
	 * @return
	 */
	List<ContactBookingUpdateItemDto> getUpdateHistory(Long bookingId);

	/**
	 * processRejectBooking
	 * @param bookingEditModel
	 * @param bookingId
	 * @param locale
	 * @return
	 */
	ContactBookingDto processRejectBooking(ContactBookingEditDto bookingEditModel, Long bookingId, Locale locale);

	/**
	 * processDoneBooking
	 * @param bookingId
	 * @param locale
	 * @return
	 */
	ContactBookingDto processDoneBooking(ContactBookingEditDto bookingEditModel, Long bookingId, Locale locale);

	/**
	 * getCommentOptions
	 * @return
	 */
	List<ContactCommentDto> getCommentOptions();
}
