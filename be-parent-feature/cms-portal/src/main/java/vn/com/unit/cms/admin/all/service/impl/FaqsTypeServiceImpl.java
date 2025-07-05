/*******************************************************************************
 * Class        ：FaqsTypeImpl
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：vinhnht
 * Change log   ：2017/02/28：01-00 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.dto.FaqsTypeDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.FaqsType;
import vn.com.unit.cms.admin.all.entity.FaqsTypeLanguage;
import vn.com.unit.cms.admin.all.repository.FaqsTypeRepository;
import vn.com.unit.cms.admin.all.service.FaqsCategoryService;
import vn.com.unit.cms.admin.all.service.FaqsTypeLanguageService;
import vn.com.unit.cms.admin.all.service.FaqsTypeService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * FaqsTypeImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FaqsTypeServiceImpl implements FaqsTypeService {

    @Autowired
    private FaqsTypeRepository faqsTypeRepository;

    @Autowired
    private FaqsTypeLanguageService faqsTypeLanguageService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private FaqsCategoryService faqsCategoryService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * Find list category faqs parent
     * 
     * @param condition
     * @return PageWrapper<FaqsTypeDto>
     * @author vinhnht
     */
    @Override
    public PageWrapper<FaqsTypeSearchDto> findFaqsTypeList(FaqsTypeSearchDto typeCateDto, int page) {
        if (null == typeCateDto)
            typeCateDto = new FaqsTypeSearchDto();
        int sizeOfPage = typeCateDto.getPageSize() != null ? typeCateDto.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

        PageWrapper<FaqsTypeSearchDto> pageWrapper = new PageWrapper<FaqsTypeSearchDto>(page, sizeOfPage);

        // set SearchParm
//        setSearchParm(typeCateDto);

        int count = faqsTypeRepository.countFaqsType(typeCateDto);
        
        if ((count % sizeOfPage == 0 && page > count / sizeOfPage) || (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
            page = 1;
        }
        
        List<FaqsTypeSearchDto> result = null;
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
            result = faqsTypeRepository.findFaqsTypeList(offsetSQL, sizeOfPage, typeCateDto);
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    /**
     * set SearchParm
     *
     * @param condition
     * @author hand
     */
//    private void setSearchParm(FaqsTypeSearchDto condition) {
//        if (null == condition.getFieldValues()) {
//            condition.setFieldValues(new ArrayList<String>());
//        }
//
//        if (condition.getFieldValues().isEmpty()) {
//            condition.setCode(condition.getFieldSearch());
//            condition.setTitle(condition.getFieldSearch());
//            condition.setDescription(condition.getFieldSearch());
//        } else {
//            for (String field : condition.getFieldValues()) {
////                if (StringUtils.equals(field, FaqsTypeSearchEnum.CODE.name())) {
////                    condition.setCode(condition.getFieldSearch());
////                    continue;
////                }
//                if (StringUtils.equals(field, FaqsTypeSearchEnum.NAME.name())) {
//                    condition.setTitle(condition.getFieldSearch());
//                    continue;
//                }
//
////                if (StringUtils.equals(field, FaqsTypeSearchEnum.DESCRIPTION.name())) {
////                    condition.setDescription(condition.getFieldSearch());
////                    continue;
////                }
//            }
//        }
//    }

    /**
     * Find type category
     * 
     * @param id
     * @return FaqsTypeDto
     * @author hand
     */
    @Override
    public FaqsTypeEditDto findTypeCategory(Long id) {
        FaqsTypeEditDto resultDto = new FaqsTypeEditDto();

        if (id == null) {
            Long sort = faqsTypeRepository.findMaxSort();
            resultDto.setSort(sort == null ? 1 : sort + 1);
            return resultDto;
        }

        // set FaqsType
        FaqsType entity = faqsTypeRepository.findOne(id);
        if (null != entity) {
            resultDto.setId(entity.getId());
            resultDto.setCode(entity.getCode());
            resultDto.setName(entity.getName());
            resultDto.setDescription(entity.getDescription());
            resultDto.setNote(entity.getNote());
            resultDto.setSort(entity.getSort());
            resultDto.setEnabled(entity.isEnabled());
            resultDto.setDescription(entity.getDescription());
            resultDto.setLinkAlias(entity.getLinkAlias());
            resultDto.setApproveBy(entity.getApproveBy());
            resultDto.setPublishBy(entity.getPublishBy());
            resultDto.setCreateBy(entity.getCreateBy());
            resultDto.setBeforeId(entity.getBeforeId());
        }

        List<FaqsTypeLanguageDto> typeFaqsLanguageList = getFaqsLanguageList(id);
        resultDto.setTypeFaqsLanguageList(typeFaqsLanguageList);

        return resultDto;
    }

    /**
     * get FaqsLanguageDto List
     *
     * @param faqsId
     * @return FaqsLanguageDto list
     * @author hand
     */
    private List<FaqsTypeLanguageDto> getFaqsLanguageList(Long faqsId) {
        List<FaqsTypeLanguageDto> resultList = new ArrayList<FaqsTypeLanguageDto>();

        List<FaqsTypeLanguage> faqsLanguageList = faqsTypeLanguageService.findByTypeId(faqsId);

        // languageList
        List<Language> languageList = languageService.findAllActive();

        // loop language
        for (Language language : languageList) {
            // loop categoryLanguages
            for (FaqsTypeLanguage entity : faqsLanguageList) {
                // faqsCategoryLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    FaqsTypeLanguageDto faqsLanguageDto = new FaqsTypeLanguageDto();
                    faqsLanguageDto.setId(entity.getId());
                    faqsLanguageDto.setTitle(entity.getTitle());
                    faqsLanguageDto.setLanguageCode(entity.getLanguageCode());
                    faqsLanguageDto.setTypeId(entity.getTypeId());
                    faqsLanguageDto.setKeyWord(entity.getKeyWord());
                    faqsLanguageDto.setKeyWordDescription(entity.getKeyWordDescription());
                    faqsLanguageDto.setLinkAlias(entity.getLinkAlias());

                    resultList.add(faqsLanguageDto);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * Find type category Faqs
     * 
     * @param id
     * @return FaqsType
     * @author vinhnht
     */
    @Override
    public FaqsType findFaqsType(Long id) {
        return faqsTypeRepository.findOne(id);
    }

    /**
     * Delete Faqs type
     * 
     * @param id
     * @author hand
     */
    @Override
    @Transactional
    public void deleteFaqsType(Long id) {
        // check exists FaqsType
        FaqsType faqsType = faqsTypeRepository.findOne(id);
        if (null == faqsType) {
            throw new BusinessException("Not found FaqsType with id=" + id);
        }

        // user name login
        String userName = UserProfileUtils.getUserNameLogin();

        // delete FaqsCategory
        faqsCategoryService.deleteByTypeId(id);

        // delete FaqsTypeLanguage
        faqsTypeLanguageService.deleteByTypeId(id, userName);

        // delete FaqsType
        faqsType.setDeleteDate(new Date());
        faqsType.setDeleteBy(userName);
        faqsTypeRepository.save(faqsType);
    }

    /**
     * find all type category faqs
     * @returnList<FaqsTypeDto>
     * 
     * @author vinhnht
     */
    @Override
    public List<FaqsTypeDto> findAllFaqsType(String lang) {
        return faqsTypeRepository.findAllFaqsType(lang);
    }
    
    @Override
    public List<FaqsTypeDto> findAllFaqsTypeByCutomerId(String lang, Long customerTypeId) {
        return faqsTypeRepository.findAllFaqsTypeByCutomerId(lang, customerTypeId);
    }

    /**
     * Find type category faqs by code
     * 
     * @param code
     * @return FaqsType
     * @author vinhnht
     */
    @Override
    public FaqsType findFaqsTypeByCode(String code) {
        return faqsTypeRepository.findFaqsTypeByCode(code);
    }

    /**
     * addOrEdit FaqsType
     *
     * @param editDto
     * @author hand
     */
    @Override
    @Transactional
    public void addOrEdit(FaqsTypeEditDto editDto) {
    	// user name login
        String userName = UserProfileUtils.getUserNameLogin();
    	
        createOrEditTypeFaqs(editDto, userName);

        createOrEditLanguage(editDto, userName);

    }

    /**
     * create or update TypeCategoryFaq
     *
     * @param editDto
     * @author hand
     */
    private void createOrEditTypeFaqs(FaqsTypeEditDto editDto, String username) {
        // m_type_category_faqs entity
        FaqsType entity = new FaqsType();

        // faqs exists id
        if (null != editDto.getId()) {
            entity = faqsTypeRepository.findOne(editDto.getId());

            if (null == entity) {
                throw new BusinessException("Not found FaqsType with id=" + editDto.getId());
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(username);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(username);
        }

        entity.setCode(editDto.getCode());
        entity.setName(editDto.getName());
        entity.setDescription(editDto.getDescription());
        entity.setNote(editDto.getNote());
        entity.setSort(editDto.getSort());
        entity.setEnabled(editDto.isEnabled());
        entity.setLinkAlias(editDto.getLinkAlias());
        entity.setCustomerTypeId(editDto.getCustomerTypeId());
        entity.setStatus(editDto.getStatus());
        updateSort(editDto);
        entity.setBeforeId(editDto.getBeforeId());
        entity.setSort(editDto.getSort());
        
        faqsTypeRepository.save(entity);

        editDto.setId(entity.getId());
    }

    /**
     * createOrEditLanguage
     *
     * @param editDto
     * @author hand
     */
    private void createOrEditLanguage(FaqsTypeEditDto editDto, String userName) {
        for (FaqsTypeLanguageDto faqsDto : editDto.getTypeFaqsLanguageList()) {

            // m_type_category_faqs_language entity
            FaqsTypeLanguage entity = new FaqsTypeLanguage();

            if (null != faqsDto.getId()) {
                entity = faqsTypeLanguageService.findById(faqsDto.getId());
                if (null == entity) {
                    throw new BusinessException("Not found FaqsTypeLanguage with id=" + faqsDto.getId());
                }
                entity.setUpdateDate(new Date());
                entity.setUpdateBy(userName);
            } else {
                entity.setCreateDate(new Date());
                entity.setCreateBy(userName);
            }

            entity.setTypeId(editDto.getId());
            entity.setLanguageCode(faqsDto.getLanguageCode());
            entity.setTitle(faqsDto.getTitle());
            entity.setLinkAlias(faqsDto.getLinkAlias());
            entity.setKeyWord(faqsDto.getKeyWord());
            entity.setKeyWordDescription(faqsDto.getKeyWordDescription());
            faqsTypeLanguageService.saveFaqsTypeLanguage(entity);
        }
    }

    /**
     * find FaqsType by id
     *
     * @param id
     * @return
     * @author hand
     */
    public FaqsType findById(Long id) {
        return faqsTypeRepository.findOne(id);
    }
    
    @Override
    public FaqsTypeEditDto findFaqsTypeByAlias(String alias) {
        return faqsTypeRepository.findFaqsTypeByAlias(alias);
    }
    
    @Override
    public FaqsTypeLanguage findFaqsTypeLangByAlias(String linkAlias, String languageCode, Long customerId) {
        return faqsTypeRepository.findFaqsTypeLangByAlias(linkAlias, languageCode, customerId);
    }

    @Override
    public String getMaxCode() {
        return faqsTypeRepository.getMaxCode();
    }

    @Override
    public List<FaqsTypeSearchDto> findListForSort(String languageCode, Long customerId) {
        return faqsTypeRepository.findListForSort(languageCode, customerId);
    }

    @Override
    public void updateSortAll(List<SortOrderDto> sortOderList) {
        for (SortOrderDto dto : sortOderList) {
            faqsTypeRepository.updateSortAll(dto);
        }
        
        Long itemId = 0L;
        for (SortOrderDto dto : sortOderList) {
            FaqsType item = faqsTypeRepository.findOne(dto.getObjectId());
            item.setBeforeId(itemId);
            itemId = item.getId();
            faqsTypeRepository.save(item);
        }        
    }

	@Override
	public List<FaqsTypeEditDto> findAllFaqsTypeAndNotIn(String languageCode, FaqsTypeEditDto faqsTypeEditDto) {
		return faqsTypeRepository.findAllFaqsTypeAndNotIn(languageCode, faqsTypeEditDto);
	}
	
	@Override
	public void updateSort(FaqsTypeEditDto currentEditDto) {
        List<FaqsType> lstFaqsType =  faqsTypeRepository.findByTypeId(currentEditDto.getCustomerTypeId());
        Long currentSort = 1L;
        FaqsType beforeEntity = new FaqsType();
        if(null != currentEditDto.getBeforeId()) {
            if(currentEditDto.getBeforeId().compareTo(0L) != 0) {
                beforeEntity = faqsTypeRepository.findOne(currentEditDto.getBeforeId());
                if(null != beforeEntity.getDeleteDate()) {
                    currentEditDto.setBeforeId(beforeEntity.getBeforeId());
                    updateSort(currentEditDto);
                } 
            }else {
                currentEditDto.setSort(currentSort++);
            }
        }else {
            if(CollectionUtils.isNotEmpty(lstFaqsType)) {
                currentEditDto.setSort(new Long(lstFaqsType.size())+1);
            }else {
                currentEditDto.setSort(currentSort++); 
            }
        }
        
        if(CollectionUtils.isNotEmpty(lstFaqsType)) {
            Iterator<FaqsType> itr = lstFaqsType.iterator();
            while(itr.hasNext()) {
                FaqsType element = (FaqsType) itr.next();
                if(null!= currentEditDto.getId() && currentEditDto.getId().compareTo(element.getId())== 0) {
                    itr.remove();
                    break;
                }
             }

            for(FaqsType item : lstFaqsType) {
                item.setSort(currentSort++);
                if(null != currentEditDto.getBeforeId() && currentEditDto.getBeforeId().compareTo(item.getId()) == 0) {
                    currentEditDto.setSort(currentSort++);
                    if(null != currentEditDto.getId()) {
                    	FaqsType currentEntity = faqsTypeRepository.findOne(currentEditDto.getId());
                        currentEntity.setSort(currentEditDto.getSort());
                        faqsTypeRepository.save(currentEntity);
                    }
                }
                
                faqsTypeRepository.save(item);
               
            }
            updateBeforeId(currentEditDto);
        }else {
            currentEditDto.setBeforeId(0L);
        }
    }
	
	public void updateBeforeId(FaqsTypeEditDto currentEditDto) {
        List<FaqsType> lstFaqsType =  faqsTypeRepository.findByTypeId(currentEditDto.getCustomerTypeId());
        Long itemId = 0L;
        for(FaqsType item : lstFaqsType) {
            item.setBeforeId(itemId);
            itemId = item.getId();
            faqsTypeRepository.save(item);
        }
    }
}