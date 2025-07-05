/*******************************************************************************
 * Class        ：ProductServiceImpl
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

//import vn.com.unit.common.exception.SystemException;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.constant.CmsStepNoStatusConstant;
import vn.com.unit.cms.admin.all.dto.CustomerTypeDto;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.ExportProductReportDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubEditDto;
import vn.com.unit.cms.admin.all.dto.ProductDetaiLanguageDto;
import vn.com.unit.cms.admin.all.dto.ProductDetailDto;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductSearchDto;
import vn.com.unit.cms.admin.all.dto.TermListDto;
import vn.com.unit.cms.admin.all.entity.Product;
import vn.com.unit.cms.admin.all.entity.ProductDetail;
import vn.com.unit.cms.admin.all.entity.ProductLanguage;
import vn.com.unit.cms.admin.all.entity.TermLanguage;
import vn.com.unit.cms.admin.all.enumdef.BannerTypeEnum;
import vn.com.unit.cms.admin.all.enumdef.ExportProductExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
import vn.com.unit.cms.admin.all.repository.ProductDetailRepository;
import vn.com.unit.cms.admin.all.repository.ProductRepository;
import vn.com.unit.cms.admin.all.repository.TermLanguageRepository;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.CustomerTypeLanguageService;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.ProductCategoryLanguageService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;
import vn.com.unit.cms.admin.all.service.ProductCategorySubLanguageService;
import vn.com.unit.cms.admin.all.service.ProductCategorySubService;
import vn.com.unit.cms.admin.all.service.ProductLanguageService;
import vn.com.unit.cms.admin.all.service.ProductService;
import vn.com.unit.cms.admin.all.service.TermService;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.service.EmailService;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
import vn.com.unit.common.utils.CommonJsonUtil;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.Language;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * ProductServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductServiceImpl extends DocumentWorkflowCommonServiceImpl<ProductEditDto, ProductEditDto>
        implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLanguageService productLanguageService;

    @Autowired
    private CustomerTypeService productTypeService;

    @Autowired
    private CustomerTypeLanguageService typeLanguageService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryLanguageService categoryLanguageService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private SystemConfig systemConfig;

//    @Autowired
//    HistoryApproveService historyApproveService;
//
//    @Autowired
//    private ProcessRepository processRepository;
//
//    @Autowired
//    private JBPMService jBPMService;

    @Autowired
    private CmsFileService fileService;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductCategorySubService categorySubService;

    @Autowired
    private ProductCategorySubLanguageService categorySubLanguageService;

    @Autowired
    private TermService termService;

    @Autowired
    private TermLanguageRepository termLanguageRepository;

    @Autowired
    private BannerService bannerService;
    
    @Autowired
    private CmsCommonService commonService;
    
//	@Autowired
//	JProcessService jprocessService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	MessageSource msg;
	
    @Autowired
    ServletContext servletContext;
    
//    @Autowired
//    EmailUtil emailUtil;
    
    private static final String PREFIX_CODE = "PROD.";
    
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * init screen product edit/add
     *
     * @param mav
     *            ModelAndView
     * @param languageCode
     * @author hand
     */
    @Override
    public void initProductEdit(ModelAndView mav, ProductEditDto productEdit, Locale locale) {
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);

        List<CustomerTypeDto> productTypeList = productTypeService.findByLanguageCode(locale.toString());
        mav.addObject("productTypeList", productTypeList);

        List<TermListDto> termList = termService.findAllByLanguageCode(locale.toString());
        mav.addObject("termList", termList);
        
        List<List<BannerLanguageDto>> lstBannerLangSelect = new ArrayList<>();        
        for(LanguageDto languageDto : languageList){
            List<BannerLanguageDto> bannerDesktopList = bannerService.findBannerLanguageByTypeAndDeviceLanguage(BannerTypeEnum.BANNER_TOP.toString(), false, languageDto.getCode(), CmsStepNoStatusConstant.STEP_APPROVED);
            lstBannerLangSelect.add(bannerDesktopList);
        }
        mav.addObject("bannerDesktopList", lstBannerLangSelect);
        
        List<List<BannerLanguageDto>> lstBannerLangSelectMobile = new ArrayList<>();        
        for(LanguageDto languageDto : languageList){
            List<BannerLanguageDto> bannerMobileList = bannerService.findBannerLanguageByTypeAndDeviceLanguage(BannerTypeEnum.BANNER_TOP.toString(), true, languageDto.getCode(), CmsStepNoStatusConstant.STEP_APPROVED);
            lstBannerLangSelectMobile.add(bannerMobileList);
        }
        mav.addObject("bannerMobileList", lstBannerLangSelectMobile);
        
		// Init PageWrapper History Approval
