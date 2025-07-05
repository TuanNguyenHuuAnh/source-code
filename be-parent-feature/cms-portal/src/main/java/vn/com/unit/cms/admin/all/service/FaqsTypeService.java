/*******************************************************************************
 * Class        ：FaqsTypeService
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：vinhnht
 * Change log   ：2017/02/28：01-00 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.FaqsTypeDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeSearchDto;
//import vn.com.unit.cms.admin.all.dto.ProductCategoryEditDto;
import vn.com.unit.cms.admin.all.entity.FaqsType;
import vn.com.unit.cms.admin.all.entity.FaqsTypeLanguage;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * FaqsTypeService
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
public interface FaqsTypeService {

    /**
     * findFaqsTypeList
     *
     * @param conditon
     * @param page
     * @return
     * @author hand
     */
    public PageWrapper<FaqsTypeSearchDto> findFaqsTypeList(FaqsTypeSearchDto conditon,
            int page);

    /**
     * findTypeCategory
     *
     * @param id
     * @return
     * @author hand
     */
    public FaqsTypeEditDto findTypeCategory(Long id);

    /**
     * findFaqsType
     *
     * @param id
     * @return
     * @author hand
     */
    public FaqsType findFaqsType(Long id);

    /**
     * deleteFaqsType
     *
     * @param id
     * @author hand
     */
    public void deleteFaqsType(Long id);

    /**
     * findAllFaqsType
     *
     * @param lang
     * @return
     * @author hand
     */
    public List<FaqsTypeDto> findAllFaqsType(String lang);

    /**
     * findFaqsTypeByCode
     *
     * @param code
     * @return
     * @author hand
     */
    public FaqsType findFaqsTypeByCode(String code);

    /**
     * addOrEdit FaqsType
     *
     * @param editDto
     * @author hand
     */
    public void addOrEdit(FaqsTypeEditDto editDto);
    
    /**
     * find FaqsType by id
     *
     * @param id
     * @return
     * @author hand
     */
    public FaqsType findById(Long id);
    /**
     * findFaqsTypeByAlias
     *
     * @param alias
     * @return
     * @author TranLTH
     */
    public FaqsTypeEditDto findFaqsTypeByAlias(String alias);
    
    public String getMaxCode();

    /** findAllFaqsTypeByCutomerId
     *
     * @param lang
     * @param customerTypeId
     * @author nhutnn
     */
    public List<FaqsTypeDto> findAllFaqsTypeByCutomerId(String lang, Long customerTypeId);
    
    public List<FaqsTypeSearchDto> findListForSort(String languageCode, Long customerId);
    
    public void updateSortAll(List<SortOrderDto> sortOderList);
    
    public List<FaqsTypeEditDto> findAllFaqsTypeAndNotIn(String languageCode , FaqsTypeEditDto faqsTypeEditDto);
    
    public void updateSort(FaqsTypeEditDto faqsTypeEditDto);

    FaqsTypeLanguage findFaqsTypeLangByAlias(String linkAlias, String languageCode, Long customerId);
}
