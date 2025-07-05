/*******************************************************************************
 * Class        ：NewsServiceImpl
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.CustomerTypeDto;
//import vn.com.unit.cms.admin.all.dto.ExportNewsReportDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.NewsEditDto;
import vn.com.unit.cms.admin.all.dto.NewsLanguageDto;
import vn.com.unit.cms.admin.all.enumdef.NewsEnum;
import vn.com.unit.cms.admin.all.enumdef.NewsExportEnum;
import vn.com.unit.cms.admin.all.enumdef.PromotionCategoryEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.NewsTypeRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.NewsCategoryService;
import vn.com.unit.cms.admin.all.service.NewsLanguageService;
import vn.com.unit.cms.admin.all.service.NewsService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
import vn.com.unit.cms.core.module.news.dto.ExportNewsReportDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.cms.core.module.news.entity.NewsLanguage;
import vn.com.unit.cms.core.module.news.repository.NewsRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.exception.SystemException;
//import vn.com.unit.common.utils.CommonDateFormatUtil;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.entity.Language;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaDatatableConfigService;
//import vn.com.unit.jcanary.repository.ProcessRepository;
//import vn.com.unit.jcanary.service.AccountService;
//import vn.com.unit.jcanary.service.EmailService;
//import vn.com.unit.cms.admin.service.FileService;
//import vn.com.unit.jcanary.service.HistoryApproveService;
//import vn.com.unit.jcanary.service.JBPMService;
//import vn.com.unit.jcanary.service.JProcessService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.ep2p.dto.JProcessStepDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * NewsServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsLanguageService newsLanguageService;

    @Autowired
    private CustomerTypeService customerTypeService;

    @Autowired
    private NewsCategoryService newsCategoryService;

    @Autowired
    private NewsTypeRepository newsTypeRepository;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    CmsFileService fileService;

    @Autowired
    private CommonSearchFilterUtils commonSearchFilterUtils;

    @Autowired
    ServletContext servletContext;
    
    @Autowired
    private JcaConstantService jcaConstantService;

    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    @Autowired
    MessageSource msg;

    @Autowired
    private CmsCommonService cmsCommonService;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final long CORPORATE_TYPE_ID = 10l;

    private final long PERSONAL_TYPE_ID = 9l;
    
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

    /**
     * init screen news edit/add
     *
     * @param mav          ModelAndView
     * @param languageCode
     * @author TaiTM
     */
    @Override
    public void initNewsEdit(ModelAndView mav, NewsEditDto newsEdit, Locale locale) {
        CmsLanguageUtils.initLanguageList(mav);

        // CustomerTypeDto List
        List<CustomerTypeDto> customerTypeList = customerTypeService.findByLanguageCode(locale.toString());
        mav.addObject("customerTypeList", customerTypeList);

        mav.addObject("custTypeList", newsService.findAllCustByLanguageCode(locale.toString()));
        /** getDate from System config */
        String patternDate = ConstantCore.FORMAT_DATE_FULL;
        mav.addObject("formatDate", patternDate);
        /** End */
   
        // News category
        List<NewsCategoryDto> lstNewsTypeCategory = newsCategoryService
                .findByTypeIdAndLanguageCode(newsEdit.getTypeId(), locale.toString());
        mav.addObject("lstNewsTypeCategory", lstNewsTypeCategory);
        
        List<JcaConstantDto> channels = jcaConstantService.getListJcaConstantDisplayByType("CHANNEL");
        mav.addObject("channels", channels);
        
        List<Select2Dto> newsTypeList = newsTypeRepository.findNewsTypeByCustomerId(newsEdit.getCustTypeId(),
                locale.toString());
        mav.addObject("newsTypeList", newsTypeList);
    }
    
    /**
     * add Or Edit News
     *
     * @param NewsEditDto
     * @param action      true is saveDraft, false is sendRequest
     * @author TaiTM
     * @throws IOException
     */
    @Override
    @Transactional
    public boolean addOrEdit(NewsEditDto newsEditDto, boolean action, HttpServletRequest request, Locale locale)
            throws IOException {

        boolean result = true;

        // user login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();

        createOrEditNews(newsEditDto, UserProfileUtils.getUserNameLogin(), action, request, locale);

        createOrEditLanguage(newsEditDto, UserProfileUtils.getUserNameLogin());

        CmsUtils.moveTempSubFolderToUpload(Paths
                .get(AdminConstant.NEWS_FOLDER, AdminConstant.EDITOR_FOLDER, newsEditDto.getRequestToken()).toString());

        return result;
    }

    /**
     * create or update News
     *
     * @param editDto
     * @param action  true is saveDraft, false is sendRequest
     * @author TaiTM
     * @throws IOException
     */
    private void createOrEditNews(NewsEditDto editDto, String userAction, boolean action, HttpServletRequest request,
            Locale locale) throws IOException {
        // m_news entity
        News entity = new News();

        // news exists id
        if (null != editDto.getId()) {
            entity = newsRepository.findOne(editDto.getId());

            // dữ liệu ko tồn tại hoặc đã bị xóa
            if (entity == null || entity.getDeleteDate() != null) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }

            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            if (entity.getUpdateDate() != null
                    && !formatDate.format(entity.getUpdateDate()).equals(formatDate.format(editDto.getUpdateDate()))) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userAction);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(userAction);
            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userAction);
        }

        try {
            entity.setCode(editDto.getCode());
            entity.setMNewsTypeId(editDto.getTypeId());
            entity.setMNewsCategoryId(editDto.getCategoryId());
            entity.setEnabled(editDto.isEnabled());

            Long sort = newsRepository.findMaxSortByTypeAndCategory(editDto.getCustTypeId());
            if (sort == null) {
                sort = 1L;
            } else {
                sort = sort + 1;
            }
            entity.setSort(sort);

            entity.setCustTypeId(editDto.getCustTypeId());
            entity.setExpirationDate(checkMaxDate(editDto.getExpirationDate()));
            entity.setPostedDate(editDto.getPostedDate());
            entity.setHomepage(editDto.isHomepage());
            entity.setEvent(editDto.isEvent());
            entity.setHot(editDto.isHot());
//            entity.setChannel(editDto.getChannel());
            if (editDto.getChannelList() != null) {         
            	entity.setChannel(String.join(",", editDto.getChannelList())); 
            }

            // set note = null
            entity.setNote(editDto.getNote());

            newsRepository.save(entity);

//            updateBeforeIdForNewsAfter(editDto, entity);

        } catch (Exception e) {
            logger.error("createOrEditNews", e.getMessage());
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
        editDto.setId(entity.getId());
        editDto.setCode(entity.getCode());

    }

    /**
     * createOrEditLanguage
     *
     * @param editDto
     * @author TaiTM
     * @throws IOException
     */
    private boolean createOrEditLanguage(NewsEditDto editDto, String userName) throws IOException {
        try {
            for (NewsLanguageDto cLanguageDto : editDto.getNewsLanguageList()) {

                // m_news_language entity
                NewsLanguage entity = new NewsLanguage();

                if (null != cLanguageDto.getId()) {
                    entity = newsLanguageService.findByid(cLanguageDto.getId());
                    if (null == entity) {
                        throw new BusinessException("Not found NewsLanguag with id=" + cLanguageDto.getId());
                    }
                    entity.setUpdateDate(new Date());
                    entity.setUpdateBy(userName);
                } else {
                    entity.setCreateDate(new Date());
                    entity.setCreateBy(userName);
                }

                entity.setmNewsId(editDto.getId());
                entity.setLanguageCode(cLanguageDto.getLanguageCode());
                entity.setTitle(cLanguageDto.getTitle());
                entity.setShortContent(cLanguageDto.getShortContent());
                entity.setContent(CmsUtils.convertStringToByteUTF8(cLanguageDto.getContent()));
                entity.setGiftMessage(cLanguageDto.getGiftMessage());
                entity.setLabel(cLanguageDto.getLabel());
                entity.setLinkAlias(cLanguageDto.getLinkAlias());
                entity.setKeyWord(cLanguageDto.getKeyWord());
                entity.setDescriptionKeyword(cLanguageDto.getDescriptionKeyword());
                entity.setBannerDesktopImg(cLanguageDto.getBannerDesktopImg());
                entity.setBannerMobileImg(cLanguageDto.getBannerMobileImg());
                entity.setImgUrl(cLanguageDto.getImgUrl());

                // physical file template
                String physicalImgTmpName = cLanguageDto.getBannerDesktopPhysicalImg();
                // upload images
                if (StringUtils.isNotEmpty(physicalImgTmpName)) {
                    String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName,
                            AdminConstant.NEWS_FOLDER);
                    entity.setBannerDesktopPhysicalImg(newPhiscalName);
                } else {
                    entity.setBannerDesktopPhysicalImg(null);
                }

                // physical file template
                String physicalImgMobileTmpName = cLanguageDto.getBannerMobilePhysicalImg();
                // upload images
                if (StringUtils.isNotEmpty(physicalImgMobileTmpName)) {
                    String newPhiscalNameMobile = CmsUtils.moveTempToUploadFolder(physicalImgMobileTmpName,
                            AdminConstant.NEWS_FOLDER);
                    entity.setBannerMobilePhysicalImg(newPhiscalNameMobile);
                } else {
                    entity.setBannerMobilePhysicalImg(null);
                }

                // physical file template
                String physicalImgUrlTmp = cLanguageDto.getPhysicalImgUrl();
                // upload images
                if (StringUtils.isNotEmpty(physicalImgUrlTmp)) {
                    String newPhiscalNameUrl = CmsUtils.moveTempToUploadFolder(physicalImgUrlTmp,
                            AdminConstant.NEWS_FOLDER);
                    entity.setPhysicalImgUrl(newPhiscalNameUrl);
                } else {
                    entity.setPhysicalImgUrl(null);
                }

                newsLanguageService.saveNewsLanguage(entity);
            }

            CmsUtils.moveTempSubFolderToUpload(
                    Paths.get(AdminConstant.NEWS_EDITOR_FOLDER, editDto.getRequestToken()).toString());
        } catch (Exception e) {
            logger.error("createOrEditLanguage", e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * get NewsEditDto
     *
     * @param id           Long
     * @param action       boolean: true is edit, false is detail
     * @param languageCode
     * @return NewsEditDto
     * @author TaiTM
     */
    @Override
    public NewsEditDto getNews(Long id, boolean action, Locale locale, String customerAlias) {
        NewsEditDto resultDto = new NewsEditDto();

        if (id == null) {
            resultDto.setEnabled(Boolean.TRUE);

            resultDto.setLang(locale.toString());
            resultDto.setCreatedBy(UserProfileUtils.getUserNameLogin());
            resultDto.setCustTypeId(HDBankUtil.getCustomerType(customerAlias));

//            Date expirationDate = null;
//            try {
//                expirationDate = DateUtils.formatStringToDate("31/12/9999", DateUtils.DDMMYYYY_HYPHEN);
//            } catch (ParseException e) {
//                expirationDate = null;
//            }
//            resultDto.setExpirationDate(expirationDate);
//            resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());

            return resultDto;
        }

        // set News
        News news = newsRepository.findOne(id);
        // dữ liệu ko tồn tại hoặc đã bị xóa
        if (news == null || news.getDeleteDate() != null) {
            throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
        }
        if (null != news) {
            String referenceType = ConstantHistoryApprove.APPROVE_NEWS;
            Long customerId = news.getCustTypeId();

            if (HDBankUtil.INVESTOR_TYPE_ID.equals(customerId)) {
                referenceType = ConstantHistoryApprove.APPROVE_NDT_NEWS;
            } else if (HDBankUtil.ABOUT_HDBANK_TYPE_ID.equals(customerId)) {
                referenceType = ConstantHistoryApprove.APPROVE_HDBANK_NEWS;
            } else if (HDBankUtil.PERSONAL_TYPE_ID.equals(customerId)) {
                referenceType = ConstantHistoryApprove.APPROVE_CN_NEWS;
            } else if (HDBankUtil.CORPORATE_TYPE_ID.equals(customerId)) {
                referenceType = ConstantHistoryApprove.APPROVE_DN_NEWS;
            }

            resultDto.setId(news.getId());
            resultDto.setTypeId(news.getMNewsTypeId());
            resultDto.setCategoryId(news.getMNewsCategoryId());
            resultDto.setCode(news.getCode());
            resultDto.setNote(news.getNote());
            resultDto.setEnabled(news.isEnabled());
            resultDto.setCustTypeId(news.getCustTypeId());
            resultDto.setExpirationDate(checkMaxDate(news.getExpirationDate()));
            resultDto.setPostedDate(news.getPostedDate());
            resultDto.setReferenceId(news.getId());
            resultDto.setReferenceType(referenceType);
            resultDto.setChannel(news.getChannel());
            if (news.getChannel() != null) {
                String[] lstChannel = news.getChannel().split(",");
                List<String> channelsList = Arrays.asList(lstChannel);
                resultDto.setChannelList(channelsList);
            }

            resultDto.setLang(locale.toString());
            resultDto.setSort(news.getSort());
            resultDto.setReferenceId(news.getId());

            resultDto.setCreatedBy(news.getCreateBy());

            resultDto.setUpdateDate(news.getUpdateDate());

            resultDto.setEvent(news.isEvent());
            resultDto.setHomepage(news.isHomepage());
            resultDto.setHot(news.isHot());
        }

        // set NewsLanguage
        List<NewsLanguageDto> newsLanguageList = getNewsLanguageList(id);
        resultDto.setNewsLanguageList(newsLanguageList);

        return resultDto;
    }

    private List<NewsLanguageDto> getNewsLanguageList(Long newsId) {
        List<NewsLanguageDto> resultList = new ArrayList<NewsLanguageDto>();
        // NewsLanguageList
        List<NewsLanguage> newsLanguageList = newsLanguageService.findByNewsId(newsId);

        // languageList
        List<Language> languageList = languageService.findAllActive();
        // loop language
        for (Language language : languageList) {
            // loop newsLanguageList
            for (NewsLanguage entity : newsLanguageList) {
                // newsLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    NewsLanguageDto newsLanguageDto = new NewsLanguageDto();
                    newsLanguageDto.setId(entity.getId());
                    newsLanguageDto.setLanguageCode(entity.getLanguageCode());
                    newsLanguageDto.setTitle(entity.getTitle());
                    newsLanguageDto.setShortContent(entity.getShortContent());
                    if (null != entity.getContent()) {
                        newsLanguageDto.setContent(CmsUtils.converByteToStringUTF8(entity.getContent()));
                    }
                    newsLanguageDto.setGiftMessage(entity.getGiftMessage());
                    newsLanguageDto.setLinkAlias(entity.getLinkAlias());
                    newsLanguageDto.setLabel(entity.getLabel());
                    newsLanguageDto.setKeyWord(entity.getKeyWord());
                    newsLanguageDto.setDescriptionKeyword(entity.getDescriptionKeyword());
                    newsLanguageDto.setBannerDesktopImg(entity.getBannerDesktopImg());
                    newsLanguageDto.setBannerMobileImg(entity.getBannerMobileImg());
                    newsLanguageDto.setImgUrl(entity.getImgUrl());
                    newsLanguageDto.setBannerDesktopPhysicalImg(entity.getBannerDesktopPhysicalImg());
                    newsLanguageDto.setBannerMobilePhysicalImg(entity.getBannerMobilePhysicalImg());
                    newsLanguageDto.setPhysicalImgUrl(entity.getPhysicalImgUrl());
                    resultList.add(newsLanguageDto);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * get CategorySelect json string
     *
     * @param typeId
     * @param languageCode
     * @return String
     * @author TaiTM
     */
    @Override
    public String getCategorySelectJson(Long typeId, String languageCode) {
        List<NewsCategoryDto> categoryList = newsCategoryService.findByTypeIdAndLanguageCode(typeId, languageCode);

        List<SelectItem> categorySelect = new ArrayList<SelectItem>();
        for (NewsCategoryDto category : categoryList) {
            categorySelect.add(new SelectItem(category.getId().toString(), category.getLabel()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
        return categorySelectJson;
    }

    /**
     * find News by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    @Override
    public News findByCode(String code) {
        return newsRepository.findByCode(code);
    }

    /**
     * delete News by entity
     *
     * @author TaiTM
     */
    @Override
    @Transactional
    public void deleteById(News news) {
        // userName
        String userName = UserProfileUtils.getUserNameLogin();
        // delete news
        deleteByNews(news, userName);

    }

    /**
     * delete News by category id
     *
     * @param typeId
     * @author TaiTM
     */
    @Override
    @Transactional
    public void deleteNewsByCategoryId(Long typeId) {
        // userName
        String userName = UserProfileUtils.getUserNameLogin();

        List<News> newsList = newsRepository.findByCategoryId(typeId);

        for (News news : newsList) {
            deleteByNews(news, userName);
        }
    }

    /**
     * delete News by news
     *
     * @param news
     * @author TaiTM
     */
    private void deleteByNews(News news, String userName) {

        // delete NewsNewsLanguage
        newsLanguageService.deleteByNewsId(new Date(), userName, news.getId());

        // delete NewsNews
        news.setDeleteDate(new Date());
        news.setDeleteBy(userName);
        newsRepository.save(news);
    }

    /**
     * find News by id
     *
     * @param id
     * @return News
     * @author TaiTM
     */
    @Override
    public News findById(Long id) {
        return newsRepository.findOne(id);
    }

    /**
     * findMaxSortByTypeAndCategory
     *
     * @param typeId
     * @return maxSort
     * @author TaiTM
     */
    @Override
    public Long findMaxSortByTypeAndCategory(Long customerId) {
        Long result = newsRepository.findMaxSortByTypeAndCategory(customerId);
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

    @Override
    public Map<String, Integer> findAllPromotionType() {
        Map<String, Integer> result = new HashMap<>();
        for (PromotionCategoryEnum item : PromotionCategoryEnum.values()) {
            result.put(item.getCode(), item.getValue());
        }
        return result;
    }

    @Override
    public Map<Long, String> findAllCustByLanguageCode(String languageCode) {
        List<CustomerTypeDto> custTypeList = customerTypeService.findByLanguageCode(languageCode);
        Map<Long, String> result = new HashMap<>();
        for (CustomerTypeDto item : custTypeList) {
            result.put(item.getId(), item.getTitle());
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteNewsByTypeId(Long typeId) {
        // userName
        String userName = UserProfileUtils.getUserNameLogin();

        List<News> newsList = newsRepository.findByTypeId(typeId);

        for (News news : newsList) {
            deleteByNews(news, userName);
        }

    }

    /**
     * Find news list for sorting
     * 
     * @param NewsSearchDto
     * @return List<NewsLanguageSearchDto>
     */
    @Override
    public List<NewsSearchResultDto> findNewsListForSorting(NewsSearchDto dto) {
        return newsRepository.findNewsListForSorting(dto);
    }

    /**
     * getMaxCode
     *
     * @author nhutnn
     * @return max code
     */
    @Override
    public String getMaxCode() {
        return newsRepository.getMaxCode();
    }

    @Override
    public List<NewsSearchResultDto> getNewsListForSortingByTypeIdAndCategoryId(Long customerId, Long id, Long typeId,
            Long categoryId, String lang) {
        return newsRepository.findNewsListForSortingByTypeIdAndCategoryId(customerId, id, typeId, categoryId, lang);
    }

    @Override
    public NewsLanguage findLangByLinkAlias(String linkAlias, String languageCode, Long customerId, Long categoryId,
            Long typeId) {
        NewsLanguage newsLanguage = new NewsLanguage();
        if (customerId == 9 || customerId == 10) {
            newsLanguage = newsRepository.findLangByLinkAlias(linkAlias, languageCode, customerId, categoryId, typeId);
        } else {
            newsLanguage = newsRepository.findLangByLinkAliasWithNewsCategoryIdAndNewsTypeId(linkAlias, languageCode,
                    customerId, categoryId, typeId);
        }
        return newsLanguage;
    }

    @Override
    public List<NewsSearchResultDto> getNewsListByProductForSorting(NewsSearchDto searchDto, String lang) {
        List<NewsSearchResultDto> result = newsRepository.findNewsListByProductForSorting(searchDto, lang);

        if (result == null) {
            result = new ArrayList<NewsSearchResultDto>();
        }
        return result;
    }

    @Override
    public void exportExcel(NewsSearchDto newsSearch, HttpServletResponse res, Locale locale, Long customerId) {
        try {
            /* change template */
            String templateName = null;
            if (customerId == PERSONAL_TYPE_ID || customerId == CORPORATE_TYPE_ID) {
                templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_PROMOTION;

                String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
                        + templateName + CmsCommonConstant.TYPE_EXCEL;
                String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

                List<NewsSearchResultDto> lstDatas = new ArrayList<NewsSearchResultDto>();

                lstDatas = newsRepository.exportExcelNews(newsSearch);

                List<ItemColsExcelDto> cols = new ArrayList<>();
                // start fill data to workbook
                ImportExcelUtil.setListColumnExcel(NewsEnum.class, cols);
                ExportExcelUtil<NewsSearchResultDto> exportExcel = new ExportExcelUtil<>();
                // do export
                exportExcel.exportExcelWithXSSFNonPass(template, locale, lstDatas, NewsSearchResultDto.class, cols,
                        datePattern, res, templateName);
            } else {
                templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_NEWS;

                String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
                        + templateName + CmsCommonConstant.TYPE_EXCEL;
                String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

                List<ExportNewsReportDto> lstDatas = new ArrayList<ExportNewsReportDto>();

                lstDatas = newsRepository.exportExcelNewsExportEnum(newsSearch);

                List<ItemColsExcelDto> cols = new ArrayList<>();
                // start fill data to workbook
                ImportExcelUtil.setListColumnExcel(NewsExportEnum.class, cols);
                ExportExcelUtil<ExportNewsReportDto> exportExcel = new ExportExcelUtil<>();
                // do export
                exportExcel.exportExcelWithXSSFNonPass(template, locale, lstDatas, ExportNewsReportDto.class, cols,
                        datePattern, res, templateName);
            }
        } catch (Exception e) {
            logger.error("##exportExcel##", e);
        }

    }

    @Override
    public Long countByProductId(String productId) {
        return newsRepository.countByProductId(productId);
    }

    @Override
    public NewsLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId) {
        return newsRepository.findByAliasAndCustomerId(linkAlias, languageCode, customerId);
    }

    @Override
    public void initNewsListSearch(ModelAndView mav, NewsSearchDto newsSearch, Locale locale) {
        String languageCode = locale.toString();

        // News Category
        List<NewsCategoryEditDto> lstNewsCategory = newsCategoryService
                .getNewsCategoryByCustomerIdAndTypeId(newsSearch.getCustTypeId(), -1L, languageCode);
        mav.addObject("lstNewsCategory", lstNewsCategory);

        List<CommonSearchFilterDto> listSearchFilter = initListSearchFilter(newsSearch, locale);
        mav.addObject("listSearchFilter", listSearchFilter);
    }

    @Override
    public List<CommonSearchFilterDto> initListSearchFilter(NewsSearchDto searchDto, Locale locale) {
        List<CommonSearchFilterDto> list = NewsService.super.initListSearchFilter(searchDto, locale);
        List<CommonSearchFilterDto> rs = new ArrayList<>();

        List<Select2Dto> lstData = newsTypeRepository.findNewsTypeByCustomerId(searchDto.getCustTypeId(),
                locale.toString());

        if (CollectionUtils.isNotEmpty(list)) {
            for (CommonSearchFilterDto filter : list) {
                if ("newsType".equals(filter.getField())) {
                    filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
                            filter.getFieldName(), searchDto.getNewsType(), lstData);
                    rs.add(filter);
                } else {
                    rs.add(filter);
                }
            }
        }
        return rs;
    }

    @Override
    public List<NewsSearchResultDto> getListByCondition(NewsSearchDto searchDto, Pageable pageable) {
        return newsRepository.findListSearch(searchDto, pageable).getContent();
    }

    @Override
    public int countListByCondition(NewsSearchDto searchDto) {
        return newsRepository.countList(searchDto);
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
    public NewsEditDto getEditDtoById(Long id, Locale locale) {
        return getNews(id, false, locale, "personal");
    }

    @Override
    public void saveOrUpdate(NewsEditDto editDto, Locale locale) throws Exception {
        addOrEdit(editDto, false, null, locale);
    }

    @Override
    public void deleteDataById(Long id) throws Exception {
        News entity = newsRepository.findOne(id);
        deleteByNews(entity, getMaxCode());
    }

    @Override
    public List<NewsSearchResultDto> getListForSort(NewsSearchDto searchDto) {
        return newsRepository.findNewsListForSorting(searchDto);
    }

    @Override
    public void updateSortAll(NewsSearchDto searchDto) {
        if (CollectionUtils.isNotEmpty(searchDto.getSortOderList())) {
        	Date getDate = new Date();
            for (SortOrderDto sortItem : searchDto.getSortOderList()) {
            	sortItem.setUpdateDate(getDate);
                newsRepository.updateSortAll(sortItem);
            }
        }
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getEnumColumnForExport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTemplateNameForExport(Locale locale) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonSearchFilterUtils getCommonSearchFilterUtils() {
        return commonSearchFilterUtils;
    }
    
    @Override
	public Integer getCategoryNameCheck(Long categoryId,Long typeId,Long newsId){
		return newsRepository.findCategoryName(categoryId,typeId,newsId);
	};

}