//		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1, productEdit.getId(),
//				productEdit.getProcessId(), ConstantHistoryApprove.APPROVE_PRODUCT, locale);
//		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
    }

    /**
     * init screen product detail
     *
     * @param mav
     *            ModelAndView
     * @author hand
     */
    @Override
    public void initProductDetail(ModelAndView mav) {
        List<LanguageDto> languageList = languageService.getLanguageDtoList();

        mav.addObject("languageList", languageList);
    }

    /**
     * search Product list
     *
     * @param page
     * @param productSearch
     *            ProductSearchDto
     * @return PageWrapper<ProductLanguageSearchDto>
     * @author hand
     */
    @Override
    public PageWrapper<ProductLanguageSearchDto> search(int page, ProductSearchDto searchDto, Locale locale) {
        if (null == searchDto)
            searchDto = new ProductSearchDto();
    	int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);   

		// set status name
		searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
    	
        int count = productRepository.countByProductSearchDto(searchDto);
        if ((count % sizeOfPage == 0 && page > count / sizeOfPage) || (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
            page = 1;
        }
        
        PageWrapper<ProductLanguageSearchDto> pageWrapper = new PageWrapper<ProductLanguageSearchDto>(page, sizeOfPage);
        List<ProductLanguageSearchDto> result = new ArrayList<ProductLanguageSearchDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            result = productRepository.findByProductSearchDto(offsetSQL, sizeOfPage, searchDto);
        }

        pageWrapper.setDataAndCount(result, count);

        return pageWrapper;
    }

    /**
     * add Or Edit Product
     *
     * @param ProductEditDto
     * @param action
     *            true is saveDraft, false is sendRequest
     * @author hand
     * @throws IOException
     */
    @Override
    @Transactional
    public boolean doEdit(ProductEditDto productEditDto, Locale locale, HttpServletRequest request) throws IOException {

        Long productId = productEditDto.getId();

        // user login
//        UserProfile userProfile = UserProfileUtils.getUserProfile();

		createOrEditProduct(productEditDto, UserProfileUtils.getUserNameLogin(), UserProfileUtils.getAccountId(),
				UserProfileUtils.getBranchId(), UserProfileUtils.getDepartmentId(), locale, request);

        createOrEditLanguage(productEditDto, UserProfileUtils.getUserNameLogin());

        createOrEditProductDetail(productEditDto, UserProfileUtils.getUserNameLogin(), productId);
        
        // if action process
        try {
            if (!StringUtils.equals(productEditDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
//                // updateHistoryApprove
//                updateHistoryApprove(productEditDto);

                sendMail(productEditDto, request);

                // clear cache api /commons
                APIUtils.callApiGet(AdminUrlConst.URL_CACHES_COMMONS);

                // clear cache api /personal
                APIUtils.callApiGet(AdminUrlConst.URL_CACHES_PERSONAL);

                // clear cache api /corporate
                APIUtils.callApiGet(AdminUrlConst.URL_CACHES_CORPORATE);

                APIUtils.callApiGet(AdminUrlConst.URL_CACHES_HOME);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return true;
    }
    
//    private void updateHistoryApprove(ProductEditDto editDto){
//        try{
//            // insert comment
//            HistoryApproveDto historyApproveDto = new HistoryApproveDto();
//            historyApproveDto.setApprover(UserProfileUtils.getFullName());
//            historyApproveDto.setComment(editDto.getProductComment());
//            historyApproveDto.setProcessId(editDto.getProcessId());
//            historyApproveDto.setProcessStep(editDto.getStatus().longValue());
//            historyApproveDto.setReferenceId(editDto.getId());
//            historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_PRODUCT);
////            historyApproveDto.setActionId(editDto.getButtonId());
//            historyApproveDto.setOldStep(editDto.getOldStatus());
//            historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
////            historyApproveService.addHistoryApprove(historyApproveDto);
//        }catch (Exception e) {
//            logger.error("updateHistoryApprove: " + e.getMessage());
//        }
//    }

    /**
     * create or update Product
     *
     * @param editDto
     * @param action
     *            true is saveDraft, false is sendRequest
     * @author hand
     * @throws IOException
     */
	private void createOrEditProduct(ProductEditDto editDto, String usernameLogin, Long accountId, Long branchId,
			Long departmentId, Locale locale, HttpServletRequest request) throws IOException {

        // m_product entity
        Product entity = new Product();

        // product exists id
        if (null != editDto.getId()) {
        	
            entity = productRepository.findOne(editDto.getId());
            // dữ liệu ko tồn tại hoặc đã bị xóa
            if (null == entity || entity.getDeleteDate() != null) {
            	throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }
            
            if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())){
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
            }
            
			List<Long> lstStatus = HDBankUtil.getListStatusForDependency();
			int countDependencies = countDependencies(entity.getId(), lstStatus);

			if (countDependencies > 0) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}
            
            entity.setUpdateDate(new Date());
            entity.setUpdateBy(usernameLogin);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(usernameLogin);
            entity.setCode(CommonUtil.getNextCode(PREFIX_CODE, commonService.getMaxCode("M_PRODUCT", PREFIX_CODE)));
        }
        
		try {
			// set sort and beforeId for dto
	        updateSortAndBeforeId(editDto);
	        
	        entity.setSort(editDto.getSort());
	        entity.setBeforeId(editDto.getBeforeId());
	        
	        entity.setStatus(editDto.getStatus());
	        //entity.setProcessId(editDto.getProcessId());
	        entity.setInterestRates(editDto.getInterestRates());
	        entity.setMaxLoanAmount(CmsUtils.convertStringToBigDcimal(editDto.getMaxLoanAmountStr(), CmsUtils.PATTERN_MONEY_COMMA));
	        entity.setTermCode(editDto.getTermCode());
	        entity.setTermType(editDto.getTermType());
	        entity.setTermValue(editDto.getTermValue());
	        entity.setBannerDesktop(editDto.getBannerDesktop());
	        entity.setBannerMobile(editDto.getBannerMobile());
	        entity.setShowForm(editDto.isShowForm());
	        entity.setLending(editDto.getLending());
	        entity.setLinkAlias(editDto.getLinkAlias());
	        entity.setRate(editDto.getRate());
	        // if not check math expression then value is null
	        if (!editDto.getLending()) {
	            editDto.setMathExpression(null);
	            editDto.setHighlightsMathExpress(false);
	        }
	        entity.setHighlightsMathExpress(editDto.isHighlightsMathExpress());
	        entity.setMathExpression(editDto.getMathExpression());
	        
	        // physical file template
	        String physicalImgTmpName = editDto.getPhysicalImg();
	        // upload images
	        if (StringUtils.isNotEmpty(physicalImgTmpName)) {
	            String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName, AdminConstant.PRODUCT_FOLDER);
	            entity.setPhysicalImg(newPhiscalName);
	            entity.setImageUrl(editDto.getImageUrl());
	        } else {
	            entity.setPhysicalImg(null);
	            entity.setImageUrl(null);
	        }

	        // physical file template
	        String physicalIconName = editDto.getPhysicalIcon();
	        // upload icon
	        if (StringUtils.isNotEmpty(physicalIconName)) {
	            String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalIconName, AdminConstant.PRODUCT_FOLDER);
	            entity.setPhysicalIcon(newPhiscalName);
	            entity.setIconImg(editDto.getIconImg());
	        } else {
	            entity.setPhysicalIcon(null);
	            entity.setIconImg(null);
	        }
	        
	        entity.setCustomerTypeId(editDto.getTypeId());
	        entity.setProductCategoryId(editDto.getCategoryId());
	        entity.setName(editDto.getName());
	        entity.setDescription(editDto.getDescription());
	        entity.setNote(editDto.getNote());
	        entity.setOwnerId(accountId);
	        entity.setOwnerBranchId(branchId);
	        entity.setOwnerSectionId(departmentId);
	        entity.setViews(editDto.getViews());
	        entity.setEnabled(editDto.isEnabled());      
	        entity.setCategorySubId(editDto.getCategorySubId());
	        
	        entity.setHighlights(editDto.getIsHighlights());
	        entity.setPriority(editDto.getIsPriority());
	      
	        entity.setMicrosite(editDto.isMicrosite());

			// if action process
