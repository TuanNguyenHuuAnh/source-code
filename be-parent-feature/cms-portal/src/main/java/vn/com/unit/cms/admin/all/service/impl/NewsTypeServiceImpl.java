/*******************************************************************************
 * Class        ：NewsTypeServiceImpl
 * Created date ：2017/03/01
 * Lasted date  ：2017/03/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.ExportNewsTypeReportDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeSearchDto;
//import vn.com.unit.cms.admin.all.entity.News;
import vn.com.unit.cms.admin.all.entity.NewsCategory;
import vn.com.unit.cms.admin.all.entity.NewsType;
import vn.com.unit.cms.admin.all.entity.NewsTypeLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportNewsTypeExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
//import vn.com.unit.cms.admin.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.admin.all.repository.NewsCategoryRepository;
//import vn.com.unit.cms.admin.all.repository.NewsRepository;
import vn.com.unit.cms.admin.all.repository.NewsTypeRepository;
import vn.com.unit.cms.admin.all.service.NewsService;
import vn.com.unit.cms.admin.all.service.NewsTypeLanguageService;
import vn.com.unit.cms.admin.all.service.NewsTypeService;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.cms.core.module.news.repository.NewsRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.entity.Language;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.jcanary.service.AccountService;
//import vn.com.unit.jcanary.service.EmailService;
//import vn.com.unit.jcanary.service.HistoryApproveService;
//import vn.com.unit.jcanary.service.JProcessService;
//import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.dts.exception.DetailException;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
//import vn.com.unit.jcanary.utils.Utils;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.utils.Utility;
//import vn.com.unit.ep2p.dto.JProcessStepDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * NewsTypeServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NewsTypeServiceImpl implements NewsTypeService {

    @Autowired
    private NewsTypeRepository newsTypeRepository;

    @Autowired
    private NewsTypeLanguageService newsTypeLanguageService;

    @Autowired
    private NewsCategoryRepository newsCategoryRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsService newsService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private LanguageService languageService;

//    @Autowired
//    JProcessService jprocessService;
//    
//    @Autowired
//    EmailService emailService;
//
//    @Autowired
//    AccountService accountService;
//    
//    @Autowired
//    HistoryApproveService historyApproveService;

    @Autowired
    MessageSource msg;

    @Autowired
    ServletContext servletContext;

//    @Autowired
//	EmailUtil emailUtil;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(HomepageServiceImpl.class);

//    private static final String  INVESTOR= "investor";
    /**
     * get all NewsType by language code
     * 
     * @param languageCode
     * @return List<NewsTypeDto>
     * @author hand
     */
    @Override
    public List<NewsTypeDto> findByLanguageCode(String languageCode) {
        // NewsTypeDto List
        List<NewsTypeDto> resultList = newsTypeRepository.findByLanguageCode(languageCode);
        return resultList;
    }

    /**
     * searchTypeLanguage
     *
     * @param page
     * @param searchDto NewsTypeSearchDto
     * @return PageWrapper
     * @author hand
     */
    @Override
    public PageWrapper<NewsTypeLanguageSearchDto> searchTypeLanguage(int page, NewsTypeSearchDto searchDto,
            Locale locale) {

        // set status name
        searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

        int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
                : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<NewsTypeLanguageSearchDto> pageWrapper = new PageWrapper<NewsTypeLanguageSearchDto>(page,
                sizeOfPage);

        int count = newsTypeRepository.countByNewsTypeSearchDto(searchDto);
        List<NewsTypeLanguageSearchDto> result = new ArrayList<NewsTypeLanguageSearchDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            result = newsTypeRepository.findByNewsTypeSearchDto(offsetSQL, sizeOfPage, searchDto);
        }

        pageWrapper.setDataAndCount(result, count);

        return pageWrapper;
    }

    /**
     * getNewsTypeEditDto
     *
     * @param id
     * @param languageCode
     * @param action:      true: edit, false: detail
     * @return NewsTypeEditDto
     * @author hand
     */
    @Override
    public NewsTypeEditDto getNewsTypeEditDto(Long id, Locale locale, String businessCode) {
        NewsTypeEditDto resultDto = new NewsTypeEditDto();

        if (id == null) {
            resultDto.setEnabled(Boolean.TRUE);
            resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
            resultDto.setCreatedBy(UserProfileUtils.getUserNameLogin());
        } else {
            NewsType entity = newsTypeRepository.findOne(id);
            // dữ liệu ko tồn tại hoặc đã bị xóa
            if (entity == null || entity.getDeleteDate() != null) {
                throw new BusinessException(
                        msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale) + id);
            }

            if (null != entity) {
                resultDto.setId(entity.getId());
                resultDto.setCode(entity.getCode());
                resultDto.setName(entity.getName());
                resultDto.setDescription(entity.getDescription());
                resultDto.setSort(entity.getSort());
                resultDto.setEnabled(entity.isEnabled());
                resultDto.setCustomerTypeId(entity.getCustomerTypeId());
                resultDto.setLinkAlias(entity.getLinkAlias());
                resultDto.setCreatedBy(entity.getCreateBy());
                List<NewsCategory> lstCategoryActive = newsCategoryRepository.findAllActiveByTypeId(id);
                List<News> lstNewsActive = newsRepository.findAllActiveByTypeId(id);
                resultDto.setHasDisableCheckEnabled(
                        CollectionUtils.isNotEmpty(lstCategoryActive) || CollectionUtils.isNotEmpty(lstNewsActive));
                resultDto.setUpdateDate(entity.getUpdateDate());
                resultDto.setReferenceId(entity.getId());
                resultDto.setComment(entity.getNote());
                // set referenceType
                String referenceType = ConstantHistoryApprove.APPROVE_HDBANK_NEWS_TYPE;
                // Nhà đầu tư
                if ((HDBankUtil.INVESTOR_TYPE_ID.equals(entity.getCustomerTypeId()))) {
                    referenceType = ConstantHistoryApprove.APPROVE_NDT_NEWS_TYPE;
                }
                resultDto.setReferenceType(referenceType);
            }
            List<NewsTypeLanguageDto> typeLanguageList = getNewsTypeLanguageList(id);
            resultDto.setTypeLanguageList(typeLanguageList);
        }

        resultDto.setBusinessCode(businessCode);
        Long processId = resultDto.getProcessId();
