/*******************************************************************************
 * Class        ：CustomerTypeRepository
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.CustomerTypeDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.CustomerType;
import vn.com.unit.cms.admin.all.jcanary.dto.CustomerTypeSelectionDto;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.jcanary.dto.CustomerTypeSelectionDto;

/**
 * CustomerTypeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface CustomerTypeRepository extends DbRepository<CustomerType, Long> {

    /**
     * find all CustomerType by language code
     * 
     * @param languageCode
     * @return
     * @author hand
     */
    List<CustomerTypeDto> findByLanguageCode(@Param("languageCode") String languageCode);
    
    List<CustomerTypeDto> findByLanguageCodeAndCustomerType(@Param("customerTypeId") Long customerTypeId, @Param("languageCode") String languageCode);

    /**
     * count all record TypeLanguage
     *
     * @param searchDto
     * @return int
     * @author hand
     */
    int countByCustomerTypeSearchDto(@Param("searchCond") CustomerTypeSearchDto searchDto);

    /**
     * find all TypeLanguage by CustomerTypeSearchDto
     *
     * @param offsetSQL
     * @param sizeOfPage
     * @param searchDto
     *            CustomerTypeSearchDto
     * @return
     * @author hand
     */
    List<CustomerTypeLanguageSearchDto> findByCustomerTypeSearchDto(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("searchCond") CustomerTypeSearchDto searchDto);

    /**
     * find CustomerType by code
     *
     * @param code
     * @return CustomerType
     * @author hand
     */
    CustomerType findByCode(@Param("code") String code);

    /**
     * get max sort
     *
     * @return
     * @author hand
     */
    Long findMaxSort();
    
    /**
     * 
     * @return
     * @author thuydtn
     */
    List<CustomerTypeSelectionDto> findSelectionList();
    
    /**
     * @param typeId
     * @return
     * @author thuydtn
     */
    String findTitleById(@Param("typeId") Long typeId);

    /**
     * @param customerTypeIds
     * @return
     */
	List<String> findNamesByIds(@Param("typeIds")List<Long> customerTypeIds);
}