//			if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
//				if (editDto.getProcessId() == null) {
//					// First step
//					if (editDto.getTypeId().equals(9L)){
//						JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_PRODUCT_CD,
//								locale.toString());
//						editDto.setProcessId(processDto.getProcessId());
//					}else{
//						JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.DN_BUSINESS_PRODUCT,
//								locale.toString());
//	                    editDto.setProcessId(processDto.getProcessId());
//					}
//				}
//
//				JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(editDto.getProcessId(), editDto.getStatus(), editDto.getButtonId());
//				Integer status = jprocessService.getNextStepNo(currentActionStep, null);
//				
//	            editDto.setOldStatus(editDto.getStatus());
//	            // set status
//	            editDto.setStatus(status);
//	            editDto.setCurrItem(currentActionStep.getItems());
//			}

			entity.setProcessId(editDto.getProcessId());
			entity.setStatus(editDto.getStatus());
			
			//Set comment
			entity.setProductComment(editDto.getProductComment());
			
            if (entity.getDocId() == null) {
                entity.setDocId(editDto.getDocId());
            }
			
	        productRepository.save(entity);
	        editDto.setId(entity.getId());
	        editDto.setCode(entity.getCode());

	        // update before id for product after product edited
	        updateBeforeIdForProductAfter(editDto);
		}catch(Exception e) {
			 logger.error("createOrEditProduct: " + e.getMessage());
		     throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
        
    }

    /**
     * createOrEditLanguage
     *
     * @param editDto
     * @author hand
     */
    private void createOrEditLanguage(ProductEditDto editDto, String userName) {
        for (ProductLanguageDto cLanguageDto : editDto.getProductLanguageList()) {

            // m_product_language entity
            ProductLanguage entity = new ProductLanguage();

            if (null != cLanguageDto.getId()) {
                entity = productLanguageService.findByid(cLanguageDto.getId());
                if (null == entity) {
                    throw new BusinessException("Not found ProductLanguag with id=" + cLanguageDto.getId());
                }
                entity.setUpdateDate(new Date());
                entity.setUpdateBy(userName);
            } else {
                entity.setCreateDate(new Date());
                entity.setCreateBy(userName);
            }

            entity.setProductId(editDto.getId());
            entity.setLanguageCode(cLanguageDto.getLanguageCode());
            entity.setTitle(cLanguageDto.getTitle());
            entity.setShortContent(cLanguageDto.getShortContent());
            
            entity.setLinkAlias(cLanguageDto.getLinkAlias());
            entity.setKeyWord(cLanguageDto.getKeyWord());
            entity.setDescriptionKeyword(cLanguageDto.getDescriptionKeyword());
            entity.setWordsBanner(cLanguageDto.getWordsBanner());
            
            entity.setBannerDesktop(cLanguageDto.getBannerDesktop());
            entity.setBannerMobile(cLanguageDto.getBannerMobile());

            productLanguageService.saveProductLanguage(entity);
        }
    }

    /**
     * createOrEditLanguage
     *
     * @param editDto
     * @author hand
     */
    private void createOrEditProductDetail(ProductEditDto editDto, String userName, Long productId) {
        if (null != productId) {
            productDetailRepository.deleteByProductId(productId);
        }

        for (ProductDetaiLanguageDto cLanguageDto : editDto.getProductDetailLanguageList()) {

            for (ProductDetailDto productDetail : cLanguageDto.getProductDetailList()) {
                // m_product_detail entity
                ProductDetail entity = new ProductDetail();

                entity.setId(productDetail.getId());
                entity.setProductId(editDto.getId());
                entity.setLanguageCode(productDetail.getLanguageCode());
                entity.setGroupContent(productDetail.getGroupContent());
                entity.setContent(productDetail.getContent());
                entity.setKeyContent(productDetail.getKeyContent());
                entity.setBackgroundUrl(productDetail.getBackgroundUrl());
                entity.setBackgroundPhysical(productDetail.getBackgroundPhysical());
                entity.setMicrositeContent(productDetail.getMicrositeContent());
                entity.setMicrositeBannerDesktop(productDetail.getBannerDesktopMicrosite());
                entity.setMicrositeBannerPhysicalDesktop(productDetail.getBannerDesktopPhysicalMicrosite());
                entity.setMicrositeBannerMobile(productDetail.getBannerMobileMicrosite());
                entity.setMicrositeBannerPhysicalMobile(productDetail.getBannerMobilePhysicalMicrosite());
                entity.setTitle(productDetail.getTitleMicrosite());
                entity.setImageUrl(productDetail.getImageUrl());
                entity.setPhysicalImg(productDetail.getPhysicalImg());
                entity.setIconImg(productDetail.getIconImg());
                entity.setPhysicalIcon(productDetail.getPhysicalIcon());
                productDetailRepository.save(entity);
            }
        }

        CmsUtils.moveTempSubFolderToUpload(Paths.get(AdminConstant.PRODUCT_EDITOR_FOLDER, editDto.getRequestToken()).toString());
    }

    /**
     * get ProductEditDto
     *
     * @param id
     *            Long
     * @param action
     *            boolean: true is edit, false is detail
     * @param languageCode
     * @return ProductEditDto
     * @author hand
     */
    @Override
    public ProductEditDto getProduct(Long id, boolean action, Locale locale, String customerAlias) {
        ProductEditDto resultDto = new ProductEditDto();

        // languageList
        List<Language> languageList = languageService.findAllActive();

        if (id == null) {
            // resultDto.setProcessId(processService.getProcessIdByBusinessCode(MasterType.AP1.toString()));
            resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
            resultDto.setEnabled(Boolean.TRUE);
            resultDto.setShowForm(Boolean.TRUE);
            resultDto.setMicrosite(Boolean.FALSE);
            resultDto.setTypeId(HDBankUtil.getCustomerType(customerAlias));

            // set ProductDetailLanguage
            List<ProductDetaiLanguageDto> productDetaiLanguageList = inItProductDetaiLanguageList(languageList);
            resultDto.setProductDetailLanguageList(productDetaiLanguageList);

//            Long processId = resultDto.getProcessId();
//            if (processId == null) {
//            	// First step
//            	if (resultDto.getTypeId().equals(9L)){
//            		JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_PRODUCT_CD, locale.toString());
//            		processId = processDto.getProcessId();
//            	}else if (resultDto.getTypeId().equals(10L)){
//            		JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.DN_BUSINESS_PRODUCT, locale.toString());
//            		processId = processDto.getProcessId();
//            	}
//            }
//            
//            // List button of step
//            List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId, resultDto.getStatus(), locale.toString());
//            resultDto.setStepBtnList(stepButtonList);
//            
//            String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//            resultDto.setStatusName(statusName);

            return resultDto;
        }
        
        // set Product
        Product product = productRepository.findOne(id);
        
        // dữ liệu ko tồn tại hoặc đã bị xóa
        if (product == null || product.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
        }
        if (null != product) {
            resultDto.setId(product.getId());
            resultDto.setTypeId(product.getCustomerTypeId());
            resultDto.setCategoryId(product.getProductCategoryId());
            resultDto.setCode(product.getCode());
            resultDto.setName(product.getName());
            resultDto.setDescription(product.getDescription());
            resultDto.setNote(product.getNote());
            resultDto.setImageUrl(product.getImageUrl());
            resultDto.setPhysicalImg(product.getPhysicalImg());
            resultDto.setIconImg(product.getIconImg());
            resultDto.setPhysicalIcon(product.getPhysicalIcon());
            resultDto.setViews(product.getViews());
            resultDto.setEnabled(product.isEnabled());
            resultDto.setMicrosite(product.isMicrosite());
            resultDto.setHighlightsMathExpress(product.isHighlightsMathExpress());
            resultDto.setStatus(product.getStatus());
            resultDto.setSort(product.getSort());
            resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_PRODUCT);
            resultDto.setReferenceId(product.getId());
            resultDto.setCategorySubId(product.getCategorySubId());
            resultDto.setInterestRates(product.getInterestRates());
            resultDto.setMaxLoanAmount(product.getMaxLoanAmount());
            resultDto.setMaxLoanAmountStr(CmsUtils.convertBigDcimalToString(product.getMaxLoanAmount(), CmsUtils.PATTERN_MONEY_COMMA));
            resultDto.setTermCode(product.getTermCode());
            resultDto.setTermType(product.getTermType());
            resultDto.setTermValue(product.getTermValue());
            resultDto.setBannerDesktop(product.getBannerDesktop());
            resultDto.setBannerMobile(product.getBannerMobile());
            resultDto.setShowForm(product.isShowForm());
            resultDto.setLending(product.getLending());
            resultDto.setLinkAlias(product.getLinkAlias());
            resultDto.setCreateBy(product.getCreateBy());
            resultDto.setApprovedBy(product.getApprovedBy());
            resultDto.setApprovedDate(product.getApprovedDate());
            resultDto.setPublishedBy(product.getPublishedBy());
            resultDto.setPublishedDate(product.getPublishedDate());
            resultDto.setIsHighlights(product.getHighlights());
            resultDto.setIsPriority(product.getPriority());
            resultDto.setProductComment(product.getProductComment());
            resultDto.setBeforeId(product.getBeforeId());
            resultDto.setMathExpression(product.getMathExpression());
            resultDto.setRate(product.getRate());
            resultDto.setProcessId(product.getProcessId());
            resultDto.setUpdateDate(product.getUpdateDate());
            // action is false
            if (!action) {
                if (null != product.getProductCategoryId()) {
                    resultDto.setCategoryName(categoryLanguageService
                            .findByCategoryIdAndLanguage(product.getProductCategoryId(), locale.toString()).getTitle());
                }

                if (null != product.getCategorySubId()) {
                    resultDto.setCategorySubName(categorySubLanguageService
                            .findByCategoryIdAndLanguage(product.getCategorySubId(), locale.toString()).getTitle());
                }

                resultDto.setTypeName(typeLanguageService
                        .findByTypeIdAndLanguage(product.getCustomerTypeId(), locale.toString()).getTitle());

                if (!StringUtils.isEmpty(product.getTermCode())) {
                    // set termTypeName
                	/**
                	 * @author tuanh
                	 * @description check null termLanguage
                	 * @date 03/01/18
                	 */
                	TermLanguage termLanguage = termLanguageRepository.findByTermCode(product.getTermCode(), locale.toString());
                	if(null != termLanguage){
                        resultDto.setTermName(termLanguage.getTitle());
                	} 
                	/**End tuanh*/
                }
            }
            
//            Long processId = resultDto.getProcessId();
//            if (processId == null) {
//            	// First step
//            	if (product.getCustomerTypeId().equals(9L)){
//            		JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_PRODUCT_CD, locale.toString());
//            		processId = processDto.getProcessId();
//            	}else if (product.getCustomerTypeId().equals(10L)){
//            		JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.DN_BUSINESS_PRODUCT, locale.toString());
//            		processId = processDto.getProcessId();
//            	}
//            }
//            
//            //List button of step
//            List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId, resultDto.getStatus(), locale.toString());
//            resultDto.setStepBtnList(stepButtonList);
//            
//            String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//            resultDto.setStatusName(statusName);
//            
//            String statusCode = jprocessService.getStatusCode(resultDto.getProcessId(), resultDto.getStatus(), locale);
//    		resultDto.setStatusCode(statusCode);
        }

        // set ProductLanguage
        List<ProductLanguageDto> productLanguageList = getProductLanguageList(id, languageList);
        resultDto.setProductLanguageList(productLanguageList);

        // set ProductDetailLanguage
        List<ProductDetaiLanguageDto> productDetaiLanguageList = getProductDetaiLanguageList(id, languageList);
        resultDto.setProductDetailLanguageList(productDetaiLanguageList);

        if (action) {
            // set category select box
            resultDto.setCategoryJsonHidden(this.getCategorySelectJson(resultDto.getTypeId(), locale.toString()));

            // set category sub select box
            resultDto.setCategorySubJsonHidden(
                    this.getCategorySubSelectJson(resultDto.getTypeId(), resultDto.getCategoryId(), locale.toString(),CmsStepNoStatusConstant.STEP_APPROVED));

            // set list before select box
            resultDto.setBeforeIdsJsonHidden(this.getBeforeIdSelectJson(resultDto.getId(), resultDto.getTypeId(), resultDto.getCategoryId(),
                    resultDto.getCategorySubId(), locale.toString(), CmsStepNoStatusConstant.STEP_APPROVED));
        }
        
        return resultDto;
    }

    /**
     * getProductLanguageList
     *
     * @param productId
     * @return
     * @author hand
     */
    private List<ProductLanguageDto> getProductLanguageList(Long productId, List<Language> languageList) {
        List<ProductLanguageDto> resultList = new ArrayList<ProductLanguageDto>();
        // ProductLanguageList
        List<ProductLanguage> productLanguageList = productLanguageService.findByProductId(productId);

        // loop language
        for (Language language : languageList) {
            // loop productLanguageList
            for (ProductLanguage entity : productLanguageList) {
                // productLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    ProductLanguageDto productLanguageDto = new ProductLanguageDto();
                    productLanguageDto.setId(entity.getId());
                    productLanguageDto.setLanguageCode(entity.getLanguageCode());
                    productLanguageDto.setTitle(entity.getTitle());
                    productLanguageDto.setShortContent(entity.getShortContent());
                    productLanguageDto.setLinkAlias(entity.getLinkAlias());
                    productLanguageDto.setKeyWord(entity.getKeyWord());
                    productLanguageDto.setDescriptionKeyword(entity.getDescriptionKeyword());
                    productLanguageDto.setWordsBanner(entity.getWordsBanner());
                    productLanguageDto.setBannerDesktop(entity.getBannerDesktop());
                    productLanguageDto.setBannerMobile(entity.getBannerMobile());

                    resultList.add(productLanguageDto);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * inItProductDetaiLanguageList
     *
     * @param productId
     * @param languageList
     * @return
     * @author hand
     */
    private List<ProductDetaiLanguageDto> inItProductDetaiLanguageList(List<Language> languageList) {
        List<ProductDetaiLanguageDto> resultList = new ArrayList<ProductDetaiLanguageDto>();
        ProductDetaiLanguageDto detaiLanguageDto = null;

        List<ProductDetailDto> productDetailList = null;

        ProductDetailDto detailDto = null;

        // loop language
        for (Language language : languageList) {
            detailDto = new ProductDetailDto();
            detailDto.setLanguageCode(language.getCode());
            detailDto.setGroupContent(ConstantCore.STR_ZERO);
            productDetailList = new ArrayList<ProductDetailDto>();
            productDetailList.add(detailDto);

            detaiLanguageDto = new ProductDetaiLanguageDto();
            detaiLanguageDto.setLanguageCode(language.getCode());
            detaiLanguageDto.setProductDetailList(productDetailList);

            resultList.add(detaiLanguageDto);

        }

        return resultList;
    }

    /**
     * getProductDetaiLanguageList
     *
     * @param productId
     * @param languageList
     * @return
     * @author hand
     */
    private List<ProductDetaiLanguageDto> getProductDetaiLanguageList(Long productId, List<Language> languageList) {
        List<ProductDetail> productDetailList = productDetailRepository.findProductDetaiByProductId(productId);

        if (productDetailList.size() == 0) {
            return inItProductDetaiLanguageList(languageList);
        }

        List<ProductDetaiLanguageDto> resultList = new ArrayList<ProductDetaiLanguageDto>();

        ProductDetaiLanguageDto detaiLanguageDto = null;

        List<ProductDetailDto> detailLanguageList = null;

        ProductDetailDto detailDto = null;

        // loop language
        for (Language language : languageList) {

            detailLanguageList = new ArrayList<ProductDetailDto>();

            detaiLanguageDto = new ProductDetaiLanguageDto();
            detaiLanguageDto.setLanguageCode(language.getCode());

            for (ProductDetail productDetail : productDetailList) {
                if (StringUtils.equals(productDetail.getLanguageCode(), language.getCode())) {
                    detailDto = new ProductDetailDto();
                    detailDto.setId(productDetail.getId());
                    detailDto.setProductId(productDetail.getProductId());
                    detailDto.setLanguageCode(productDetail.getLanguageCode());
                    detailDto.setGroupContent(productDetail.getGroupContent());
                    detailDto.setContent(productDetail.getContent());
                    detailDto.setKeyContent(productDetail.getKeyContent());
                    detailDto.setBackgroundUrl(productDetail.getBackgroundUrl());
                    detailDto.setBackgroundPhysical(productDetail.getBackgroundPhysical());
                    detailDto.setMicrositeContent(productDetail.getMicrositeContent());
                    detailDto.setBannerDesktopMicrosite(productDetail.getMicrositeBannerDesktop());
                    detailDto.setBannerDesktopPhysicalMicrosite(productDetail.getMicrositeBannerPhysicalDesktop());
                    detailDto.setBannerMobileMicrosite(productDetail.getMicrositeBannerMobile());
                    detailDto.setBannerMobilePhysicalMicrosite(productDetail.getMicrositeBannerPhysicalMobile());
                    detailDto.setTitleMicrosite(productDetail.getTitle());
                    detailDto.setImageUrl(productDetail.getImageUrl());
                    detailDto.setPhysicalImg(productDetail.getPhysicalImg());
                    detailDto.setIconImg(productDetail.getIconImg());
                    detailDto.setPhysicalIcon(productDetail.getPhysicalIcon());
                    detailLanguageList.add(detailDto);
                }
            }

            detaiLanguageDto.setProductDetailList(detailLanguageList);

            resultList.add(detaiLanguageDto);
        }

        return resultList;
    }

    /**
     * get CategorySelect json string
     *
     * @param typeId
     * @param languageCode
     * @return String
     * @author hand
     */
    @Override
    public String getCategorySelectJson(Long typeId, String languageCode) {
        List<ProductCategoryDto> categoryList = productCategoryService.findByTypeIdAndLanguageCode(typeId,
                languageCode,CmsStepNoStatusConstant.STEP_APPROVED);

        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductCategoryDto category : categoryList) {
            categorySelect.add(new SelectItem(category.getId().toString(), category.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }

    /**
     * getCategorySubSelectJson
     *
     * @param typeId
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    @Override
    public String getCategorySubSelectJson(Long typeId, Long categoryId, String languageCode,Integer status) {
        List<ProductCategorySubDto> categorySubList = categorySubService.findByTypeIdAndCategoryId(typeId, categoryId,
                languageCode,status);

        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductCategorySubDto category : categorySubList) {
            categorySelect.add(new SelectItem(category.getId().toString(), category.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }
    
    @Override
    public String getListCategorySubSelectJsonByListCategory(Long typeId, List<Long> categoryListId, String languageCode,Integer status) {
        List<ProductCategorySubDto> categorySubList = categorySubService.findByTypeIdAndListCategoryId(typeId, categoryListId,
                languageCode,status);

        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductCategorySubDto category : categorySubList) {
            categorySelect.add(new SelectItem(category.getId().toString(), category.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }
    
    /**
     * getCategorySubSelectJson
     *
     * @param typeId
     * @param categoryId
     * @param categorySubId
     * @param languageCode
     * @author nhutnn
     */
    @Override
    public String getBeforeIdSelectJson(Long id, Long typeId, Long categoryId, Long categorySubId, String languageCode,Integer status) {
       
    	ProductSearchDto searchDto = new ProductSearchDto();
        searchDto.setId(id);
        searchDto.setTypeId(typeId);
        searchDto.setCategoryId(categoryId);
        searchDto.setCategorySubId(categorySubId);
        
        List<ProductEditDto> listProductBefore = productRepository.findListProductDtoForSort(languageCode, searchDto,status);

        List<SelectItem> beforeSelect = new ArrayList<SelectItem>();
        for (ProductEditDto product : listProductBefore) {
            beforeSelect.add(new SelectItem(product.getId().toString(),product.getName()));
        }
        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(beforeSelect);
        return categorySelectJson;
    }

    /**
     * find Product by code
     *
     * @param code
     * @return
     * @author hand
     */
    @Override
    public Product findByCode(String code) {
        return productRepository.findByCode(code);
    }

    /**
     * delete Product by entity
     *
     * @author hand
     */
    @Override
    @Transactional
    public void deleteById(Product product) {
        // userName
        String userName = UserProfileUtils.getUserNameLogin();
        // delete product
        deleteByProduct(product, userName);

    }

    /**
     * delete Product by category id
     *
     * @param typeId
     * @author hand
     */
    @Override
    @Transactional
    public void deleteProductByCategoryId(Long typeId) {
        // userName
        String userName = UserProfileUtils.getUserNameLogin();

        List<Product> productList = productRepository.findByCategoryId(typeId);

        for (Product product : productList) {
            deleteByProduct(product, userName);
        }
    }

    /**
     * delete Product by product
     *
     * @param product
     * @author hand
     */
    private void deleteByProduct(Product product, String userName) {
        
        // delete ProductProductLanguage
        productLanguageService.deleteByProductId(new Date(), userName, product.getId());

        // delete ProductProduct
        product.setDeleteDate(new Date());
        product.setDeleteBy(userName);
        productRepository.save(product);
    }

    /**
     * updateProcess
     *
     * @param id
     * @param processEnum
     * @author hand
     */
//    @Override
//    @Transactional
//    public void updateProcess(ProductEditDto productEdit, ProductProcessEnum processEnum) {
//        Product entity = productRepository.findOne(productEdit.getId());
//
//        Process process = processRepository.findOne(entity.getProcessId());
//        if (process != null) {
//            if (UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_DISP))) {
//                Hashtable<String, Object> params = new Hashtable<String, Object>();
//                if (ProductProcessEnum.APPROVAL.equals(processEnum)) {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_APPROVE);
//                    entity.setStatus(StepStatusEnum.APPROVED.getStepNo());
//                } else {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_REJECT);
//                    entity.setStatus(StepStatusEnum.REJECTED.getStepNo());
//                }
//
//                List<String> listCheck = new ArrayList<String>();
//                listCheck.add(CommonConstant.STATUS_SUBMITTED);
//
//                jBPMService.updateJBPMStatus(process.getDeploymentId(), entity.getProcessInstanceId(),
//                        CmsRoleConstant.ROLE_MANAGER, CmsRoleConstant.ROLE_MANAGER, params, CommonConstant.PARAM_STATUS,
//                        listCheck);
//            } else {
//                throw new SystemException("user not has role");
//            }
//        }
//
//        productRepository.save(entity);
//
//        // Update history approve
//        String userNameLogin = UserProfileUtils.getUserNameLogin();
//
//        HistoryApproveDto historyApproveDto = new HistoryApproveDto();
//        historyApproveDto.setComment(productEdit.getComment());
//        historyApproveDto.setProcessId(entity.getProcessId());
//        historyApproveDto.setReferenceId(entity.getId());
//        historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_PRODUCT);
//        historyApproveDto.setApprover(userNameLogin);
//
//        historyApproveService.addHistoryApprove(historyApproveDto, MasterType.AP1.toString());
//    }

    /**
     * find Product by id
     *
     * @param id
     * @return Product
     * @author hand
     */
    @Override
    public Product findById(Long id) {
        return productRepository.findOne(id);
    }

    /**
     * findMaxSortByTypeAndCategory
     *
     * @param typeId
     * @param categoryId
     * @param categorySubId
     * @return maxSort
     * @author hand
     */
    @Override
    public Long findMaxSortByTypeAndCategory(Long typeId, Long categoryId, Long categorySubId) {
        Long result = productRepository.findMaxSortByTypeAndCategory(typeId, categoryId, categorySubId);
        return null == result ? 1 : result + 1;
    }

    /**
     * @param fileUrl
     * @param request
     * @param response
     * @return
     */
    @Override
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
        boolean retVal = false;
        if (fileUrl != null) {
            if (CmsUtils.fileExistedInMain(fileUrl)) {
                fileService.download(fileUrl, request, response);
                retVal = true;
            } else if (CmsUtils.fileExistedInTemp(fileUrl)) {
                fileService.downloadTemp(fileUrl, request, response);
                retVal = true;
            }
        }
        return retVal;
    }

    /**
     * find Product by alias and customerId and categoryId
     * 
     * @param linkAlias
     * @param customerId
     * @param categoryId
     * @return Product
     */
    @Override
    public ProductLanguage findByAliasCustomerIdCategoryId(String linkAlias, String languageCode, Long customerId, Long categoryId, Long typeId) {
        return productRepository.findByAliasCustomerIdCategoryId(linkAlias, languageCode, customerId, categoryId, typeId);
    }
    
    /**
     * getCategoryByCustomerId
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author phunghn
     */
    @Override
    public List<ProductCategoryDto> getCategoryByCustomerId(Long typeId,
            String languageCode) {
        List<ProductCategoryDto> categoryList = productCategoryService.findByTypeIdAndLanguageCode(typeId,
                languageCode,CmsStepNoStatusConstant.STEP_APPROVED);        
        return categoryList;

    }

    /**
     * getCategorySubByCategoryAndCustomerId
     *
     * @param typeId
     * @param categoryId
     * @param languageCode
     * @return
     * @author phunghn
     */
    @Override
    public List<ProductCategorySubDto> getCategorySubByCategoryAndCustomerId(
            Long typeId, Long categoryId, String languageCode,Integer status) {
        List<ProductCategorySubDto> categorySubList = categorySubService.findByTypeIdAndCategoryId(typeId, categoryId,
                languageCode,status);
        return categorySubList;        
    }

    /**
     * findListProductForSorting
     *
     * @param typeId
     * @param categoryId
     * @param categorySubId
     * @param languageCode
     * @return
     * @author phunghn
     */
    @Override
    public List<ProductLanguageSearchDto> findListProductForSorting(Long typeId,
            Long categoryId, Long categorySubId, String languageCode,Integer status) {
        return productRepository.findListProductForSorting(typeId, categoryId, categorySubId, languageCode,status);
    }

    /**
     * saveProductOfSorting
     *
     * @param list
     * @return
     * @author phunghn
     */
    @Override
    @Transactional
    public Boolean saveProductOfSorting(List<SortOrderDto> list) {
        String userName = UserProfileUtils.getUserNameLogin();
        try {
            Long itemId = 0L;
            for (SortOrderDto item : list) {
                Product entity = new Product();
                entity = productRepository.findOne(item.getObjectId());
                if (entity != null) {
                    entity.setUpdateDate(new Date());
                    entity.setUpdateBy(userName);
                    entity.setSort(item.getSortValue());
                    entity.setBeforeId(itemId);
                    productRepository.save(entity);

                    itemId = item.getObjectId();
                }
            }
            return true;
        } catch (Exception e) {
        	logger.error("__saveProductOfSorting__", e);
            return false;
        }
    }
    
    /** getMaxCode
    *
    * @author nhutnn
    * @return max code
    */
    @Override
    public String getMaxCode() {
        return productRepository.getMaxCode();
    }
    
    /** findListProductDtoForSort
     *
     * @param searchDto
     * @param languageCode
     * @author nhutnn
     */
    @Override
    public List<ProductEditDto> findListProductDtoForSort(ProductSearchDto searchDto, String languageCode,Integer status) {
        return productRepository.findListProductDtoForSort(languageCode, searchDto,status);
    }
    
    /**
     * findAllProductForSort
     *
     * @param searchDto
     * @param languageCode
     * @author nhutnn
     */
    @Override
   public List<Product> findAllProductForSort(ProductSearchDto searchDto, String languageCode) {
       return productRepository.findAllProductForSort(languageCode, searchDto);
   }
    
    private void updateSortAndBeforeId(ProductEditDto editDto) {
        ProductSearchDto searchDto = new ProductSearchDto();
        searchDto.setTypeId(editDto.getTypeId());
        searchDto.setCategoryId(editDto.getCategoryId());
        searchDto.setCategorySubId(editDto.getCategorySubId());
        List<Product> lstProductType = productRepository.findAllProductForSort(editDto.getCodeLanguage(), searchDto);
        
        if (null != editDto.getBeforeId()) {
            // tim product truoc va gan sort cho productEdit
            int indexBefore = lstProductType.size();
            for (int i = 0, sz = indexBefore; i < sz; i++) {
                Product item = lstProductType.get(i);

                if (item.getId().equals(editDto.getBeforeId())) {
                    indexBefore = i;
                    editDto.setSort(item.getSort() + 1);
                    break;
                }
            }

            // gan sort cho cac product sau produtEdit
            Long currentSort = editDto.getSort() + 1;
            for (int i = indexBefore + 1, sz = lstProductType.size(); i < sz; i++) {
                Product item = lstProductType.get(i);
                item.setSort(currentSort++);
                productRepository.save(item);
            }
        } else {
            if (CollectionUtils.isNotEmpty(lstProductType)) {
                Product item = lstProductType.get(lstProductType.size() - 1);
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
        
    private void updateBeforeIdForProductAfter(ProductEditDto editDto) {
        ProductSearchDto searchDto = new ProductSearchDto();
        searchDto.setTypeId(editDto.getTypeId());
        searchDto.setCategoryId(editDto.getCategoryId());
        searchDto.setCategorySubId(editDto.getCategorySubId());
        List<Product> lstProductType = productRepository.findAllProductForSort(editDto.getCodeLanguage(), searchDto);
        if (CollectionUtils.isEmpty(lstProductType)) {
            return;
        }
            
        Product item = lstProductType.get(0);
        if (item.getId().equals(editDto.getBeforeId()) && !item.getBeforeId().equals(0l)) {
            item.setBeforeId(0l);
            productRepository.save(item);
        }
        
        for (int i = 0, sz = lstProductType.size(); i < sz - 1; i++) {
            item = lstProductType.get(i);
            if (item.getId().equals(editDto.getId())) {
                Product itemNext = lstProductType.get(i + 1);
                itemNext.setBeforeId(item.getId());

                productRepository.save(item);
                break;
            }
        }
    }

    @Override
    public String getProductSelectJsonByListSubId(Long customerId, Long categorySubListId, String languageCode,Integer status) {
        List<ProductLanguageSearchDto> products = productRepository.findListProductByListSubId(customerId, categorySubListId, languageCode,status);
        
        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductLanguageSearchDto product : products) {
            categorySelect.add(new SelectItem(product.getId().toString(), product.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }
    
    @Override
    public String getProductSelectJson(Long customerId, Long categoryId, Long categorySubId, String languageCode) {
        
    	List<ProductLanguageSearchDto> products = productRepository.findListProductForSorting(customerId, categoryId, categorySubId, languageCode,CmsStepNoStatusConstant.STEP_APPROVED);
        
        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductLanguageSearchDto product : products) {
            categorySelect.add(new SelectItem(product.getId().toString(), product.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }
    
    @Override
    public List<ProductLanguageSearchDto> findListProductByListSubId(Long customerId, Long categorySubListId, String languageCode,Integer status) {
        return productRepository.findListProductByListSubId(customerId, categorySubListId, languageCode,status);
    }
    
    @Override
    public ProductEditDto getProductDtoMicrosite(String languageCode) {
        return productRepository.getProductDtoMicrosite(languageCode);
    }

    @Override
    public String getProductSelectJsonByList(Long customerId, List<Long> categoryId, List<Long> categorySubId, String languageCode) {
        List<ProductLanguageSearchDto> products = productRepository.findListProductForSortingByList(customerId, categoryId, categorySubId, languageCode);
        
        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductLanguageSearchDto product : products) {
            categorySelect.add(new SelectItem(product.getId().toString(), product.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }

    @Override
    public String getListCategorySubSelectJsonByListCategoryForEdit(Long typeId, List<Long> categoryListId, String languageCode,Integer status) {
        List<ProductCategorySubDto> categorySubList = categorySubService.getByTypeIdAndListCategoryIdForEdit(typeId, categoryListId, languageCode,status);

        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductCategorySubDto category : categorySubList) {
            categorySelect.add(new SelectItem(category.getId().toString(), category.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }

    @Override
    public String getProductSelectJsonByListSubIdForEdit(Long customerId, List<Long> categoryListId, List<Long> categorySubListId, String languageCode,Integer status) {
        List<ProductLanguageSearchDto> products = productRepository.findListProductByListSubIdForEdit(customerId, categoryListId, categorySubListId, languageCode,status);
        
        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (ProductLanguageSearchDto product : products) {
            categorySelect.add(new SelectItem(product.getId().toString(), product.getTitle()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }
    
    private void sendMail(ProductEditDto editDto, HttpServletRequest request){
        try {
            // locale default
            String defaultlocale = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
            Locale locale = new Locale(defaultlocale);

            EmailCommonDto emailCommon = new EmailCommonDto();
//            emailCommon.setActionName(msg.getMessage("email.template.product", null, locale));
            emailCommon.setButtonAction(editDto.getButtonAction());
            if (editDto.getButtonId() != null) {
                emailCommon.setButtonId(editDto.getButtonId().toString());
            }
            emailCommon.setComment(editDto.getProductComment());

            // Nội dung
            LinkedHashMap<String, String> content = new LinkedHashMap<>();

            // tìm tên loại sản phẩm
            String businessCode = CmsCommonConstant.BUSINESS_PRODUCT_CD;
            if (editDto.getCustomerAlias().equals("corporate")) {
                businessCode = CmsCommonConstant.DN_BUSINESS_PRODUCT;
            }
            ProductCategoryEditDto productCategory = productCategoryService.getProductCategory(editDto.getCategoryId(),
                    locale, businessCode);
            ProductCategorySubEditDto productCategorySub = categorySubService.getProductCategorySub(businessCode,
                    editDto.getCustomerAlias(), editDto.getCategorySubId(), locale);

            content.put("Mã", editDto.getCode());
            content.put("Loại sản phẩm", productCategory.getCategoryLanguageList().get(0).getTitle());
            content.put("Danh mục sản phẩm", productCategorySub.getCategoryLanguageList().get(0).getTitle());
            content.put("Danh sách sản phẩm", editDto.getProductLanguageList().get(0).getTitle());
            emailCommon.setContent(content);

            emailCommon.setCurrItem(editDto.getCurrItem());

            emailCommon.setId(editDto.getId());
            emailCommon.setOldStatus(editDto.getOldStatus());
            emailCommon.setProcessId(editDto.getProcessId());
            emailCommon.setReferenceType(editDto.getReferenceType());
            emailCommon.setStatus(editDto.getStatus());

            // Subject của email
//            emailCommon.setSubject(msg.getMessage("subject.email.template.product", null, locale));

            if (request != null) {
                emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias() + "/product/edit?id="
                        + editDto.getId());
            }

//            emailUtil.sendMail(emailCommon, request, locale);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
	@Override
	public void exportExcel(ProductSearchDto searchDto, HttpServletResponse res, Locale locale) {
		try {
			// set status name
			searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

			/* change template */
			String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_PRODUCT;
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CmsCommonConstant.TYPE_EXCEL;
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

			List<ExportProductReportDto> lstData = productRepository.exportExcelWithCondition(searchDto);
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(ExportProductExportEnum.class, cols);
			ExportExcelUtil<ExportProductReportDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportProductReportDto.class,
					cols, datePattern, res, templateName);

		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}

	@Override
	public Long countByProductCategorySubId(Long productCategorySubId) {
		return productRepository.countByProductCategorySubId(productCategorySubId);
	}

	@Override
	public Long countByBannerDesktop(Long bannerId) {
		return productRepository.countByBannerDesktop(bannerId);
	}

	@Override
	public Long countByBannerMobile(Long bannerId) {
		return productRepository.countByBannerMobile(bannerId);
	}

	@Override
	public List<Select2Dto> findListProductForSort2(String languageCode, Long customerId, Long productId, Long categoryId,
			Long categorySubId) {
		return productRepository.findListProductForSort2(languageCode, customerId, productId, categoryId, categorySubId);
	}

	@Override
	public int countDependencies(Long productId, List<Long> lstStatus) {
		return productRepository.countDependencies(productId, lstStatus);
	}
	
	@Override
	public List<Map <String, String>> listDependencies(Long productId, List<Long> lstStatus) {
		return productRepository.listDependencies(productId, lstStatus);
	}

    @Override
    public ProductEditDto getEdit(Long id, String customerAlias, Locale locale) {
        return getProduct(id, false, locale, customerAlias);
    }
    
    @Override
    public DocumentActionReq actionBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto, Locale locale)
            throws Exception {
        ProductEditDto editDto = (ProductEditDto) documentActionReq;
        
        doEdit(editDto, locale, null);
        
        return editDto;
    }
}
