/*******************************************************************************
 * Class        ：CustomerTypeService
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.CustomerTypeDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeEditDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.CustomerType;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.cms.admin.all.jcanary.dto.CustomerTypeSelectionDto;

/**
 * CustomerTypeService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface CustomerTypeService {
    /**
     * get all CustomerType by language code
     * 
     * @param languageCode
     * @return List<CustomerTypeDto>
     * @author hand
     */
    public List<CustomerTypeDto> findByLanguageCode(String languageCode);
    
    public List<CustomerTypeDto> findByLanguageCodeAndCustomerType(Long customerTypeId, String languageCode);

    /**
     * searchTypeLanguage
     *
     * @param page
     * @param searchDto
     *            CustomerTypeSearchDto
     * @return PageWrapper
     * @author hand
     */
    public PageWrapper<CustomerTypeLanguageSearchDto> searchTypeLanguage(int page, CustomerTypeSearchDto searchDto);

    /**
     * getCustomerTypeEditDto
     *
     * @param id
     * @return CustomerTypeEditDto
     * @author hand
     */
    public CustomerTypeEditDto getCustomerTypeEditDto(Long id);

    /**
     * add Or Edit CustomerType
     *
     * @param categoryEditDto
     * @author hand
     */
    public void addOrEdit(CustomerTypeEditDto categoryEditDto);

    /**
     * delete CustomerType by id
     *
     * @param id
     * @author hand
     */
    public void deleteById(Long id);

    /**
     * find CustomerType by code
     *
     * @param code
     * @return
     * @author hand
     */
    public CustomerType findByCode(String code);

    /**
     * find CustomerType by id
     *
     * @param id
     * @return
     * @author hand
     */
    public CustomerType findById(Long id);
    
    /**
     * 
     * @return
     * @author thuydtn
     */
    public List<CustomerTypeSelectionDto> getSelectionList();

    /**
     * @param customerTypeIds
     * @return
     */
	public List<String> getTypeNamesByIds(List<Long> customerTypeIds);
}
