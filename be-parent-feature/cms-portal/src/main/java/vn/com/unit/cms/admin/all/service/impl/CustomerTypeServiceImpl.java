/*******************************************************************************
 * Class        ：CustomerTypeServiceImpl
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.dto.CustomerTypeDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeEditDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.CustomerType;
import vn.com.unit.cms.admin.all.entity.CustomerTypeLanguage;
import vn.com.unit.cms.admin.all.enumdef.CustomerTypeSearchEnum;
import vn.com.unit.cms.admin.all.repository.CustomerTypeRepository;
import vn.com.unit.cms.admin.all.service.CustomerTypeLanguageService;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.jcanary.dto.CustomerTypeSelectionDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * CustomerTypeServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CustomerTypeServiceImpl implements CustomerTypeService {

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private CustomerTypeLanguageService customerTypeLanguageService;

     @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private LanguageService languageService;

    /**
     * get all CustomerType by language code
     * 
     * @param languageCode
     * @return List<CustomerTypeDto>
     * @author hand
     */
    @Override
    public List<CustomerTypeDto> findByLanguageCode(String languageCode) {
        // CustomerTypeDto List
        List<CustomerTypeDto> resultList = customerTypeRepository.findByLanguageCode(languageCode);
        return resultList;
    }

    @Override
    public List<CustomerTypeDto> findByLanguageCodeAndCustomerType(Long customerTypeId, String languageCode) {
        List<CustomerTypeDto> resultList = customerTypeRepository.findByLanguageCodeAndCustomerType(customerTypeId, languageCode);
        return resultList;
    }



    /**
     * searchTypeLanguage
     *
     * @param page
     * @param searchDto
     *            CustomerTypeSearchDto
     * @return PageWrapper
     * @author hand
     */
    @Override
    public PageWrapper<CustomerTypeLanguageSearchDto> searchTypeLanguage(int page, CustomerTypeSearchDto searchDto) {
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

        PageWrapper<CustomerTypeLanguageSearchDto> pageWrapper = new PageWrapper<CustomerTypeLanguageSearchDto>(page,
                sizeOfPage);

        if (null == searchDto)
            searchDto = new CustomerTypeSearchDto();

        // set SearchParm
        setSearchParm(searchDto);

        int count = customerTypeRepository.countByCustomerTypeSearchDto(searchDto);
        List<CustomerTypeLanguageSearchDto> result = new ArrayList<CustomerTypeLanguageSearchDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            result = customerTypeRepository.findByCustomerTypeSearchDto(offsetSQL, sizeOfPage, searchDto);
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
    private void setSearchParm(CustomerTypeSearchDto condition) {
        if (null == condition.getFieldValues()) {
            condition.setFieldValues(new ArrayList<String>());
        }

        if (condition.getFieldValues().isEmpty()) {
            condition.setCode(condition.getFieldSearch());
            condition.setDescription(condition.getFieldSearch());
            condition.setTitle(condition.getFieldSearch());
        } else {
            for (String field : condition.getFieldValues()) {
                if (StringUtils.equals(field, CustomerTypeSearchEnum.CODE.name())) {
                    condition.setCode(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, CustomerTypeSearchEnum.DESCRIPTION.name())) {
                    condition.setDescription(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, CustomerTypeSearchEnum.TITLE.name())) {
                    condition.setTitle(condition.getFieldSearch());
                    continue;
                }
            }
        }
    }

    /**
     * getCustomerTypeEditDto
     *
     * @param id
     * @return CustomerTypeEditDto
     * @author hand
     */
    @Override
    public CustomerTypeEditDto getCustomerTypeEditDto(Long id) {
        CustomerTypeEditDto resultDto = new CustomerTypeEditDto();

        if (id == null) {
            Long sort = customerTypeRepository.findMaxSort();
            resultDto.setSort(sort == null ? 1 : sort + 1);
            return resultDto;
        }

        // set banner
        CustomerType entity = customerTypeRepository.findOne(id);
        if (null != entity) {
            resultDto.setId(entity.getId());
            resultDto.setCode(entity.getCode());
            resultDto.setName(entity.getName());
            resultDto.setDescription(entity.getDescription());
            resultDto.setNote(entity.getNote());
            resultDto.setSort(entity.getSort());
            resultDto.setEnabled(entity.isEnabled());
        }

        List<CustomerTypeLanguageDto> typeLanguageList = getCustomerTypeLanguageList(id);
        resultDto.setTypeLanguageList(typeLanguageList);

        return resultDto;
    }

    /**
     * get CustomerTypeLanguageDto List
     *
     * @param typeId
     * @return CustomerTypeLanguageDto list
     * @author hand
     */
    private List<CustomerTypeLanguageDto> getCustomerTypeLanguageList(Long typeId) {
        List<CustomerTypeLanguageDto> resultList = new ArrayList<CustomerTypeLanguageDto>();

        List<CustomerTypeLanguage> typeLanguages = customerTypeLanguageService.findByTypeId(typeId);

        // languageList
        List<Language> languageList = languageService.findAllActive();

        // loop language
        for (Language language : languageList) {
            // loop typeLanguages
            for (CustomerTypeLanguage entity : typeLanguages) {
                // customerTypeLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    CustomerTypeLanguageDto typeLanguageDto = new CustomerTypeLanguageDto();
                    typeLanguageDto.setId(entity.getId());
                    typeLanguageDto.setTitle(entity.getTitle());
                    typeLanguageDto.setLanguageCode(entity.getLanguageCode());

                    resultList.add(typeLanguageDto);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * add Or Edit CustomerType
     *
     * @param typeEditDto
     * @author hand
     */
    @Override
    @Transactional
    public void addOrEdit(CustomerTypeEditDto typeEditDto) {
        // user name login
        String userName = UserProfileUtils.getUserNameLogin();

        createOrEditCustomerType(typeEditDto, userName);

        createOrEditTypeLanguage(typeEditDto, userName);
    }

    /**
     * create or update CustomerType
     *
     * @param editDto
     * @author hand
     */
    private void createOrEditCustomerType(CustomerTypeEditDto editDto, String userName) {

        CustomerType entity = new CustomerType();

        if (null != editDto.getId()) {
            entity = customerTypeRepository.findOne(editDto.getId());

            if (null == entity) {
                throw new BusinessException("Not found Customer Type with id=" + editDto.getId());
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userName);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(userName);
        }

        entity.setCode(editDto.getCode());
        entity.setName(editDto.getName());
        entity.setDescription(editDto.getDescription());
        entity.setNote(editDto.getNote());
        entity.setSort(editDto.getSort());
        entity.setEnabled(editDto.isEnabled());

        customerTypeRepository.save(entity);

        editDto.setId(entity.getId());
    }

    /**
     * createOrEditTypeLanguage
     *
     * @param editDto
     * @author hand
     */
    private void createOrEditTypeLanguage(CustomerTypeEditDto editDto, String userName) {
        for (CustomerTypeLanguageDto cLanguageDto : editDto.getTypeLanguageList()) {

            CustomerTypeLanguage entity = new CustomerTypeLanguage();

            if (null != cLanguageDto.getId()) {
                entity = customerTypeLanguageService.findByid(cLanguageDto.getId());
                if (null == entity) {
                    throw new BusinessException("Not found CustomerTypeLanguag with id=" + cLanguageDto.getId());
                }
                entity.setUpdateDate(new Date());
                entity.setUpdateBy(userName);
            } else {
                entity.setCreateDate(new Date());
                entity.setCreateBy(userName);
            }

            entity.setCustomerTypeId(editDto.getId());
            entity.setTitle(cLanguageDto.getTitle());
            entity.setLanguageCode(cLanguageDto.getLanguageCode());

            customerTypeLanguageService.saveCustomerTypeLanguage(entity);
        }
    }

    /**
     * delete CustomerType by id
     *
     * @param id
     * @author hand
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        // check exists CustomerType
        CustomerType customerType = customerTypeRepository.findOne(id);
        if (null == customerType) {
            throw new BusinessException("Not found CustomerType with id=" + id);
        }

        // user name login
        String userName = UserProfileUtils.getUserNameLogin();

        // delete CustomerCategory
         categoryService.deleteByTypeId(id);

        // delete CustomerTypeLanguage
        customerTypeLanguageService.deleteByTypeId(new Date(), userName, id);

        // delete CustomerType
        customerType.setDeleteDate(new Date());
        customerType.setDeleteBy(userName);
        customerTypeRepository.save(customerType);
    }

    /**
     * find CustomerType by code
     *
     * @param code
     * @return
     * @author hand
     */
    @Override
    public CustomerType findByCode(String code) {
        return customerTypeRepository.findByCode(code);
    }

    /**
     * find CustomerType by id
     *
     * @param id
     * @return
     * @author hand
     */
    @Override
    public CustomerType findById(Long id) {
        return customerTypeRepository.findOne(id);
    }

    @Override
    public List<CustomerTypeSelectionDto> getSelectionList() {
        return customerTypeRepository.findSelectionList();
    }

	@Override
	public List<String> getTypeNamesByIds(List<Long> customerTypeIds) {
		return customerTypeRepository.findNamesByIds(customerTypeIds);
	}
}
