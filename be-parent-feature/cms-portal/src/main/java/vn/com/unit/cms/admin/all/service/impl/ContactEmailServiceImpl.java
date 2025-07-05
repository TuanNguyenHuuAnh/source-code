package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.dto.ContactCommentDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailEditDto;
//import vn.com.unit.cms.admin.all.dto.ContactEmailSearchDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailUpdateItemDto;
import vn.com.unit.cms.admin.all.entity.ContactEmail;
import vn.com.unit.cms.admin.all.entity.ContactEmailUpdateItem;
import vn.com.unit.cms.admin.all.entity.ProductCategoryLanguage;
import vn.com.unit.cms.admin.all.enumdef.ContactEmailCommentEnum;
import vn.com.unit.cms.admin.all.enumdef.ContactEmailSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.ContactEmailStatusEnum;
import vn.com.unit.cms.admin.all.jcanary.repository.BranchRepository;
import vn.com.unit.cms.admin.all.repository.ContactEmailRepository;
import vn.com.unit.cms.admin.all.repository.ContactEmailUpdateItemRepository;
import vn.com.unit.cms.admin.all.service.ContactEmailService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.cms.core.module.contact.dto.ContactEmailSearchDto;
//import vn.com.unit.jcanary.repository.BranchRepository;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
//import vn.com.unit.jcanary.service.EmailService;
import vn.com.unit.ep2p.utils.SearchUtil;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ContactEmailServiceImpl implements ContactEmailService{

	@Autowired
	ContactEmailRepository contactEmailRepository;
	
//	@Autowired
//	ConstantDisplayService constantDisplayService;
	
	@Autowired
	ContactEmailUpdateItemRepository contactEmailUpdateItemRepository;
	
	@Autowired
	BranchRepository branchRepository;
	
	@Autowired
	MessageSource msg;
	
	@Autowired
	SystemConfig systemConfig;
	
//	@Autowired
//	EmailService emailService;
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@Autowired
	private JcaConstantService jcaConstantService;
	
	private static final Logger logger = LoggerFactory.getLogger(ContactEmailServiceImpl.class);
	
	@Override
	public List<ContactEmailDto> getEmailList() {
		List<ContactEmail> entityList = contactEmailRepository.findAllEmailRegistration();
		List<ContactEmailDto> emailList = new ArrayList<ContactEmailDto>();
		for(ContactEmail entity : entityList){
			ContactEmailDto contactEmailDto = new ContactEmailDto(entity);
			emailList.add(contactEmailDto);
		}
		return emailList;
	}
	
	@Override
	public PageWrapper<ContactEmailDto> getEmailList(ContactEmailSearchDto searchModel, int page){

		int sizeOfPage = searchModel.getPageSize() != null ? searchModel.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

        PageWrapper<ContactEmailDto> pageWrapper = new PageWrapper<ContactEmailDto>(page,
                sizeOfPage);

        int count = contactEmailRepository.countBySearchCondition(searchModel);
        List<ContactEmailDto> result = new ArrayList<ContactEmailDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            result = contactEmailRepository.findAllEmailByCondition(offsetSQL, sizeOfPage, searchModel);
        }
        
        pageWrapper.setDataAndCount(result, count);

        return pageWrapper;
	}

	@Override
	public ContactEmailDto getEmailDetail(Long emailId) {
		ContactEmail entity = contactEmailRepository.findOne(emailId);
		
		if (entity == null){
			throw new BusinessException("Thông tin không tồn tại");
		}
		
		ContactEmailDto emailModel = new ContactEmailDto(entity);
		
		ProductCategoryLanguage productCategory = productCategoryService.findByAliasAndCustomerId(emailModel.getService(), "VI", emailModel.getCustomerId());
		
		if (productCategory != null) {
			emailModel.setServiceName(productCategory.getTitle());
		}
		
		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
//		ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(),
//				emailModel.getMotive().toString());
		
		JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		emailModel.setMotiveName(motive.getName());
		
		List<ContactEmailUpdateItemDto> updateHistory = contactEmailUpdateItemRepository.findByEmailId(emailId);
		emailModel.setUpdateHistory(updateHistory);
		
		return emailModel;
	}

	@Override
	public List<SearchKeyDto> genSearchKeyList(Locale locale) {
		return SearchUtil.genSearchKeyList(ContactEmailSearchEnum.class, msg, locale);
	}
	
	@Override
	@Transactional
	public ContactEmailDto updateEmailToProcessing(ContactEmailEditDto editModel, Long emailId, Locale locale) {
		ContactEmail emailEntity = contactEmailRepository.findOne(emailId);
		ContactEmailDto contactEmailDto = processUpdateEmail(emailEntity, editModel, emailId, locale, ContactEmailStatusEnum.BOOKING_STATUS_PROCESSING);
		return contactEmailDto;
	}
	
	@Override
	@Transactional
	public ContactEmailDto updateEmailDone(ContactEmailEditDto editModel, Long emailId, Locale locale) {
		ContactEmail emailEntity = contactEmailRepository.findOne(emailId);
		ContactEmailDto contactEmailDto = processUpdateEmail(emailEntity, editModel, emailId, locale, ContactEmailStatusEnum.BOOKING_STATUS_DONE);
		return contactEmailDto;
	}
	
	
	@Override
	@Transactional
	public boolean deleteEmail(Long emailId) {
		ContactEmail entity = contactEmailRepository.findOne(emailId);
		if(ContactEmailStatusEnum.BOOKING_STATUS_REJECTED.getStatusName().equals(entity.getProcessingStatus())){
			entity.setDeleteDate(new Date());
			String userName = UserProfileUtils.getUserNameLogin();
			entity.setDeleteBy(userName);
			entity = contactEmailRepository.save(entity);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<ContactEmailDto> getFullEmailList(ContactEmailSearchDto searchModel) {

        List<ContactEmailDto> result = contactEmailRepository.findAllEmailByConditionNoPaging(searchModel);
        
        return result;
	}

	@Override
	public List<ContactCommentDto> getCommentOptions() {
		List<ContactCommentDto> commentOptions = new ArrayList<ContactCommentDto>();
		for(ContactEmailCommentEnum commentEnum : ContactEmailCommentEnum.class.getEnumConstants()){
			ContactCommentDto commentOption = new ContactCommentDto();
			commentOption.setCommentTitle(commentEnum.getCommentTitle());
			commentOption.setCommentValue(commentEnum.getCommentValue());
			commentOptions.add(commentOption);
		}
		return commentOptions;
	}

	@Override
	public List<ContactEmailUpdateItemDto> getUpdateHistory(Long emailId) {
		List<ContactEmailUpdateItemDto> updateHistory = contactEmailUpdateItemRepository.findByEmailId(emailId);
		return updateHistory;
	}

	@Override
	@Transactional
	public ContactEmailDto rejectEmail(ContactEmailEditDto emailEditModel, Long emailId, Locale locale) {
		ContactEmail emailEntity = contactEmailRepository.findOne(emailId);
		ContactEmailDto contactEmailDto = processUpdateEmail(emailEntity, emailEditModel, emailId, locale, ContactEmailStatusEnum.BOOKING_STATUS_REJECTED);
		return contactEmailDto;
	}
	
	private ContactEmailDto processUpdateEmail(ContactEmail emailEntity, ContactEmailEditDto editModel, Long emailId, Locale locale,
			ContactEmailStatusEnum processingStatus) {
		
		ContactEmailDto emailDto = new ContactEmailDto();
		
		try{
			emailEntity.setProcessingStatus(processingStatus.getStatusName());
			emailEntity.setProcessedUser(UserProfileUtils.getFullName());
			emailEntity.setComment(editModel.getComment());
			emailEntity.setCommentCode(editModel.getCommentCode());
			
			//tuanh setup nguoi duyet va ngay duyet
			emailEntity.setUpdateDate(new Date());
			emailEntity.setUpdateBy(UserProfileUtils.getUserNameLogin());
			ContactEmailUpdateItem updateHistoryItem = new ContactEmailUpdateItem();
			
			updateHistoryItem.setEmailId(emailId);
			updateHistoryItem.setComment(editModel.getComment());
			updateHistoryItem.setCommentCode(editModel.getCommentCode());
			updateHistoryItem.setProcessedUser(UserProfileUtils.getFullName());
			updateHistoryItem.setStatus(processingStatus.getStatusName());
			updateHistoryItem.setCreateDate(new Date());
			updateHistoryItem.setCreateBy(UserProfileUtils.getUserNameLogin());
			contactEmailUpdateItemRepository.save(updateHistoryItem);
			
			emailEntity = contactEmailRepository.save(emailEntity);
			
			emailDto = new ContactEmailDto(emailEntity);
			List<ContactEmailUpdateItemDto> updateHistory = contactEmailUpdateItemRepository.findByEmailId(emailId);
			emailDto.setUpdateHistory(updateHistory);
		}catch (Exception e) {
			logger.error("processUpdateEmail: " + e.getMessage());
			throw new BusinessException("Cập nhật thông tin process email lỗi");
		}
		return emailDto;
	}
}
