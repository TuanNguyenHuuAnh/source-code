/*******************************************************************************
 * Class        ：FaqsServiceImpl
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.enumdef.FaqsSearchExportEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.FaqsCategoryService;
import vn.com.unit.cms.admin.all.service.FaqsLanguageService;
import vn.com.unit.cms.admin.all.service.FaqsService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.faqs.dto.FaqsEditDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsLanguageDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchResultDto;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;
import vn.com.unit.cms.core.module.faqs.repository.FaqsRepository;
import vn.com.unit.cms.core.utils.CmsDateUtils;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.repository.EfoDocRepository;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.service.JpmStatusDeployService;

/**
 * FaqsServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FaqsServiceImpl extends DocumentWorkflowCommonServiceImpl<FaqsEditDto, FaqsEditDto>
        implements FaqsService {

    /* systemConfig */
    @Autowired
    private SystemConfig systemConfig;

    /* faqsRep */
    @Autowired
    private FaqsRepository faqsRepository;

    @Autowired
    private FaqsLanguageService faqsLanguageService;

    @Autowired
    private FaqsCategoryService categoryFaqsService;

    @Autowired
    private MessageSource msg;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private CmsCommonService cmsCommonService;

    @Autowired
    private EfoDocRepository efoDocRepository;

    @Autowired
    private CommonSearchFilterUtils commonSearchFilterUtils;

    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;
    
    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;

    @Autowired
    private Select2DataService select2DataService;

    private static final Logger logger = LoggerFactory.getLogger(FaqsServiceImpl.class);
    
    private Date checkMaxDate(Date date) {
        if(!ObjectUtils.isNotEmpty(date)) {
                try {
                    Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/9999");
                    return maxDate;
                } catch (ParseException e) {
                    logger.error("Exception ", e);
                }
            }
        return date;
    }
    
    @Override
    public Faqs findFaqsByCode(String faqsCode) {
        return faqsRepository.findFaqsByCode(faqsCode);
    }

    /**
     * getFaqsDto
     *
     * @param faqsId
     * @param languageCode
     * @param action       boolean: true is edit, false is detail
     * @return FaqsEditDto
     * @author TaiTM
     */
    public FaqsEditDto getEditDto(Long id, Locale locale, boolean action, String customerAlias) {
        FaqsEditDto resultDto = new FaqsEditDto();
        if (id == null) {
            resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
            resultDto.setEnabled(true);
            Long customerId = 9L;
            resultDto.setCustomerId(customerId);
            return resultDto;
        }

        // set FaqsCategory
        Faqs faqs = faqsRepository.findOne(id);

        // dữ liệu ko tồn tại hoặc đã bị xóa
        if (faqs == null || faqs.getDeleteDate() != null) {
            throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
        }

        if (null != faqs) {
            resultDto.setId(faqs.getId());
            resultDto.setCode(faqs.getCode());
            resultDto.setCateFaqsId(faqs.getCategoryId());
            resultDto.setNote(faqs.getNote());
            resultDto.setEnabled(faqs.isEnabled());
            resultDto.setSort(faqs.getSort());
            resultDto.setCustomerId(faqs.getCustomerId());
            resultDto.setCreateBy(faqs.getCreateBy());
            resultDto.setUpdateDate(faqs.getUpdateDate());
            resultDto.setNote(faqs.getNote());
            resultDto.setDocId(faqs.getDocId());

            resultDto.setPostedDate(faqs.getPostedDate());
            resultDto.setExpirationDate(faqs.getExpirationDate());
            
            JpmStatusDeployDto status = jpmStatusDeployService.getStatusDeploy(resultDto.getDocId(), locale.toString());
            if (status != null) {
                resultDto.setStatusName(status.getStatusName());
            }
        }

        List<FaqsLanguageDto> faqsLanguageList = getFaqsLanguageList(id);
        resultDto.setFaqsLanguageList(faqsLanguageList);

        return resultDto;
    }

    /**
     * get FaqsLanguageDto List
     *
     * @param faqsId
     * @return FaqsLanguageDto list
     * @author TaiTM
     */
    private List<FaqsLanguageDto> getFaqsLanguageList(Long faqsId) {
        List<FaqsLanguageDto> resultList = new ArrayList<FaqsLanguageDto>();

        List<FaqsLanguage> faqsLanguageList = faqsLanguageService.findByFaqsId(faqsId);

        for (FaqsLanguage entity : faqsLanguageList) {
            FaqsLanguageDto faqsLanguageDto = new FaqsLanguageDto();
            faqsLanguageDto.setId(entity.getId());
            faqsLanguageDto.setTitle(entity.getTitle());
            faqsLanguageDto.setLanguageCode(entity.getLanguageCode());
            faqsLanguageDto.setFaqsId(entity.getFaqsId());
            faqsLanguageDto.setShortContent(entity.getShortContent());
            faqsLanguageDto.setContent(entity.getContent());
            faqsLanguageDto.setKeyword(entity.getKeyword());
            faqsLanguageDto.setKeywordDescription(entity.getKeywordDescription());
            faqsLanguageDto.setLinkAlias(entity.getLinkAlias());
            resultList.add(faqsLanguageDto);
            break;
        }
        return resultList;
    }

    /**
     * addOrEdit Faqs
     *
     * @param editDto
     * @param action  true is saveDraft, false is sendRequest
     * @author TaiTM
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean addOrEdit(FaqsEditDto editDto, boolean action, Locale locale, HttpServletRequest request)
            throws Exception {

        boolean result = true;

        // user login
//        UserProfile userProfile = UserProfileUtils.getUserProfile();

        createOrEditFaqs(editDto, UserProfileUtils.getUserNameLogin(), action, locale, request);

        createOrEditLanguage(editDto, UserProfileUtils.getUserNameLogin(), locale);

        CmsUtils.moveTempSubFolderToUpload(Paths
                .get(AdminConstant.FAQS_FOLDER, AdminConstant.EDITOR_FOLDER, editDto.getRequestToken()).toString());

        return result;
    }

    /**
     * create or update Faqs
     *
     * @param editDto
     * @param action  true is saveDraft, false is sendRequest
     * @author TaiTM
     * @param request
     * @throws Exception
     */
    private void createOrEditFaqs(FaqsEditDto editDto, String userName, boolean action, Locale locale,
            HttpServletRequest request) throws Exception {
        Faqs entity = new Faqs();

        if (null != editDto.getId()) {
            entity = faqsRepository.findOne(editDto.getId());
            if (null == entity || entity.getDeleteDate() != null) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userName);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(userName);
        }

        try {
            if (StringUtils.isBlank(entity.getCode())) {
                entity.setCode(CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_FAQS,
                        cmsCommonService.getMaxCodeYYMM("M_FAQS", CmsPrefixCodeConstant.PREFIX_CODE_FAQS)));
            }

            entity.setDocId(editDto.getDocId());

            entity.setCategoryId(editDto.getCateFaqsId());
            entity.setEnabled(editDto.isEnabled());
            entity.setCustomerId(editDto.getCustomerId());
            entity.setSort(editDto.getSort());
            entity.setPostedDate(editDto.getPostedDate());
            entity.setExpirationDate(checkMaxDate(editDto.getExpirationDate()));
            entity.setNote(editDto.getNote());

            faqsRepository.save(entity);
            editDto.setId(entity.getId());
            editDto.setCode(entity.getCode());
        } catch (Exception e) {
            logger.error("createOrEditFaqs: " + e.getMessage());
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
    }