//        if (processId == null) {
//            // First step
//            JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//            processId = processDto.getProcessId();
//
//        }
//        // List button of step
//        List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//                resultDto.getStatus(), locale.toString());
//        resultDto.setStepBtnList(stepButtonList);
//
//        String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//        resultDto.setStatusName(statusName);
        resultDto.setProcessId(processId);
        return resultDto;
    }

    /**
     * get NewsTypeLanguageDto List
     *
     * @param typeId
     * @return NewsTypeLanguageDto list
     * @author hand
     */
    private List<NewsTypeLanguageDto> getNewsTypeLanguageList(Long typeId) {
        List<NewsTypeLanguageDto> resultList = new ArrayList<NewsTypeLanguageDto>();

        List<NewsTypeLanguage> typeLanguages = newsTypeLanguageService.findByTypeId(typeId);

        // languageList
        List<Language> languageList = languageService.findAllActive();

        // loop language
        for (Language language : languageList) {
            // loop typeLanguages
            for (NewsTypeLanguage entity : typeLanguages) {
                // newsTypeLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    NewsTypeLanguageDto typeLanguageDto = new NewsTypeLanguageDto();
                    typeLanguageDto.setId(entity.getId());
                    typeLanguageDto.setLabel(entity.getLabel());
                    typeLanguageDto.setLanguageCode(entity.getLanguageCode());
                    typeLanguageDto.setDescription(entity.getDescription());
                    typeLanguageDto.setKeyWord(entity.getKeyWord());
                    typeLanguageDto.setDescriptionKeyword(entity.getDescriptionKeyword());
                    typeLanguageDto.setLinkAlias(entity.getLinkAlias());
                    typeLanguageDto.setImageName(entity.getImageName());
                    typeLanguageDto.setPhysicalImg(entity.getPhycicalImg());
                    resultList.add(typeLanguageDto);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * add Or Edit NewsType
     *
     * @param typeEditDto
     * @author hand
     * @throws IOException
     */
    @Override
    @Transactional
    public boolean addOrEdit(NewsTypeEditDto typeEditDto, Locale locale, HttpServletRequest request)
            throws IOException {
        // user name login
//        UserProfile userProfile = UserProfileUtils.getUserProfile();

        // Edit News Type
        this.createOrEditNewsType(typeEditDto, UserProfileUtils.getUserNameLogin(), locale, request);

        // Edit News Type Language
        this.createOrEditTypeLanguage(typeEditDto, UserProfileUtils.getUserNameLogin());

        // move upload
        CmsUtils.moveTempSubFolderToUpload(
                Paths.get(AdminConstant.NEWS_TYPE_EDITOR_FOLDER, typeEditDto.getRequestToken()).toString());

        // if action process
        if (!StringUtils.equals(typeEditDto.getButtonCode(), StepActionEnum.SAVE.getCode())) {
            // update history approve
            this.updateHistoryApprove(typeEditDto);

            // send mail
            this.sendMail(typeEditDto, request, locale);
        }

        return true;
    }

    private void updateHistoryApprove(NewsTypeEditDto editDto) {
        try {
            // Về HDBank
            String referenceType = ConstantHistoryApprove.APPROVE_HDBANK_NEWS_TYPE;
            // Nhà đầu tư
            if (HDBankUtil.INVESTOR_TYPE_ID.equals(editDto.getCustomerTypeId())) {
                referenceType = ConstantHistoryApprove.APPROVE_NDT_NEWS_TYPE;
            }
            editDto.setReferenceType(referenceType);

            // insert comment
            HistoryApproveDto historyApproveDto = new HistoryApproveDto();
            historyApproveDto.setApprover(UserProfileUtils.getFullName());
            historyApproveDto.setComment(editDto.getComment());
            historyApproveDto.setProcessId(editDto.getProcessId());
            historyApproveDto.setProcessStep(editDto.getStatus().longValue());
            historyApproveDto.setReferenceId(editDto.getId());
            historyApproveDto.setReferenceType(editDto.getReferenceType());
            historyApproveDto.setActionId(editDto.getButtonCode());
            historyApproveDto.setOldStep(editDto.getOldStatus());

//            historyApproveService.addHistoryApprove(historyApproveDto);
        } catch (Exception e) {
            logger.error("updateHistoryApprove: " + e.getMessage());
        }

    }

    public void sendMail(NewsTypeEditDto editDto, HttpServletRequest request, Locale locale) {

        try {

            EmailCommonDto emailCommon = new EmailCommonDto();
            emailCommon.setActionName(msg.getMessage("email.template.type", null, locale));
            emailCommon.setButtonAction(editDto.getButtonAction());
            emailCommon.setButtonId(editDto.getButtonId().toString());
            emailCommon.setComment(editDto.getComment());

            // Nội dung
            LinkedHashMap<String, String> content = new LinkedHashMap<>();
            content.put("Mã", editDto.getCode());
            content.put("Tên hiển thị", editDto.getTypeLanguageList().get(0).getLabel());
            emailCommon.setContent(content);
            emailCommon.setCurrItem(editDto.getCurrItem());
            emailCommon.setId(editDto.getId());
            emailCommon.setOldStatus(editDto.getOldStatus());
            emailCommon.setProcessId(editDto.getProcessId());
            emailCommon.setReferenceType(editDto.getReferenceType());
            emailCommon.setStatus(editDto.getStatus());

            // Về HDBank
            String subject = msg.getMessage("subject.email.template.news.category.about.hdbank", null, locale);
            // Nhà đầu tư
            if (HDBankUtil.INVESTOR_TYPE_ID.equals(editDto.getCustomerTypeId())) {
                subject = msg.getMessage("subject.email.template.news.category.investor", null, locale);
            }
            // Subject của email
            emailCommon.setSubject(subject);

            String url = CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias() + "/news-type/edit?id="
                    + editDto.getId();
            emailCommon.setUrl(url);

//        emailUtil.sendMail(emailCommon, request, locale);
        } catch (Exception e) {
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }

    }

    /**
     * create or update NewsType
     *
     * @param editDto
     * @author hand
     */
    private void createOrEditNewsType(NewsTypeEditDto editDto, String usernameLogin, Locale locale,
            HttpServletRequest request) {

        NewsType entity = new NewsType();

        if (null != editDto.getId()) {
            entity = newsTypeRepository.findOne(editDto.getId());

            // dữ liệu bị xóa hoặc ko tìm thấy
            if (null == entity || entity.getDeleteDate() != null) {
                throw new BusinessException(
                        msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale) + editDto.getId());
            }

            // dữ liệu bị cập nhật bởi người khác
            if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(usernameLogin);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(usernameLogin);
        }

        entity.setCode(editDto.getCode());
        entity.setName(editDto.getName());
        entity.setDescription(editDto.getDescription());
        entity.setEnabled(editDto.isEnabled());
        entity.setCustomerTypeId(editDto.getCustomerTypeId());
        entity.setLinkAlias(editDto.getLinkAlias());
        entity.setNote(editDto.getComment());

        newsTypeRepository.save(entity);

        editDto.setId(entity.getId());
    }

    /**
     * createOrEditTypeLanguage
     *
     * @param editDto
     * @author hand
     * @throws IOException
     */
    private void createOrEditTypeLanguage(NewsTypeEditDto editDto, String usernameLogin) throws IOException {
        for (NewsTypeLanguageDto cLanguageDto : editDto.getTypeLanguageList()) {

            NewsTypeLanguage entity = new NewsTypeLanguage();

            if (null != cLanguageDto.getId()) {
                entity = newsTypeLanguageService.findByid(cLanguageDto.getId());
                if (null == entity) {
                    throw new BusinessException("Not found NewsTypeLanguag with id=" + cLanguageDto.getId());
                }
                entity.setUpdateDate(new Date());
                entity.setUpdateBy(usernameLogin);
            } else {
                entity.setCreateDate(new Date());
                entity.setCreateBy(usernameLogin);
            }

            entity.setmNewsTypeId(editDto.getId());
            entity.setLabel(cLanguageDto.getLabel());
            entity.setLanguageCode(cLanguageDto.getLanguageCode());
            entity.setDescription(cLanguageDto.getDescription());
            entity.setLinkAlias(cLanguageDto.getLinkAlias());
            entity.setKeyWord(cLanguageDto.getKeyWord());
            entity.setDescriptionKeyword(cLanguageDto.getDescriptionKeyword());
            entity.setPhycicalImg(cLanguageDto.getPhysicalImg());
            entity.setImageName(cLanguageDto.getImageName());

            // physical file template
            String physicalImgUrlTmp = cLanguageDto.getPhysicalImg();
            // upload images
            if (StringUtils.isNotEmpty(physicalImgUrlTmp)) {
                String newPhiscalNameUrl = CmsUtils.moveTempToUploadFolder(physicalImgUrlTmp,
                        AdminConstant.NEWS_TYPE_FOLDER);
                entity.setPhycicalImg(newPhiscalNameUrl);
            } else {
                entity.setPhycicalImg(null);
            }

            newsTypeLanguageService.saveNewsTypeLanguage(entity);
        }
    }

    /**
     * delete NewsType by id
     *
     * @param id
     * @author hand
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        // check exists NewsType
        NewsType newsType = newsTypeRepository.findOne(id);
        if (null == newsType) {
            throw new BusinessException("Not found NewsType with id=" + id);
        }

        // user name login
        String userName = UserProfileUtils.getUserNameLogin();

        // delete News referent News Type
        newsService.deleteNewsByTypeId(id);

        // delete NewsTypeLanguage
        newsTypeLanguageService.deleteByTypeId(new Date(), userName, id);

        // delete NewsType
        newsType.setDeleteDate(new Date());
        newsType.setDeleteBy(userName);
        newsTypeRepository.save(newsType);
    }

    /**
     * find NewsType by code
     *
     * @param code
     * @return
     * @author hand
     */
    @Override
    public NewsType findByCode(String code) {
        return newsTypeRepository.findByCode(code);
    }

    /**
     * find NewsType by id
     *
     * @param id
     * @return
     * @author hand
     */
    @Override
    public NewsType findById(Long id) {
        return newsTypeRepository.findOne(id);
    }

    /**
     * get max sort by TypeId
     *
     * @return
     * @author hand
     */
    @Override
    public Long findMaxSortByTypeId(Long typeId) {
        Long result = newsTypeRepository.findMaxSortByTypeId(typeId);
        return null == result ? 1 : result + 1;
    }

    /**
     * findNewsTypeListByCustomerIdAndPromotion
     * 
     * @param customerId
     * @param languageCode
     * @param promotion
     * @return List<NewsTypeDto>
     */
    @Override
    public List<NewsTypeDto> findListByCustomerIdAndPromotion(Long customerId, String languageCode, Boolean promotion) {
        // newsTypeList
        List<NewsTypeDto> newsTypeList = newsTypeRepository.findByCustomerId(customerId, languageCode, promotion);

        return newsTypeList;
    }

    /**
     * find NewsType by alias and customerId
     * 
     * @param linkAlias
     * @param customerId
     * @return NewsType
     */
    @Override
    public NewsTypeLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId) {
        return newsTypeRepository.findByAliasAndCustomerId(linkAlias, languageCode, customerId);
    }

    /**
     * find news list for sorting
     * 
     * @param languageCode
     * @return List<NewsTypeDto>
     */
    @Override
    public List<NewsTypeLanguageSearchDto> findNewsListForSorting(Long customerId, String languageCode) {
        return newsTypeRepository.findNewsListForSorting(customerId, languageCode);
    }

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author hand
     */
    @Override
    @Transactional
    public void updateSortAll(List<SortOrderDto> sortOderList) {
        for (SortOrderDto dto : sortOderList) {
            newsTypeRepository.updateSortAll(dto);
        }

        for (SortOrderDto dto : sortOderList) {
            NewsType item = newsTypeRepository.findOne(dto.getObjectId());
            newsTypeRepository.save(item);
        }
    }

    /**
     * getMaxCode
     *
     * @author nhutnn
     * @return max code
     */
    @Override
    public String getMaxCode() {
        return newsTypeRepository.getMaxCode();
    }

    @Override
    public List<Select2Dto> getNewsTypeByCustomerId(Long customerId, String lang) {
        return newsTypeRepository.findNewsTypeByCustomerId(customerId, lang);
    }

    @Override
    public List<NewsTypeEditDto> getNewsTypeEditDtoByNotId(Long id, Long customerId, String lang) {
        return newsTypeRepository.findNewsTypeEditDtoByNotId(id, customerId, lang);
    }

    @Override
    public List<NewsType> getNewsTypeSortByNotId(Long id, Long customerId, String lang) {
        return newsTypeRepository.findNewsTypeSortByNotId(id, customerId, lang);
    }

    @Override
    public NewsType getNewsTypeTypeOfLibary(Integer typeOfLibary, Long customerId) {
        return newsTypeRepository.findTypeOfLibary(typeOfLibary, customerId);
    }

    @Override
    public boolean checkTypeOfLibary(Integer typeOfLibary, Long customerId) {
        NewsType newsType = newsTypeRepository.findTypeOfLibary(typeOfLibary, customerId);
        if (newsType != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<NewsTypeCategoryDto> getNewsTypeCategory(String languageCode, Long customerTypeId, Integer status) {
        return newsTypeRepository.findByNewsCustomerTypeId(customerTypeId, languageCode, status);
    }

    @Override
    public void exportExcel(NewsTypeSearchDto searchDto, HttpServletResponse res, Locale locale) {
        try {
            // set status name
            searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

            /* change template */
            String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_NEWS_TYPE;
            String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
                    + CmsCommonConstant.TYPE_EXCEL;
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

            List<ExportNewsTypeReportDto> lstData = newsTypeRepository.exportExcelWithCondition(searchDto);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(ExportNewsTypeExportEnum.class, cols);
            ExportExcelUtil<ExportNewsTypeReportDto> exportExcel = new ExportExcelUtil<>();
            // do export
            exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportNewsTypeReportDto.class, cols,
                    datePattern, res, templateName);

        } catch (Exception e) {
            logger.error("Exception ", e);
        }
    }
}
