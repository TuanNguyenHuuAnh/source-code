package vn.com.unit.cms.admin.all.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.ContactBookingDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingEditDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingSearchDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingUpdateItemDto;
import vn.com.unit.cms.admin.all.dto.ContactCommentDto;
import vn.com.unit.cms.admin.all.entity.ContactBooking;
import vn.com.unit.cms.admin.all.entity.ContactBookingUpdateItem;
import vn.com.unit.cms.admin.all.enumdef.ContactBookingCommentEnum;
import vn.com.unit.cms.admin.all.enumdef.ContactBookingSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.ContactBookingStatusEnum;
import vn.com.unit.cms.admin.all.jcanary.entity.Branch;
import vn.com.unit.cms.admin.all.jcanary.repository.BranchRepository;
import vn.com.unit.cms.admin.all.repository.ContactBookingRepository;
import vn.com.unit.cms.admin.all.repository.ContactBookingUpdateItemRepository;
import vn.com.unit.cms.admin.all.service.ContactBookingService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.dto.EmailDto;
import vn.com.unit.ep2p.utils.SearchUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ContactBookingServiceImpl implements ContactBookingService{

	@Autowired
	ContactBookingRepository contactBookingRepository;
	
	@Autowired
	BranchRepository branchRepository;
	
	@Autowired
	MessageSource msg;
	
	@Autowired
	SystemConfig systemConfig;
	
//	@Autowired
//	EmailService emailService;
	
	@Autowired
	ContactBookingUpdateItemRepository contactBookingUpdateItemRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ContactBookingServiceImpl.class);
	
	@Override
	public List<ContactBookingDto> getBookingList() {
		List<ContactBooking> entityList = contactBookingRepository.findAllBooking();
		List<ContactBookingDto> bookingList = new ArrayList<ContactBookingDto>();
		for(ContactBooking entity : entityList){
			ContactBookingDto contactBookingDto = new ContactBookingDto(entity);
			bookingList.add(contactBookingDto);
		}
		return bookingList;
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public PageWrapper<ContactBookingDto> getBookingList(ContactBookingSearchDto searchModel, int page){

        int sizeOfPage = searchModel.getPageSize() != null ? searchModel.getPageSize()
                : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

        PageWrapper<ContactBookingDto> pageWrapper = new PageWrapper<ContactBookingDto>(page,
                sizeOfPage);

        if (null == searchModel)
        	searchModel = new ContactBookingSearchDto();

        ContactBookingSearchDto searchCondition = this.genSearchCondition(searchModel);
        if(null != searchCondition.getToDate()){
        	Date toDate = searchCondition.getToDate();
        	toDate.setHours(23);
        	toDate.setMinutes(59);
        	toDate.setSeconds(59);
        	searchCondition.setToDate(toDate);
        }

        int count = contactBookingRepository.countBySearchCondition(searchCondition);
        List<ContactBookingDto> result = new ArrayList<ContactBookingDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            List<ContactBooking> entityList = contactBookingRepository.findAllBookingByCondition(offsetSQL, sizeOfPage, searchCondition);
            for(ContactBooking entity : entityList){
            	ContactBookingDto contactBookingDto = new ContactBookingDto(entity);
            	result.add(contactBookingDto);
            }
        }

        pageWrapper.setDataAndCount(result, count);

        return pageWrapper;
	}

	@Override
	public List<ContactBookingDto> getFullBookingList(ContactBookingSearchDto searchModel) {
        if (null == searchModel)
        	searchModel = new ContactBookingSearchDto();
        ContactBookingSearchDto searchCondition = this.genSearchCondition(searchModel);
        List<ContactBookingDto> result = new ArrayList<ContactBookingDto>();
        int i = 1;
        List<ContactBooking> entityList = contactBookingRepository.findAllBookingByConditionNoPaging(searchCondition);
        for(ContactBooking entity : entityList){
        	ContactBookingDto contactBookingDto = new ContactBookingDto(entity);
        	contactBookingDto.setStt(i++);        	
        	result.add(contactBookingDto);
        }
        return result;
	}

	@Override
	public List<ContactBookingDto> getList() {
		return null;
	}

	@Override
	public ContactBookingDto getBookingDetail(Long bookingId, Locale locale) {
		ContactBooking entity = contactBookingRepository.findOne(bookingId);
		
		if (entity == null){
			if (null == entity) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale) + bookingId);
			}
		}
		
		ContactBookingDto bookingModel = new ContactBookingDto(entity);
		List<ContactBookingUpdateItemDto> updateHistory = contactBookingUpdateItemRepository.findByBookingId(bookingId);
		bookingModel.setUpdateHistory(updateHistory);
		return bookingModel;
	}

	@Override
	public List<SearchKeyDto> genSearchKeyList(Locale locale) {
		return SearchUtil.genSearchKeyList(ContactBookingSearchEnum.class, msg, locale);
	}
	
	private ContactBookingSearchDto genSearchCondition(ContactBookingSearchDto searchModel){
        String searchValue = searchModel.getSearchValue() == null ? null : searchModel.getSearchValue().replace(" ", "");
        ContactBookingSearchDto searchCondition = new ContactBookingSearchDto();
        
        if (searchModel.getSearchKeyIds() == null || searchModel.getSearchKeyIds().isEmpty()) {
            searchCondition.setFullName(searchValue);
            searchCondition.setEmail(searchValue);
            searchCondition.setIdNumber(searchValue);
            searchCondition.setBookingSubject(searchValue);
            searchCondition.setPlaceBooking(searchValue);
            searchCondition.setBookingContent(searchValue);
            
        } else {
            List<String> searchKeyIds = searchModel.getSearchKeyIds();
            for (String searchKeyId : searchKeyIds) {
                if (searchKeyId.equals(ContactBookingSearchEnum.FULL_NAME.name())) {
                    searchCondition.setFullName(searchValue);
                    break;
                }
                if (searchKeyId.equals(ContactBookingSearchEnum.PHONE_NUMBER.name())) {
                    searchCondition.setPhoneNumber(searchValue);
                    break;
                }
                if (searchKeyId.equals(ContactBookingSearchEnum.BOOKING_SUBJECT.name())) {
                    searchCondition.setBookingSubject(searchValue);
                    break;
                }
                if (searchKeyId.equals(ContactBookingSearchEnum.BOOKING_CONTENT.name())) {
                    searchCondition.setBookingContent(searchValue);
                    break;
                }
                if(searchKeyId.equals(ContactBookingSearchEnum.EMAIL.name())){
                	searchCondition.setEmail(searchValue);
                	break;
                }
                if(searchKeyId.equals(ContactBookingSearchEnum.PLACE_BOOKING.name())){
                	searchCondition.setPlaceBooking(searchValue);
                	break;
                }
                if(searchKeyId.equals(ContactBookingSearchEnum.ID_NUMBER.name())){
                	searchCondition.setIdNumber(searchValue);
                	break;
                }
            }
        }
        searchCondition.setEmail(searchModel.getEmail());
        searchCondition.setPhoneNumber(searchModel.getPhoneNumber());
        searchCondition.setFromDate(searchModel.getFromDate());
        searchCondition.setToDate(searchModel.getToDate());
        searchCondition.setProcessingStatus(searchModel.getProcessingStatus());
        return searchCondition;
	}
	
	@Override
	@Transactional
	public ContactBookingDto processRejectBooking(ContactBookingEditDto editModel, Long bookingId,
			Locale locale) {
		ContactBooking bookingEntity = contactBookingRepository.findOne(bookingId);
		ContactBookingStatusEnum processingStatus = ContactBookingStatusEnum.BOOKING_STATUS_REJECTED;
		ContactBookingDto bookingDto = processUpdateBooking(bookingEntity, editModel, bookingId, locale, processingStatus);
		return bookingDto;
	}

	@Override
	@Transactional
	public ContactBookingDto processTransferBooking(ContactBookingEditDto editModel, Long bookingId, Locale locale) {
		
		ContactBooking bookingEntity = contactBookingRepository.findOne(bookingId);
		ContactBookingDto bookingDto = new ContactBookingDto();
		
		if (bookingEntity == null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}
		try {
			//sendBookRequestMailToBranch(bookingEntity, locale);
			sendBookingFeedbackMailToCustomer(bookingEntity, locale);
			ContactBookingStatusEnum processingStatus = ContactBookingStatusEnum.BOOKING_STATUS_PROCESSED;
			bookingDto = processUpdateBooking(bookingEntity, editModel, bookingId, locale, processingStatus);
		} catch (Exception e) {
			logger.error("processTransferBooking: " + e.getMessage());
			throw new BusinessException("");
		}
		return bookingDto;
	}
	
	@Override
	@Transactional
	public ContactBookingDto processDoneBooking(ContactBookingEditDto bookingEditModel, Long bookingId, Locale locale) {
		
		ContactBooking bookingEntity = contactBookingRepository.findOne(bookingId);
		ContactBookingStatusEnum processingStatus = ContactBookingStatusEnum.BOOKING_STATUS_DONE;
		ContactBookingDto bookingDto = processUpdateBooking(bookingEntity, bookingEditModel, bookingId, locale, processingStatus);
		return bookingDto;
	}

	private ContactBookingDto processUpdateBooking(ContactBooking bookingEntity, ContactBookingEditDto editModel, Long bookingId, Locale locale,
			ContactBookingStatusEnum processingStatus) {
		bookingEntity.setProcessingStatus(processingStatus.getStatusName());
		bookingEntity.setProcessedUser(UserProfileUtils.getFullName());
		bookingEntity.setBookingContent(editModel.getContent());
		bookingEntity.setCommentName(editModel.getComment());
		bookingEntity.setCommentCode(editModel.getCommentCode());
		bookingEntity.setUpdateDate(new Date());
		bookingEntity.setUpdateBy(UserProfileUtils.getUserNameLogin());
		ContactBookingUpdateItem updateHistoryItem = new ContactBookingUpdateItem();
		updateHistoryItem.setBookingId(bookingId);
		updateHistoryItem.setCommentName(editModel.getComment());
		updateHistoryItem.setCommentCode(editModel.getCommentCode());
		updateHistoryItem.setProcessedUser(UserProfileUtils.getFullName());
		updateHistoryItem.setStatus(processingStatus.getStatusName());
		updateHistoryItem.setCreateDate(new Date());
		updateHistoryItem.setCreateBy(UserProfileUtils.getUserNameLogin());
		contactBookingUpdateItemRepository.save(updateHistoryItem);
		bookingEntity = contactBookingRepository.save(bookingEntity);
		ContactBookingDto bookingDto = new ContactBookingDto(bookingEntity);
		List<ContactBookingUpdateItemDto> updateHistory = contactBookingUpdateItemRepository.findByBookingId(bookingId);
		bookingDto.setUpdateHistory(updateHistory);
		return bookingDto;
	}
	
	@SuppressWarnings("unused")
	private void sendBookRequestMailToBranch(ContactBooking bookingEntity, Locale locale){
		Branch branchEntity = branchRepository.findOne(bookingEntity.getPlaceBookingBranchId());
		EmailDto emaildto = new EmailDto();
		emaildto.setSubject(msg.getMessage("contact.booking.transfer-mail.title", null, locale));
		if(null != branchEntity){
			emaildto.setReceiveAddress(branchEntity.getEmail());
		}
		emaildto.setTemplateFile("contact-booking-template-branch_" + locale.toString() + ".ftl");
		Map<String, Object> dataFillContentMail = new HashMap<String, Object>();
		dataFillContentMail.put("contentMail", bookingEntity.getBookingContent());
		dataFillContentMail.put("fullName", bookingEntity.getFullName());
		dataFillContentMail.put("email", bookingEntity.getEmail());
		dataFillContentMail.put("phoneNumber", bookingEntity.getPhoneNumber());
		dataFillContentMail.put("personalId", bookingEntity.getIdNumber());
		dataFillContentMail.put("timeBooking", bookingEntity.getTimeBooking());
		dataFillContentMail.put("bookingSubject", bookingEntity.getBookingSubject());
		String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
		if (bookingEntity.getDateBooking() != null) {
			DateFormat df = new SimpleDateFormat(patternDate);
			String strDateBooking = df.format(bookingEntity.getDateBooking());
			dataFillContentMail.put("dateBooking", strDateBooking);
		} else {
			dataFillContentMail.put("dateBooking", "");
		}
		emaildto.setData(dataFillContentMail);
//		emailService.sendEmail(emaildto);
	}
	
	private void sendBookingFeedbackMailToCustomer(ContactBooking bookingEntity, Locale locale){
		EmailDto emaildto = new EmailDto();
		emaildto.setSubject(msg.getMessage("contact.booking.feedback-mail.title", null, locale));
		emaildto.setReceiveAddress(bookingEntity.getEmail());
		emaildto.setTemplateFile("contact-booking-template-customer_" + locale.toString() + ".ftl");
		Map<String, Object> dataFillContentMail = new HashMap<String, Object>();
		dataFillContentMail.put("bookingContent", bookingEntity.getBookingContent());
		dataFillContentMail.put("fullName", bookingEntity.getFullName());
		dataFillContentMail.put("bookingSubject", bookingEntity.getBookingSubject());
		dataFillContentMail.put("placeBooking", bookingEntity.getPlaceBooking());
		dataFillContentMail.put("timeBooking", bookingEntity.getTimeBooking());
		String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
		if (bookingEntity.getDateBooking() != null) {
			DateFormat df = new SimpleDateFormat(patternDate);
			String strDateBooking = df.format(bookingEntity.getDateBooking());
			dataFillContentMail.put("dateBooking", strDateBooking);
		} else {
			dataFillContentMail.put("dateBooking", "");
		}
		emaildto.setData(dataFillContentMail);
//		emailService.sendEmail(emaildto);
	}

	@Override
	@Transactional
	public boolean deleteBooking(Long bookingId) {
		ContactBooking entity = contactBookingRepository.findOne(bookingId);
		if(ContactBookingStatusEnum.BOOKING_STATUS_PROCESSED.getStatusName().equals(entity.getProcessingStatus())){
			entity.setDeleteDate(new Date());
			String userName = UserProfileUtils.getUserNameLogin();
			entity.setDeleteBy(userName);
			entity = contactBookingRepository.save(entity);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<ContactBookingUpdateItemDto> getUpdateHistory(Long bookingId) {
		List<ContactBookingUpdateItemDto> updateHistory = contactBookingUpdateItemRepository.findByBookingId(bookingId);
		return updateHistory;
	}

	@Override
	public List<ContactCommentDto> getCommentOptions() {
		List<ContactCommentDto> commentOptions = new ArrayList<ContactCommentDto>();
		for(ContactBookingCommentEnum commentEnum : ContactBookingCommentEnum.class.getEnumConstants()){
			ContactCommentDto commentOption = new ContactCommentDto();
			commentOption.setCommentTitle(commentEnum.getCommentTitle());
			commentOption.setCommentValue(commentEnum.getCommentValue());
			commentOptions.add(commentOption);
		}
		return commentOptions;
	}

}
