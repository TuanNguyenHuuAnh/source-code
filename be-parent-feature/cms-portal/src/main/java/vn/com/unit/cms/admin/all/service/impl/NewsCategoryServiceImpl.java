/*******************************************************************************
 * Class        ：NewsCategoryServiceImpl
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：TaiTM
 * Change log   ：2017/02/27：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.NewsCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.entity.NewsCategory;
import vn.com.unit.cms.admin.all.entity.NewsCategoryLanguage;
import vn.com.unit.cms.admin.all.enumdef.StatusSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.NewsCategoryRepository;
import vn.com.unit.cms.admin.all.repository.NewsTypeRepository;
import vn.com.unit.cms.admin.all.service.NewsCategoryLanguageService;
import vn.com.unit.cms.admin.all.service.NewsCategoryService;
import vn.com.unit.cms.admin.all.service.NewsService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.cms.core.module.news.repository.NewsRepository;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.exception.BusinessException;

/**
 * NewsCategoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Autowired
    private NewsCategoryLanguageService categoryLanguageService;

    @Autowired
    private NewsCategoryRepository newsCategoryRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsTypeRepository newsTypeRepository;

    @Autowired
    private NewsService newsService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CommonSearchFilterUtils commonSearchFilterUtils;

    @Autowired
    private CmsCommonService cmsCommonService;

    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private Select2DataService select2DataService;

    /**
     * add Or Edit NewsCategory
     *
     * @param categoryEditDto
     * @author TaiTM
     */
    @Override
    @Transactional
    public void addOrEdit(NewsCategoryEditDto categoryEditDto) {

        // user name login
        String userName = UserProfileUtils.getUserNameLogin();

        createOrEditNewsCategory(categoryEditDto, userName);

        createOrEditCategoryLanguage(categoryEditDto, userName);

    }

    /**
     * create or update NewsCategory
     *
     * @param editDto
     * @author TaiTM
     */
    private void createOrEditNewsCategory(NewsCategoryEditDto editDto, String userName) {

        NewsCategory entity = new NewsCategory();

        if (null != editDto.getId()) {
            entity = newsCategoryRepository.findOne(editDto.getId());

            if (null == entity) {
                throw new BusinessException("Not found News Category with id=" + editDto.getId());
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userName);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(userName);
        }

        entity.setMNewsTypeId(editDto.getMNewsTypeId());
        entity.setCode(editDto.getCode());
        entity.setNote(editDto.getNote());
        entity.setSort(editDto.getSort());
        entity.setEnabled(editDto.isEnabled());
        entity.setCustomerTypeId(editDto.getCustomerTypeId());
        entity.setLinkAlias(editDto.getLinkAlias());
        entity.setCategoryType(editDto.getCategoryType());
//        entity.setChannel(editDto.getChannel());
        if (editDto.getChannelList() != null) {         
        	entity.setChannel(String.join(",", editDto.getChannelList())); 
        }
        newsCategoryRepository.save(entity);

        editDto.setId(entity.getId());
    }

    /**
     * createOrEditCategoryLanguage
     *
     * @param editDto
     * @author TaiTM
     */
    private void createOrEditCategoryLanguage(NewsCategoryEditDto editDto, String userName) {
        for (NewsCategoryLanguageDto cLanguageDto : editDto.getCategoryLanguageList()) {

            NewsCategoryLanguage entity = new NewsCategoryLanguage();

            if (null != cLanguageDto.getId()) {
                entity = categoryLanguageService.findByid(cLanguageDto.getId());
                if (null == entity) {
                    throw new BusinessException("Not found NewsCategoryLanguag with id=" + cLanguageDto.getId());
                }
                entity.setUpdateDate(new Date());
                entity.setUpdateBy(userName);
            } else {
                entity.setCreateDate(new Date());
                entity.setCreateBy(userName);
            }

            entity.setmNewsCategoryId(editDto.getId());
            entity.setLabel(cLanguageDto.getLabel());
            entity.setLanguageCode(cLanguageDto.getlanguageCode());
            entity.setLinkAlias(cLanguageDto.getLinkAlias());
            entity.setKeyWord(cLanguageDto.getKeyWord());
            entity.setDescriptionKeyword(cLanguageDto.getDescriptionKeyword());
            categoryLanguageService.saveNewsCategoryLanguage(entity);
        }
    }

    /**
     * get NewsCategoryLanguageDto List
     *
     * @param categoryId
     * @return NewsCategoryLanguageDto list
     * @author TaiTM
     */
    private List<NewsCategoryLanguageDto> getNewsCategoryLanguageList(Long categoryId) {
        List<NewsCategoryLanguageDto> resultList = new ArrayList<NewsCategoryLanguageDto>();

        List<NewsCategoryLanguage> categoryLanguages = categoryLanguageService.findByCategoryId(categoryId);

        // languageList
        List<Language> languageList = languageService.findAllActive();

        // loop language
        for (Language language : languageList) {
            // loop categoryLanguages
            for (NewsCategoryLanguage entity : categoryLanguages) {
                // newsCategoryLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    NewsCategoryLanguageDto categoryLanguageDto = new NewsCategoryLanguageDto();
                    categoryLanguageDto.setId(entity.getId());
                    categoryLanguageDto.setLabel(entity.getLabel());
                    categoryLanguageDto.setlanguageCode(entity.getLanguageCode());
                    categoryLanguageDto.setLinkAlias(entity.getLinkAlias());
                    categoryLanguageDto.setKeyWord(entity.getKeyWord());
                    categoryLanguageDto.setDescriptionKeyword(entity.getDescriptionKeyword());
                    resultList.add(categoryLanguageDto);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * deleteNewsCategory
     *
     * @param category
     * @param userName
     * @author TaiTM
     */
    private void deleteNewsCategory(NewsCategory category, String userName) {
        // delete News
        newsService.deleteNewsByCategoryId(category.getId());

        // delete NewsCategoryLanguage
        categoryLanguageService.deleteByCategoryId(new Date(), userName, category.getId());

        // delete NewsCategory
        category.setDeleteDate(new Date());
        category.setDeleteBy(userName);
        newsCategoryRepository.save(category);
    }

    /**
     * find all NewsCategory not delete
     *
     * @param typeId
     * @param languageCode
     * @return List<NewsCategoryDto>
     * @author TaiTM
     */
    @Override
    public List<NewsCategoryDto> findByTypeIdAndLanguageCode(Long typeId, String languageCode) {
        return newsCategoryRepository.findByTypeIdAndLanguageCode(typeId, languageCode);
    }

    /**
     * find NewsCategory by typeId
     *
     * @return List<NewsCategory>
     * @author TaiTM
     */
    @Override
    public List<NewsCategory> findByTypeId(Long typeId) {
        return newsCategoryRepository.findByTypeId(typeId);
    }

    /**
     * find NewsCategory by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    @Override
    public NewsCategory findByCode(String code) {
        return newsCategoryRepository.findByCode(code);
    }

    @Override
    public List<NewsCategoryEditDto> getNewsCategoryByCustomerIdAndTypeId(Long customerId, Long typeId, String lang) {
        return newsCategoryRepository.findNewsCategoryByCustomerIdAndTypeId(customerId, typeId, lang);
    }

    @Override
    public String getBeforeIdSelectJsonByTypeId(Long customerId, Long typeId, Long categoryId, String lang) {
        List<NewsCategoryEditDto> lstCategorySort = newsCategoryRepository
                .findNewsListForSortingNotCategoryId(customerId, typeId, categoryId, lang);

        for (Iterator<NewsCategoryEditDto> iter = lstCategorySort.listIterator(); iter.hasNext();) {
            NewsCategoryEditDto newsCategoryEditDto = iter.next();
            if (newsCategoryEditDto.getId().equals(typeId)) {
                iter.remove();
            }
        }

        List<SelectItem> beforeSelect = new ArrayList<SelectItem>();
        for (NewsCategoryEditDto before : lstCategorySort) {
            beforeSelect.add(new SelectItem(before.getId().toString(), before.getName()));
        }

        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(beforeSelect);
        return categorySelectJson;
    }

    @Override
    public NewsCategoryLanguage findByAliasTypeId(String linkAlias, String languageCode, Long customerId, Long typeId) {
        return newsCategoryRepository.findByAliasTypeId(linkAlias, languageCode, customerId, typeId);
    }

    @Override
    public List<CommonSearchFilterDto> initListSearchFilter(NewsCategorySearchDto searchDto, Locale locale) {

        List<CommonSearchFilterDto> list = NewsCategoryService.super.initListSearchFilter(searchDto, locale);
        List<Select2Dto> listDataType = select2DataService.getConstantData("NEWS_TYPE", "CATEGORY_NEWS", locale.toString());

        List<CommonSearchFilterDto> rs = new ArrayList<CommonSearchFilterDto>();

        List<Select2Dto> lstData = newsTypeRepository.findNewsTypeByCustomerId(searchDto.getCustomerTypeId(),
                locale.toString());

        if (CollectionUtils.isNotEmpty(list)) {
            for (CommonSearchFilterDto filter : list) {
                if ("typeName".equals(filter.getField())) {
                    filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
                            filter.getFieldName(), searchDto.getTypeName(), lstData);
                    rs.add(filter);
                }else if ("categoryType".equals(filter.getField())) {
                    filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
                            filter.getFieldName(), searchDto.getCategoryType(), listDataType);
                    rs.add(filter);
                }
                else {
                    rs.add(filter);
                }
            }
        }
        return rs;
    }

    @Override
    public List<NewsCategorySearchResultDto> getListByCondition(NewsCategorySearchDto searchDto, Pageable pageable) {
        return newsCategoryRepository.findListSearch(searchDto, pageable).getContent();
    }

    @Override
    public int countListByCondition(NewsCategorySearchDto searchDto) {
        return newsCategoryRepository.countList(searchDto);
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
    public NewsCategoryEditDto getEditDtoById(Long id, Locale locale) {
        NewsCategoryEditDto resultDto = new NewsCategoryEditDto();

        if (id == null) {
            resultDto.setEnabled(Boolean.TRUE);
            resultDto.setStatus(StatusSearchEnum.SAVED.toString());
            resultDto.setCreatedBy(UserProfileUtils.getUserNameLogin());
            return resultDto;
        }

        // set NewsCategory
        NewsCategory category = newsCategoryRepository.findOne(id);
        if (null != category) {
            resultDto.setId(category.getId());
            resultDto.setMNewsTypeId(category.getMNewsTypeId());
            resultDto.setCode(category.getCode());
            resultDto.setNote(category.getNote());
            resultDto.setSort(category.getSort());
            resultDto.setEnabled(category.isEnabled());
            resultDto.setCustomerTypeId(category.getCustomerTypeId());
            resultDto.setLinkAlias(category.getLinkAlias());
            resultDto.setCategoryType(category.getCategoryType());
            resultDto.setChannel(category.getChannel());
            if (category.getChannel() != null) {
                String[] lstChannel = category.getChannel().split(",");
                List<String> channelsList = Arrays.asList(lstChannel);
                resultDto.setChannelList(channelsList);
            }
            
//            if (editDto.getChannelList() != null) {         
//            	editDto.setChannel(String.join(",", editDto.getChannelList())); 
//            }

            List<News> lstNewsActive = newsRepository.findByCategoryId(id);
            resultDto.setHasDisableCheckEnabled(CollectionUtils.isNotEmpty(lstNewsActive));
        }

        List<NewsCategoryLanguageDto> categoryLanguageList = getNewsCategoryLanguageList(id);
        resultDto.setCategoryLanguageList(categoryLanguageList);

        return resultDto;
    }

    @Override
    public void saveOrUpdate(NewsCategoryEditDto editDto, Locale locale) throws Exception {
        addOrEdit(editDto);
    }

    @Override
    public void deleteDataById(Long id) throws Exception {
        NewsCategory category = newsCategoryRepository.findOne(id);
        if (null == category) {
            throw new BusinessException("Not found NewsCategory with id=" + id);
        }

        // userName
        String userName = UserProfileUtils.getUserNameLogin();

        // delete NewsCategory
        deleteNewsCategory(category, userName);
    }

    @Override
    public List<NewsCategorySearchResultDto> getListForSort(NewsCategorySearchDto searchDto) {
        return newsCategoryRepository.findListForSort(searchDto);
    }

    @Override
    public void updateSortAll(NewsCategorySearchDto searchDto) {
        for (SortOrderDto dto : searchDto.getSortOderList()) {
            newsCategoryRepository.updateSortAll(dto);
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
}
