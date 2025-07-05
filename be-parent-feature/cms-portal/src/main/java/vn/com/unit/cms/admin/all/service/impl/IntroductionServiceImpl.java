package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.core.IntroductionCategoryNode;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.ExportIntroductionReportDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.dto.IntroductionSearchDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.entity.Introduction;
import vn.com.unit.cms.admin.all.entity.IntroductionLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportIntroductionExportEnum;
import vn.com.unit.cms.admin.all.enumdef.GenderEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.jcanary.repository.LanguageRepository;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.IntroductionCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.repository.IntroductionCategoryRepository;
import vn.com.unit.cms.admin.all.repository.IntroductionLanguageRepository;
import vn.com.unit.cms.admin.all.repository.IntroductionRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.IntroductionCategoryService;
import vn.com.unit.cms.admin.all.service.IntroductionService;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.repository.LanguageRepository;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.utils.Utility;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
import vn.com.unit.ep2p.utils.SearchUtil;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * IntroduceManagementServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Service
@Transactional
public class IntroductionServiceImpl extends DocumentWorkflowCommonServiceImpl<IntroductionDto, IntroductionDto>
        implements IntroductionService {

    private static final String SHORT_CONTENT = "shortContent";
    private static final String TITLE = "title";
    private static final String CATEGORY_NAME = "categoryName";
    private static final String CATEGORY_CODE = "categoryCode";
    private static final String KEY_WORD = "keyWord";
    private static final String SUB_TITLE = "subTitle";
    private static final String NAME = "name";
    private static final String CODE = "code";
    private static final String NOTE = "note";
    private static final String DESCRIPTION = "description";
    
    private static final String[] introductionSearchFieldDispNames = {"searchfield.disp.code", "searchfield.disp.name", "searchfield.disp.note", "searchfield.disp.description", "searchfield.disp.subtitle","searchfield.disp.keyword", "searchfield.disp.categorycode", "searchfield.disp.categoryname", "searchfield.disp.title", "searchfield.disp.shortcontent"};

    private static final String[] introductionSearchFieldIds = {CODE, NAME, NOTE, DESCRIPTION, SUB_TITLE, KEY_WORD, CATEGORY_CODE, CATEGORY_NAME, TITLE, SHORT_CONTENT};
    
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(IntroductionServiceImpl.class);
	
	private static final String PREFIX_CODE = "INTRO.";

    @Autowired
    IntroductionRepository introRepo;
    
    @Autowired
    IntroductionCategoryRepository introCateRepo;
    
    @Autowired
    IntroductionCategoryLanguageRepository introCateLangRepo;
    
    @Autowired
    IntroductionLanguageRepository introLangRepo;
    
    @Autowired
    @Qualifier("languageRepository")
    LanguageRepository langRepo;
  
    @Autowired
    MessageSource msg;
    
//	@Autowired
//	JProcessService jprocessService;
//
//    @Autowired
//    private ProcessService processService;
//    
//    @Autowired
//    HistoryApproveService historyApproveService;
    
    @Autowired
    CmsFileService fileService;
    
	@Autowired
	private CmsCommonService commonService;
	
//	@Autowired
//	EmailUtil emailUtil;
	
    @Autowired
    ServletContext servletContext;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    private IntroductionCategoryService introductionCategoryService;
        
    
    @Override
    public List<SearchKeyDto> genIntroductionSearchKeyList(Locale locale) {
        List<SearchKeyDto> searchKeys = SearchUtil.genSearchKeyList(introductionSearchFieldIds, introductionSearchFieldDispNames, locale, msg);
        return searchKeys;
    }
    
    public List<IntroductionCategoryNode> loadAllSubNodes(List<IntroductionCategoryNode> parentNodeList, List<IntroductionCategoryDto> listAllCategory){
        List<IntroductionCategoryNode> retVal = new ArrayList<IntroductionCategoryNode>();
        for(IntroductionCategoryNode parentNodeItem : parentNodeList){
            List<IntroductionCategoryNode> listSub = getListNodeSubCategory(parentNodeItem.getId(), listAllCategory);
            parentNodeItem.setChildren(listSub);
            if(listSub != null && listSub.size() > 0){
                retVal.addAll(listSub);
            }
        }
        return retVal;
    }
    
    public List<IntroductionCategoryNode> getListNodeSubCategory(Long menuId, List<IntroductionCategoryDto> listAllCategory){
        List<IntroductionCategoryNode> listSubCategory = new LinkedList<IntroductionCategoryNode>();
        for(IntroductionCategoryDto menu : listAllCategory){
            if(menu.getParentId() != null && menu.getParentId().equals(menuId)){
                IntroductionCategoryNode categoryNode = new IntroductionCategoryNode();
                categoryNode.setId(menu.getId());
                categoryNode.setText(menu.getName());
                categoryNode.setState(ConstantCore.OPEN);
                listSubCategory.add(categoryNode);
            }
        }
        return listSubCategory;
    }
    
    private IntroductionSearchDto genIntroductionSearchCondition(CommonSearchDto searchDto) {
        IntroductionSearchDto searchCondition = new IntroductionSearchDto();
        if (searchDto.getCode() != null && !searchDto.getCode().equals("")) {
            searchCondition.setCode(searchDto.getCode());
        }

        if (searchDto.getName() != null && !searchDto.getName().equals("")) {
            searchCondition.setName(searchDto.getName());
        }

        if (searchDto.getStatus() != null && !searchDto.getStatus().equals("")) {
            searchCondition.setStatus(Integer.parseInt(searchDto.getStatus()));
        }
        
        if (searchDto.getCategoryId() != null) {
            searchCondition.setCategoryId(searchDto.getCategoryId());
        }
        
        if (searchDto.getEnabled() != null){
        	searchCondition.setEnabled(searchDto.getEnabled());
        }
        
        return searchCondition;
    }
    
    public List<IntroductionCategoryDto> sortCategoryDtoByConstructorTree(List<IntroductionCategoryDto> categoryList) {
        // Find roots
        List<IntroductionCategoryDto> result = getMenuRoot(categoryList);
        
        int i=0;
        while ( i < result.size() ) {
            IntroductionCategoryDto categoryRootNodeDto = result.get(i);
            Long categoryId = categoryRootNodeDto.getId();
            
            List<IntroductionCategoryDto> categoryChildren = new ArrayList<IntroductionCategoryDto>();
            for (IntroductionCategoryDto categoryDto: categoryList) {
                Long parentId = categoryDto.getParentId();
                if( parentId != null && categoryId.equals(parentId)) {
                    result.add(i + 1, categoryDto);
                    categoryChildren.add(categoryDto);
                }
            }
            categoryList.removeAll(categoryChildren);
            i++;
        }
        
        return result;
    }
    
    private List<IntroductionCategoryDto> getMenuRoot( List<IntroductionCategoryDto> categoryList) {
        List<IntroductionCategoryDto> result = new ArrayList<IntroductionCategoryDto>();
        // Find roots
        for (int i = 0; i < categoryList.size(); i ++) {
            IntroductionCategoryDto categoryDto = categoryList.get(i);
            if(null == categoryDto.getParentId() || categoryDto.getParentId().equals(0l)){
                result.add(categoryDto);
            }
        }
        
        return result;
    }
    /**
     * Return introduction detail
     *
     * @param code
     *            substring of shareholder code
     * @return introductionDto
     * @see
     */
    @Override
    public IntroductionDto getDetailById(Long id) {
        Introduction entity = introRepo.findOne(id);
        return new IntroductionDto(entity);
    }
    
    /**
     * @param page int 
     * @param sizeOfpage int
     */
    @Override
    public PageWrapper<IntroductionDto> getAllActiveIntroduction(int page, int sizeOfPage, Locale locale){
        PageWrapper<IntroductionDto> pageWrapper = new PageWrapper<IntroductionDto>(
                page, sizeOfPage);

        List<Introduction> shareHolders = new ArrayList<Introduction>();
        int count = introRepo.countActive();

        if (count > 0) {
            int itemOffset = Utility.calculateOffsetSQL(page, sizeOfPage);
            shareHolders = introRepo.findAllActive(itemOffset, sizeOfPage, locale.toString());
        }
        List<IntroductionDto> dtoList = new ArrayList<IntroductionDto>();
        for (Introduction entity : shareHolders) {
            IntroductionDto dto = new IntroductionDto(entity);
            if(dto.getStatus() == null){
                dto.setStatus(StepStatusEnum.DRAFT.getStepNo());
            }
            // set IntroductionCategory for dto
            IntroductionCategoryDto introductionCategoryDto= new IntroductionCategoryDto();
            introductionCategoryDto = introductionCategoryService.getIntroductionById(entity.getCategoryId(), locale.toString());
            dto.setIntroductionCategoryDto(introductionCategoryDto);
            String title = introLangRepo.findTitleByIntroductionIdAndLanguageCode(dto.getId(), locale.toString());
            dto.setTitle(title);
            dto.setStatusName(introductionCategoryDto.getStatusName());
            dtoList.add(dto);
        }
        
        pageWrapper.setDataAndCount(dtoList, count);
        return pageWrapper;
    }
    
    @Override
	public List<IntroductionDto> getAllActiveIntroduction(String language) {
        List<Introduction> introductions = introRepo.findAllActiveNoPaging(language);
        List<IntroductionDto> dtoList = new ArrayList<IntroductionDto>();
        for (Introduction entity : introductions) {
            IntroductionDto dto = new IntroductionDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * @param page
     * @param sizeOfPage
     * @param condition
     */
    @Override
    public PageWrapper<IntroductionLanguageDto> getActiveIntroductionByCondition(int page, int sizeOfPage, CommonSearchDto searchDto, Locale locale){
        IntroductionSearchDto condition = this.genIntroductionSearchCondition(searchDto);
        PageWrapper<IntroductionLanguageDto> pageWrapper = new PageWrapper<IntroductionLanguageDto>(
                page, sizeOfPage);

        condition.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
        
        List<IntroductionLanguageDto> dtoList = null;
        int count = introRepo.countActiveByConditions(condition, locale.toString());
        
		if ((count % sizeOfPage == 0 && page > count / sizeOfPage)
				|| (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
			page = 1;
		}
		
        if (count > 0) {
            int itemOffset = Utility.calculateOffsetSQL(page, sizeOfPage);
            dtoList = introRepo.findActiveByConditions(itemOffset, sizeOfPage, condition, locale.toString());
        }
        
        pageWrapper.setDataAndCount(dtoList, count);
        return pageWrapper;
     }
    
    /**
     * Return update result
     *
     * @param updateModel
     * @return true/false update result
     * @throws IOException 
     * @see
     */
    @Override
	public IntroductionDto doEdit(IntroductionDto updateDto, Locale locale, HttpServletRequest request)
			throws IOException {
    	
		// user name login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();
        
        createOrEditIntroduction(updateDto, UserProfileUtils.getUserNameLogin(), locale, request);
        
        createOrEditIntroductionLanguage(updateDto, locale);
        
		// move file upload
		CmsUtils.moveTempSubFolderToUpload(
				Paths.get(AdminConstant.INTRODUCTION_FOLDER, AdminConstant.INTRODUCTION_EDITOR_FOLDER, updateDto.getRequestToken())
						.toString());
		
		// if action process
		if (!StringUtils.equals(updateDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
			// update history approve
			updateHistoryApprove(updateDto, locale);
			
			// send mail
	        sendMail(updateDto, request, locale);
		}
		
        return updateDto;
    }

	private void sendMail(IntroductionDto editDto, HttpServletRequest request, Locale locale) {
		try {
            EmailCommonDto emailCommon = new EmailCommonDto();
            emailCommon.setActionName(msg.getMessage("email.template.introduction", null, locale));
            emailCommon.setButtonAction(editDto.getButtonAction());
            emailCommon.setButtonId(editDto.getButtonId().toString());
            emailCommon.setComment(editDto.getIntroductionComment());

            // Nội dung
            LinkedHashMap<String, String> content = new LinkedHashMap<>();
            
            IntroductionCategoryDto detailDto = introductionCategoryService.getCategory(editDto.getCategoryId(), locale, CmsCommonConstant.HDBANK_BUSINESS_INTRODUCTION);
            
            content.put("Mã", editDto.getCode());
            content.put("Danh Mục Giới thiệu", detailDto.getInfoByLanguages().get(0).getLabel());
            content.put("Danh sách giới thiệu", editDto.getInfoByLanguages().get(0).getTitle());
            emailCommon.setContent(content);

            emailCommon.setCurrItem(editDto.getCurrItem());

            emailCommon.setId(editDto.getId());
            emailCommon.setOldStatus(editDto.getOldStatus());
            emailCommon.setProcessId(editDto.getProcessId());
            emailCommon.setReferenceType(editDto.getReferenceType());
            emailCommon.setStatus(editDto.getStatus());

            // Subject của email
            emailCommon.setSubject(msg.getMessage("subject.email.template.introduction", null, locale));

            if (request != null) {
                emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/introduction/edit?id=" + editDto.getId());
            }

//            emailUtil.sendMail(emailCommon, request, locale);
        } catch (Exception e) {
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
	}

	private void createOrEditIntroductionLanguage(IntroductionDto editDto, Locale locale) {
		try{
			List<IntroductionLanguage> introLangEntities = editDto.createIntroLanguageEntities();
			Introduction entity = introRepo.findOne(editDto.getId());

			for (IntroductionLanguage introLangEntity : introLangEntities) {
				introLangEntity.setIntroductionId(entity.getId());
			}

			if (entity != null) {
				introLangRepo.save(introLangEntities);
				List<IntroductionLanguageDto> introLangDtos = loadIntroductionInfoByLanguage(entity);
				editDto.setInfoByLanguages(introLangDtos);
			}
			
		} catch (Exception e) {
			logger.error("createOdEditIntroductionLanguage: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	private void createOrEditIntroduction(IntroductionDto editDto, String usernameLogin, Locale locale,
			HttpServletRequest request) {
		
		Introduction entity = new Introduction();

		if (null != editDto.getId()) {
			entity = introRepo.findOne(editDto.getId());

			  // dữ liệu ko tồn tại hoặc đã bị xóa
            if (entity == null || entity.getDeleteDate() != null) {
            	throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }

			if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(usernameLogin);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(usernameLogin);
			// code generation
			entity.setCode(
					CommonUtil.getNextCode(PREFIX_CODE, commonService.getMaxCode("M_INTRODUCTION", PREFIX_CODE)));
		}
		try{
			editDto.createIntroEntity();
			entity.setEnabled(editDto.isEnabled());
	        entity.setCategoryId(editDto.getCategoryId());
	        entity.setName(editDto.getName());
	        entity.setLinkAlias(editDto.getLinkAlias());
	        entity.setNote(editDto.getNote());
	        entity.setDescription(editDto.getDescription());
	        entity.setSubTitle(editDto.getSubTitle());
	        entity.setImageUrl(editDto.getImageUrl());
	        entity.setKeyWord(editDto.getKeyWord());
	        entity.setSort(editDto.getSort());
	        entity.setGender(editDto.getGender());
	        entity.setViews(editDto.getViews());
	        entity.setProcessId(editDto.getProcessId());
	        entity.setStatus(editDto.getStatus());
	        entity.setIntroductionPhysicalVideo(editDto.getIntroductionPhysicalVideo());
	        entity.setIntroductionTitleVideo(editDto.getIntroductionTitleVideo());
	        entity.setIntroductionVideo(editDto.getIntroductionVideo());
	        entity.setApprovedBy(editDto.getApprovedBy());
	        entity.setPublishedBy(editDto.getPublishedBy());
	        entity.setCustomerTypeId(11L);
	        entity.setOwnerBranchId(UserProfileUtils.getBranchId());
	        entity.setOwnerId(UserProfileUtils.getAccountId());
	        entity.setOwnerSectionId(UserProfileUtils.getDepartmentId());
	        entity.setCreateDate(new Date());
	        entity.setCreateBy(usernameLogin);
	        entity.setIntroductionComment(editDto.getComment());
	        
    		// set sort and beforeId for dto
    		updateSortAndBeforeId(editDto, locale);
    		entity.setBeforeId(editDto.getBeforeId());
    		entity.setSort(editDto.getSort());
	        
	        moveTmpImage(editDto, entity);
	        
//			// if action process
//			if (!StringUtils.equals(editDto.getButtonId(), StepActionEnum.SAVE.getCode())) {
//				if (editDto.getProcessId() == null) {
//					// First step
//					JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(editDto.getBusinessCode(),
//							locale.toString());
//					// set process id
//					editDto.setProcessId(processDto.getProcessId());
//				}
//
//				// current step
//				JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(editDto.getProcessId(),
//						editDto.getStatus(), editDto.getButtonId());
//				Integer status = jprocessService.getNextStepNo(currentActionStep, null);
//				
//				editDto.setOldStatus(editDto.getStatus());
//				// set status
//				editDto.setStatus(status);
//				editDto.setCurrItem(currentActionStep.getItems());
//			}
			
			entity.setProcessId(editDto.getProcessId());
			entity.setStatus(editDto.getStatus());
			
			introRepo.save(entity);
			
			if (entity.getId() != null) {
				editDto.setId(entity.getId());
				editDto.setCode(entity.getCode());
			}
			
    		// update before id for product after product edited
			updateBeforeIdForIntroductionAfter(editDto, locale);
			
		} catch (Exception e) {
			logger.error("createOrEditProductCategory: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}
	
	private void updateSortAndBeforeId(IntroductionDto editDto, Locale locale) {
		List<IntroductionDto>  lstProductType = introRepo.findAllActiveByCategorySort(11L, locale.toString(), editDto.getCategoryId());
		if (null != editDto.getBeforeId()) {
			// tim product truoc va gan sort cho productEdit
			int indexBefore = lstProductType.size();
			for (int i = 0, sz = indexBefore; i < sz; i++) {
				IntroductionDto item = lstProductType.get(i);

				if (item.getId().equals(editDto.getBeforeId())) {
					indexBefore = i;
					editDto.setSort(item.getSort() + 1);
					break;
				}
			}

			// gan sort cho cac product sau produtEdit
			Long currentSort = editDto.getSort() + 1;
			for (int i = indexBefore + 1, sz = lstProductType.size(); i < sz; i++) {
				IntroductionDto item = lstProductType.get(i);
				Introduction entity = introRepo.findOne(item.getId());
				entity.setSort(currentSort++);
				introRepo.save(entity);
			}
		} else {
			if (CollectionUtils.isNotEmpty(lstProductType)) {
				IntroductionDto item = lstProductType.get(lstProductType.size() - 1);
				editDto.setSort(item.getSort() + 1);
				if (!item.getId().equals(editDto.getId())) {
					editDto.setBeforeId(item.getId());
				} else if (lstProductType.size() > 2) {
					editDto.setBeforeId(lstProductType.get(lstProductType.size() - 2).getId());
				} else {
					editDto.setBeforeId(0l);
				}
			} else {
				editDto.setSort(1l);
				editDto.setBeforeId(0l);
			}
		}
	}

	private void updateBeforeIdForIntroductionAfter(IntroductionDto editDto, Locale locale) {
		List<IntroductionDto>  lstProductType = introRepo.findAllActiveByCategorySort(11L, locale.toString(), editDto.getCategoryId());
		
		if (CollectionUtils.isEmpty(lstProductType)) {
			return;
		}

		IntroductionDto item = lstProductType.get(0);
		if (item.getId().equals(editDto.getBeforeId()) && !item.getBeforeId().equals(0l)) {
			Introduction entity = introRepo.findOne(item.getId());
			entity.setBeforeId(0l);
			introRepo.save(entity);
		}

		for (int i = 0, sz = lstProductType.size(); i < sz - 1; i++) {
			item = lstProductType.get(i);
			if (item.getId().equals(editDto.getId())) {
				IntroductionDto itemNext = lstProductType.get(i + 1);
				Introduction entity = introRepo.findOne(itemNext.getId());
				entity.setBeforeId(item.getId());

				introRepo.save(entity);
				break;
			}
		}
	}

	/**
     * Create new IntroductionDto with empty data (for add mode)
     * @return IntroductionDto
     */
    @Override
    public IntroductionDto initIntroductionDto(){
        IntroductionDto detailDto = new IntroductionDto();
        List<IntroductionLanguageDto> lstInfoByLanguage = new ArrayList<IntroductionLanguageDto>();
        List<Language> languageList = langRepo.findAllActive();
        for(Language lang: languageList){
            IntroductionLanguageDto introCateLangDto = new IntroductionLanguageDto();
            introCateLangDto.setLanguageCode(lang.getCode());
            introCateLangDto.setLanguageDispName(lang.getName());
            lstInfoByLanguage.add(introCateLangDto);
            detailDto.setInfoByLanguages(lstInfoByLanguage);
        }
        detailDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
//        detailDto.setProcessId(processService.getProcessIdByBusinessCode(MasterType.AI1.toString()));
        detailDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTRODUCTION);
        Long sort = introRepo.findMaxSort(null);
        detailDto.setSort(sort == null?1:sort + 1);
        return detailDto;
    }
    /**
     * @param Introduction entity
     * @return
     */
	private List<IntroductionLanguageDto> loadIntroductionInfoByLanguage(Introduction introEntity) {
		Long introductionId = introEntity.getId();
		return loadIntroductionInfoByLanguage(introductionId);
	}

    /**
     * @param introductionId
     * @return
     */
    private List<IntroductionLanguageDto> loadIntroductionInfoByLanguage(Long introductionId) {
        List<IntroductionLanguage> infoBylanguageEntities = introLangRepo.findByIntroductionId(introductionId);
        HashMap<String, IntroductionLanguage> introLangMap = new HashMap<String, IntroductionLanguage>();
        for(IntroductionLanguage infoByLangEntity : infoBylanguageEntities){
            introLangMap.put(infoByLangEntity.getLanguageCode(), infoByLangEntity);
        }
        List<Language> languages = langRepo.findAllActive();
        List<IntroductionLanguageDto> introLangDtos = new ArrayList<IntroductionLanguageDto>();
        for(Language language : languages){
            IntroductionLanguage introLangEntity = introLangMap.get(language.getCode());
            if(introLangEntity != null){
                IntroductionLanguageDto introLangDto = new IntroductionLanguageDto(introLangEntity, language.getName());
                introLangDtos.add(introLangDto);
            }else{
                IntroductionLanguageDto introLangDto = new IntroductionLanguageDto();
                introLangDto.setIntroductionId(introductionId);
                introLangDto.setLanguageCode(language.getCode());
                introLangDto.setLanguageDispName(language.getName());
                introLangDtos.add(introLangDto);
            }
        }
        return introLangDtos;
    }

    /**
     * @param updateDto
     * @param entity
     * @throws IOException 
     */
	private void moveTmpImage(IntroductionDto updateDto, Introduction entity) throws IOException {
		String physicalImgTmpName = updateDto.getImagePhysicalName();
		// upload images
		if (StringUtils.isNotEmpty(physicalImgTmpName)) {
			String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName, AdminConstant.INTRODUCTION_FOLDER);
			entity.setImageUrl(newPhiscalName);
			entity.setImageName(updateDto.getImageName());
		}
	}
    
    /**
     * @param introductionId 
     */
    @Override
    public IntroductionDto getIntroductionViewDto(Long introductionId){
        IntroductionDto introductionDto = introRepo.findIntroductionViewDetail(introductionId);
        if(introductionDto != null){
//            List<IntroductionLanguage> infoBylanguage = introLangRepo.findByIntroductionId(entity.getId());
            List<IntroductionLanguageDto> introLangDtos = loadIntroductionInfoByLanguage(
                    introductionDto.getId());
            introductionDto.setInfoByLanguages(introLangDtos);
            if(introductionDto.getGender() != null && !introductionDto.getGender().equals(0)){
            	for (GenderEnum en : GenderEnum.class.getEnumConstants()){
            		if(introductionDto.getGender().equals(en.genderValue())){
            			introductionDto.setGenderNameKey(en.genderTitle());
            		}
            	}
            }
            
//            List<HistoryApproveDto> listHistoryApproveDto = historyApproveService.getInforHistoryApprove(introductionDto.getReferenceId(), ConstantHistoryApprove.APPROVE_INTRODUCTION);
//            introductionDto.setHistoryApproveDtos(listHistoryApproveDto);
//            introductionDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTRODUCTION);
        }
        // Get information jca_m_history_approve
        return introductionDto;
    }

    @Override
    public boolean deleteIntroById(Long id){
        Introduction entity = introRepo.findOne(id);
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String username = UserProfileUtils.getUserNameLogin();
        Date today = new Date();
        entity.setDeleteBy(username);
        entity.setDeleteDate(today);
        List<IntroductionLanguage> introLangEntities = introLangRepo.findByIntroductionId(entity.getId());
        for (IntroductionLanguage introLan : introLangEntities){
            introLan.setDeleteBy(username);
            introLan.setDeleteDate(today);
            introLangRepo.save(introLan);
        }
        Introduction resultEntity = introRepo.save(entity);
//        this.abortJbpmProcess(entity.getProcessId(), entity.getProcessIntanceId());
        return resultEntity != null;
    }

    @Override
    public int countIntroductionByCode(String code) {
        int count = introRepo.countByCode(code);
        return count;
    }
    
    /**
     * @param id
     */
    @Override
    public IntroductionDto getIntroductionObject(Long id) {
        Introduction entity = introRepo.findOne(id);
        if(entity == null){
            return null;
        }else{
            return new IntroductionDto(entity);
        }
    }
    
    /**
     * updateHistoryApprove
     *
     * @param introductionDto
     * @author TranLTH
     */
    @Transactional
    public void updateHistoryApprove (IntroductionDto editDto, Locale locale){
		try {
			// insert comment
			HistoryApproveDto historyApproveDto = new HistoryApproveDto();
			historyApproveDto.setApprover(UserProfileUtils.getFullName());
			historyApproveDto.setComment(editDto.getIntroductionComment());
			historyApproveDto.setProcessId(editDto.getProcessId());
			historyApproveDto.setProcessStep(editDto.getStatus().longValue());
			historyApproveDto.setReferenceId(editDto.getId());
			historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTRODUCTION);
			historyApproveDto.setActionId(editDto.getButtonId().toString());
			historyApproveDto.setOldStep(editDto.getOldStatus());
			historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//			historyApproveService.addHistoryApprove(historyApproveDto);
		} catch (Exception e) {
			logger.error("updateHistoryApprove: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
    }

    @Override
    public boolean requestDownloadImage(String imageUrl, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        fileService.download(imageUrl, request, response);
        return true;
    }
    
    @Override
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response){
        boolean retVal = false;
        if(fileUrl != null){
            if(CmsUtils.fileExistedInMain(fileUrl)){
                fileService.download(fileUrl, request, response);
                retVal =true;
            }else if(CmsUtils.fileExistedInTemp(fileUrl)){
                fileService.downloadTemp(fileUrl, request, response);
                retVal = true;
            } 
        }
        return retVal;
    }

    
    
    @Override
    public Long getMaxIntroductionSort(Long categoryId) {
        Long maxSort = introRepo.findMaxSort(categoryId);
        if(maxSort == null){
            maxSort = 0L;
        }
        return maxSort;
    }

    @Override
	public void updateModelsSort(SortPageDto sortPageModel) {
		if(sortPageModel.getSortList() != null){
			for(SortOrderDto sortItem : sortPageModel.getSortList()){
				introRepo.updateSortAll(sortItem);
			}
		}
		
		Long itemId = 0L;
		for (SortOrderDto dto : sortPageModel.getSortList()) {
			Introduction item = introRepo.findOne(dto.getObjectId());
			item.setBeforeId(itemId);
			itemId = item.getId();
			introRepo.save(item);
		}
	}

	@Override
	public void initSortPage(Long categoryId, ModelAndView mav, Locale locale) {
	    List<IntroductionDto> introductionList = new ArrayList<>();
             if (null != categoryId){
                 introductionList = introRepo.findAllActiveByCategorySort(11L, locale.toString(), categoryId);
             }
	   	SortPageDto sortPageModel = this.createSortOrderDtoList(introductionList);
       	mav.addObject("sortPageModel", sortPageModel);
        mav.addObject("sortList", introductionList);
	}
	
	private SortPageDto createSortOrderDtoList(List<IntroductionDto> introductionList) {
		SortPageDto sortPageModel = new SortPageDto();
		List<SortOrderDto> sortList = new ArrayList<SortOrderDto>();
		Long sortIndex = 0L;
		for(IntroductionDto shareholderDto : introductionList){
			SortOrderDto sortItem = new SortOrderDto();
			sortItem.setObjectId(shareholderDto.getId());
			sortItem.setSortValue(sortIndex);
			sortList.add(sortItem);
		}
		sortPageModel.setSortList(sortList);
		return sortPageModel;
	}

	@Override
	public int countCategoryItemWithAlias(String languageCode, Long categoryId, String linkAlias, Long exceptedId) {
		int sameAliasCount = introRepo.countSameLinkAliasInCategoryWithExceptedId(languageCode, linkAlias, categoryId, exceptedId); 
		return sameAliasCount;
	}

	/** getMaxCodeIntroduction
    *
    * @author nhutnn
    * @return max code
    */
    @Override
    public String getMaxCodeIntroduction() {
        return introRepo.getMaxCode();
    }
    
    @Override
    public List<IntroductionCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang) {
        List<IntroductionCategoryDto> categories = introCateRepo.findAllRootActive(lang);
        List<IntroductionCategoryDto> listCategoryDto = loadFullCategoryTreeByRoots(categories, true,
                exceptCategoryId, lang);

        List<IntroductionCategoryNode> categoryTree = new LinkedList<IntroductionCategoryNode>();
        List<IntroductionCategoryDto> listRoot = new LinkedList<IntroductionCategoryDto>();
        if (null != listCategoryDto && !listCategoryDto.isEmpty()) {
            if (null != listCategoryDto) {
                for (IntroductionCategoryDto category : listCategoryDto) {
                    if (null == category.getParentId() || category.getParentId().equals(0L))
                        listRoot.add(category);
                }
                List<IntroductionCategoryNode> parentNodeList = new ArrayList<IntroductionCategoryNode>();
                for (IntroductionCategoryDto categoryDto : listRoot) {
                    IntroductionCategoryNode categoryNode = new IntroductionCategoryNode();
                    categoryNode.setId(categoryDto.getId());
                    categoryNode.setText(categoryDto.getTitle());
                    categoryNode.setState(ConstantCore.OPEN);
                    categoryTree.add(categoryNode);
                    parentNodeList.add(categoryNode);
                }
                do {
                    parentNodeList = loadAllSubNodes(parentNodeList, listCategoryDto);
                } while (parentNodeList != null && parentNodeList.size() > 0);

            }
        }
        return categoryTree;
    }
    
    private List<IntroductionCategoryDto> loadFullCategoryTreeByRoots(List<IntroductionCategoryDto> categories,
            boolean forSelection, Long exceptCategoryId, String lang) {
        if (exceptCategoryId != null) {
            categories = removeExceptCategory(categories, exceptCategoryId);
        }
        categories = loadDescendandCategories(categories, forSelection, exceptCategoryId, lang);
        List<IntroductionCategoryDto> categoryDtos = new ArrayList<IntroductionCategoryDto>();
        for (IntroductionCategoryDto category : categories) {
            categoryDtos.add(category);
        }
        categoryDtos = sortCategoryDtoByConstructorTree(categoryDtos);
        return categoryDtos;
    }
    
    private List<IntroductionCategoryDto> removeExceptCategory(List<IntroductionCategoryDto> categories,
            Long exceptCategoryId) {
        for (int index = 0; index < categories.size(); ++index) {
            IntroductionCategoryDto iCategory = categories.get(index);
            if (iCategory.getId().equals(exceptCategoryId)) {
                categories.remove(index);
                break;
            }
        }
        return categories;
    }
    
    private List<IntroductionCategoryDto> loadDescendandCategories(List<IntroductionCategoryDto> categories,
            boolean forSelection, Long exceptCategoryId, String lang) {
        List<Long> selectionParentIds = createCategoryIdList(categories);
        if (selectionParentIds.size() > 0) {
            List<IntroductionCategoryDto> childrens;
            do {
                if (forSelection) {
                    childrens = introCateRepo.findAllActiveChildrenCategoryForSelection(selectionParentIds, lang);
                } else {
                    childrens = introCateRepo.findAllActiveChildrenCategory(selectionParentIds, lang);
                }
                if (exceptCategoryId != null) {
                    childrens = removeExceptCategory(childrens, exceptCategoryId);
                }
                selectionParentIds = this.createCategoryIdList(childrens);
                categories.addAll(childrens);
            } while (childrens != null && childrens.size() > 0);
        }
        return categories;
    }
    
    private List<Long> createCategoryIdList(List<IntroductionCategoryDto> categories) {
        List<Long> idList = new ArrayList<Long>();
        for (IntroductionCategoryDto category : categories) {
            idList.add(category.getId());
        }
        return idList;
    }
    
    @Override
    public List<IntroductionCategoryDto> getCategoriesForSelection(Long exceptCategoryId, String lang) {
        List<IntroductionCategoryDto> categories = introCateRepo.findRootListForSelection();

        List<IntroductionCategoryDto> categoryDtos = loadFullCategoryTreeByRoots(categories, true, exceptCategoryId,
                lang);
        return categoryDtos;
    }
    
    @Override
    public Long getMaxCategorySort(Long parentId) {
        Long maxSort = introCateRepo.findMaxSort(parentId);
        if(maxSort == null){
            maxSort = 0L;
        }
        return maxSort;
    }

	@Override
	public IntroductionDto getIntroductionUpdateDto(Long id, Locale locale, String businessCode) {
		IntroductionDto resultDto = new IntroductionDto();
		if (id == null){
			resultDto.setEnabled(Boolean.TRUE);
			resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
			
	        List<IntroductionLanguageDto> lstInfoByLanguage = new ArrayList<IntroductionLanguageDto>();
	        List<Language> languageList = langRepo.findAllActive();
	        for(Language lang: languageList){
	            IntroductionLanguageDto introCateLangDto = new IntroductionLanguageDto();
	            introCateLangDto.setLanguageCode(lang.getCode());
	            introCateLangDto.setLanguageDispName(lang.getName());
	            lstInfoByLanguage.add(introCateLangDto);
	            resultDto.setInfoByLanguages(lstInfoByLanguage);
	        }
	        
		}else{
	        Introduction entity = introRepo.findOne(id);
	        
	  	  	// dữ liệu ko tồn tại hoặc đã bị xóa
            if (entity == null || entity.getDeleteDate() != null) {
            	throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }
	        if (null != entity){
	            resultDto = new IntroductionDto(entity);
	            resultDto.setEnabled(entity.isEnabled());
	            resultDto.setReferenceId(entity.getId());
	            resultDto.setIntroductionComment(entity.getIntroductionComment());

	            resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTRODUCTION);
	            
	            List<IntroductionLanguageDto> introLangDtos = loadIntroductionInfoByLanguage(entity);
	            resultDto.setInfoByLanguages(introLangDtos);
	        }
		}
		
//		Long processId = resultDto.getProcessId();
//		if (processId == null) {
//			// First step
//			JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//			processId = processDto.getProcessId();
//
//		}
//		// List button of step
//		List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//				resultDto.getStatus(), locale.toString());
//		resultDto.setStepBtnList(stepButtonList);
//
//		String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//		resultDto.setStatusName(statusName);
//		
//		String statusCode = jprocessService.getStatusCode(resultDto.getProcessId(), resultDto.getStatus(), locale);
//		resultDto.setStatusCode(statusCode);
		
        return resultDto;
	}

	@Override
	public List<Select2Dto> getListForSort(String language, Long customerId, Long categoryId, Long id) {
		return introRepo.findListForSort(language, customerId, categoryId, id);
	}

	@Override
	public void exportExcel(CommonSearchDto searchDto, HttpServletResponse res, Locale locale) {
		try {
			// set status name
			searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

			/* change template */
			String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_INTRODUCTION;
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CmsCommonConstant.TYPE_EXCEL;
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

			List<ExportIntroductionReportDto> lstData = introRepo.exportExcelWithCondition(searchDto);
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(ExportIntroductionExportEnum.class, cols);
			ExportExcelUtil<ExportIntroductionReportDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportIntroductionReportDto.class,
					cols, datePattern, res, templateName);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void setDataForSearchDto(CommonSearchDto searchDto, String codeSearch, String nameSearch,
			Integer statusSearch, Integer enabledSearch, Long categoryIdSearch) {
		if (codeSearch != null){
			searchDto.setCode(codeSearch);
		}
		if (nameSearch != null){
			searchDto.setName(nameSearch);
		}
		if (statusSearch != null){
			searchDto.setStatus(statusSearch.toString());
		}
		if (enabledSearch != null){
			searchDto.setEnabled(enabledSearch);
		}
		if (categoryIdSearch != null){
			searchDto.setCategoryId(categoryIdSearch);
		}
	}

    @Override
    public IntroductionDto getEdit(Long id, String customerAlias, Locale locale) {
    	
		String businessCode = CmsCommonConstant.HDBANK_BUSINESS_INTRODUCTION;
		
        logger.error("NEED CHANGE BUSINESS CODE");
        // TODO remove "BUSINESS_BANNER"
        businessCode = "BUSINESS_BANNER"; // remove this line, only for test
        logger.error("NEED CHANGE BUSINESS CODE");
    	
        return getIntroductionUpdateDto(id, locale, businessCode);
    }
}