//    private void sendMail(FaqsEditDto editDto, HttpServletRequest request, Locale locale) {
//        try {
//            EmailCommonDto emailCommon = new EmailCommonDto();
//            emailCommon.setActionName(msg.getMessage("email.template.faqs", null, locale));
//            emailCommon.setButtonAction(editDto.getButtonAction());
//            emailCommon.setButtonId(editDto.getButtonId().toString());
//            emailCommon.setComment(editDto.getNote());
//
//            // Nội dung
//            LinkedHashMap<String, String> content = new LinkedHashMap<>();
//            content.put("Mã", editDto.getCode());
//            content.put("Danh sách câu hỏi", editDto.getFaqsLanguageList().get(0).getTitle());
//            emailCommon.setContent(content);
//
//            emailCommon.setId(editDto.getId());
//            emailCommon.setProcessId(editDto.getProcessId());
//            emailCommon.setReferenceType(editDto.getReferenceType());
//
//            // Subject của email
//            emailCommon.setSubject(msg.getMessage("subject.email.template.faqs", null, locale));
//
//            emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias() + "/faqs/edit?id="
//                    + editDto.getId());
//
////            emailUtil.sendMail(emailCommon, request, locale);
//        } catch (Exception e) {
//            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
//        }
//    }

    /**
     * createOrEditLanguage
     *
     * @param editDto
     * @author TaiTM
     */
    private void createOrEditLanguage(FaqsEditDto editDto, String userName, Locale locale) {
        try {
            for (FaqsLanguageDto faqsDto : editDto.getFaqsLanguageList()) {
                FaqsLanguage entity = new FaqsLanguage();
                FaqsLanguage entityEn = new FaqsLanguage();
                if (null != faqsDto.getId()) {
                    entity = faqsLanguageService.findByIdAndLang(editDto.getId(),"VI");
                    entityEn = faqsLanguageService.findByIdAndLang(editDto.getId(),"EN");
                    if (null == entity || null == entityEn) {
                        throw new BusinessException(
                                msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale)
                                        + editDto.getId());
                    }
                    entity.setUpdateDate(new Date());
                    entity.setUpdateBy(userName);
                    entityEn.setUpdateDate(new Date());
                    entityEn.setUpdateBy(userName);
                } else {
                    entity.setCreateDate(new Date());
                    entity.setCreateBy(userName);
                    entityEn.setCreateDate(new Date());
                    entityEn.setCreateBy(userName);
                }
                entity.setFaqsId(editDto.getId());
                entity.setLanguageCode("VI");
                entity.setTitle(faqsDto.getTitle());
                entity.setShortContent(faqsDto.getShortContent());
                entity.setContent(faqsDto.getContent());
                entity.setLinkAlias(faqsDto.getLinkAlias());
                entity.setKeyword(faqsDto.getKeyword());
                entity.setKeywordDescription(faqsDto.getKeywordDescription());
                faqsLanguageService.saveFaqsLanguage(entity);
                
                entityEn.setFaqsId(editDto.getId());
                entityEn.setLanguageCode("EN");
                entityEn.setTitle(faqsDto.getTitle());
                entityEn.setShortContent(faqsDto.getShortContent());
                entityEn.setContent(faqsDto.getContent());
                entityEn.setLinkAlias(faqsDto.getLinkAlias());
                entityEn.setKeyword(faqsDto.getKeyword());
                entityEn.setKeywordDescription(faqsDto.getKeywordDescription());
                faqsLanguageService.saveFaqsLanguage(entityEn);
            }

//            CmsUtils.moveTempSubFolderToUpload(Paths
//                    .get(AdminConstant.FAQS_FOLDER, AdminConstant.EDITOR_FOLDER, editDto.getRequestToken()).toString());
        } catch (Exception e) {
            logger.error("createOrEditLanguage: " + e);
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
    }

    /**
     * delete Faqs
     *
     * @param faqsId
     * @author TaiTM
     */
    @Override
    @Transactional
    public void deleteFaqs(Faqs faqs) {
        // userName
        String deleteBy = UserProfileUtils.getUserNameLogin();
        // delete FaqsLanguage
        faqsLanguageService.deleteByFawsId(faqs.getId(), deleteBy);

        // delete Faqs
        faqs.setDeleteDate(new Date());
        faqs.setDeleteBy(deleteBy);
        faqsRepository.save(faqs);
    }

    /**
     * delete Faqs by category id
     *
     * @param typeId
     * @author TaiTM
     */
    @Override
    @Transactional
    public void deleteFaqsByCategoryId(Long typeId) {
        // userName
        String userName = UserProfileUtils.getUserNameLogin();

        List<Faqs> faqsList = faqsRepository.findByCategoryId(typeId);

        for (Faqs faqs : faqsList) {
            deleteByFaqs(faqs, userName);
        }
    }

    /**
     * delete Faqs by faqs
     *
     * @param faqs
     * @author TaiTM
     */
    private void deleteByFaqs(Faqs faqs, String userName) {
        // delete FaqsFaqsLanguage
        faqsLanguageService.deleteByFawsId(faqs.getId(), userName);

        // delete FaqsFaqs
        faqs.setDeleteDate(new Date());
        faqs.setDeleteBy(userName);
        faqsRepository.save(faqs);
    }

    /**
     * findById
     *
     * @param id
     * @return
     * @author TaiTM
     */
    @Override
    public Faqs findById(Long id) {
        return faqsRepository.findOne(id);
    }

    @Override
    public Boolean updateSortAll(List<SortOrderDto> sortOderList) {
        String userName = UserProfileUtils.getUserNameLogin();
        try {
            for (SortOrderDto dto : sortOderList) {
                faqsRepository.updateSortAll(dto);
            }

            for (SortOrderDto dto : sortOderList) {
                Faqs item = faqsRepository.findOne(dto.getObjectId());
                item.setUpdateDate(new Date());
                item.setUpdateBy(userName);
                faqsRepository.save(item);
            }
            return true;
        } catch (Exception e) {
            logger.error("__updateSortAll__", e);
            return false;
        }
    }

    @Override
    public List<FaqsSearchResultDto> findAllFaqsForSort(String lang, FaqsSearchDto searchDto) {
        List<FaqsSearchResultDto> listfags = new ArrayList<>();
        listfags = faqsRepository.findAllFaqsForSort(searchDto);
        return listfags;
    }

    @Override
    public FaqsLanguage findByAliasCategoryIdAndTypeId(String linkAlias, String languageCode, Long customerId,
            Long categoryId, Long typeId, List<Long> categoryProductListId, List<Long> categorySubProductListId,
            List<Long> productListId) {
        return faqsRepository.findByAliasCategoryIdAndTypeId(linkAlias, languageCode, customerId, categoryId, typeId,
                categoryProductListId, categorySubProductListId, productListId);
    }

    @Override
    public String getCateFaq(Long id, String lang) {
        return faqsRepository.findCateFaq(id,lang);
    }

    @Override
    public Faqs getlsDocument(Long id) {
        return faqsRepository.findOne(id);
    }

    @Override
    public FaqsEditDto getEdit(Long id, String customerAlias, Locale locale) {
        if (CommonStringUtil.isBlank(customerAlias)) {
            customerAlias = "personal";
        }

        return getEditDto(id, locale, false, customerAlias);
    }

    @Override
    public DocumentActionReq actionBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto, Locale locale)
            throws Exception {

        FaqsEditDto editDto = (FaqsEditDto) documentActionReq;
        String usernameLogin = UserProfileUtils.getUserNameLogin();
        try {
            if (StringUtils.isNotBlank(editDto.getPostedDateString())) {
                editDto.setPostedDate(
                        CmsDateUtils.convertStringToDate(editDto.getPostedDateString(), "dd/MM/yyyy HH:mm:ss"));
            }

            if (StringUtils.isNotBlank(editDto.getExpirationDateString())) {
                editDto.setExpirationDate(
                        CmsDateUtils.convertStringToDate(editDto.getExpirationDateString(), "dd/MM/yyyy HH:mm:ss"));
            }
            
            if (editDto.getHasUpdateData() != null && editDto.getHasUpdateData() == true) {
                Faqs entity = faqsRepository.findOne(editDto.getId());
                entity.setEnabled(editDto.isEnabled());
                entity.setPostedDate(editDto.getPostedDate());
                entity.setExpirationDate(checkMaxDate(editDto.getExpirationDate()));
                
                entity.setUpdateBy(usernameLogin);
                entity.setUpdateDate(new Date());
                
                faqsRepository.save(entity);
            } else {
                createOrEditFaqs(editDto, usernameLogin, false, locale, null);

                createOrEditLanguage(editDto, usernameLogin, locale);

                // Cập nhật thêm ITEM_FUNCTION_CODE cho process
                if (editDto.getCateFaqsId() != null) {
                    FaqsCategory category = categoryFaqsService.findById(editDto.getCateFaqsId());
                    if (documentActionReq.getDocId() != null) {
                        efoDocRepository.updateItemFunctionForDoc(documentActionReq.getDocId(),
                                category.getItemFunctionCode());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Need fix createOrEditLanguage", e);
            throw new Exception(e.getMessage());
        }

        editDto.setDataId(editDto.getId());
        return editDto;
    }

    @Override
    public List<CommonSearchFilterDto> initListSearchFilter(FaqsSearchDto searchDto, Locale locale) {
        List<CommonSearchFilterDto> list = new ArrayList<>();

        List<Select2Dto> datas = select2DataService.getListFaqsCategory(locale.toString());
        CommonSearchFilterDto categoryName = commonSearchFilterUtils.createSelectCommonSearchFilterDto("categoryName",
                msg.getMessage("searchfield.disp.category", null, locale),
                searchDto.getCategoryName() != null ? searchDto.getCategoryName().toString() : null, datas);
        list.add(categoryName);

        CommonSearchFilterDto code = commonSearchFilterUtils.createInputCommonSearchFilterDto("code",
                msg.getMessage("faqs.code", null, locale), searchDto.getCode());
        new CommonSearchFilterDto();
        list.add(code);

        CommonSearchFilterDto content = commonSearchFilterUtils.createInputCommonSearchFilterDto("title",
                msg.getMessage("faqs.content", null, locale), searchDto.getContent());
        new CommonSearchFilterDto();
        list.add(content);

        CommonSearchFilterDto status = commonSearchFilterUtils.createSelectStatusProcess("statusName",
                searchDto.getStatusCode(), "BUSINESS_CMS", locale);
        list.add(status);

        boolean check = false;
        if ("1".equals(searchDto.getEnabled())) {
            check = true;
        }
        CommonSearchFilterDto enabled = commonSearchFilterUtils.createCheckboxCommonSearchFilterDto("enabled",
                msg.getMessage("searchfield.disp.enabled", null, locale), check);
        list.add(enabled);

        CommonSearchFilterDto postedDate = commonSearchFilterUtils.createDateCommonSearchFilterDto("postedDate",
                msg.getMessage("news.posted.date", null, locale), searchDto.getPostedDate());
        list.add(postedDate);

        CommonSearchFilterDto expirationDate = commonSearchFilterUtils.createDateCommonSearchFilterDto("expirationDate",
                msg.getMessage("news.expiration.date", null, locale), searchDto.getExpirationDate());
        list.add(expirationDate);


        return list;
    }


    @Override
    public List<FaqsSearchResultDto> getListByCondition(FaqsSearchDto searchDto, Pageable pageable) {
        if (!UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT))) {
            searchDto.setUsername(UserProfileUtils.getUserNameLogin());
        }
        return faqsRepository.findListData(searchDto, pageable).getContent();
    }

    @Override
    public int countListByCondition(FaqsSearchDto searchDto) {
        if (!UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT))) {
            searchDto.setUsername(UserProfileUtils.getUserNameLogin());
        }
        return faqsRepository.countFaqs(searchDto);
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public CmsCommonService getCmsCommonService() {
        return cmsCommonService;
    }

    @Override
    public JcaDatatableConfigService getJcaDatatableConfigService() {
        return jcaDatatableConfigService;
    }

    @Override
    public FaqsEditDto getEditDtoById(Long id, Locale locale) {
        return getEditDto(id, locale, false, "personal");
    }

    @Override
    public void saveOrUpdate(FaqsEditDto editDto, Locale locale) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteDataById(Long id) throws Exception {
        Faqs faqs = faqsRepository.findOne(id);
        deleteFaqs(faqs);
    }

    @Override
    public List<FaqsSearchResultDto> getListForSort(FaqsSearchDto searchDto) {
        return faqsRepository.findAllFaqsForSort(searchDto);
    }

    @Override
    public void updateSortAll(FaqsSearchDto searchDto) {
        for (SortOrderDto dto : searchDto.getSortOderList()) {
            faqsRepository.updateSortAll(dto);
        }
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getEnumColumnForExport() {
        return FaqsSearchExportEnum.class;
    }

    @Override
    public String getTemplateNameForExport(Locale locale) {
        return CmsCommonConstant.TEMPLATE_FAQS;
    }

    @Override
    public CommonSearchFilterUtils getCommonSearchFilterUtils() {
        return commonSearchFilterUtils;
    }
}
