/*******************************************************************************
 * Class        ：FaqsCategoryService
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.FaqsCategoryDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryLanguage;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

/**
 * FaqsServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface FaqsCategoryService
        extends CmsCommonSearchFillterService<FaqsCategorySearchDto, FaqsCategorySearchResultDto, FaqsCategoryEditDto> {

    /**
     * getFaqsCategory
     *
     * @param id
     * @param action true is edit, false is detail
     * @return FaqsCategoryEditDto
     * @author TaiTM
     */
    public FaqsCategoryEditDto getFaqsCategory(Long id, boolean action);

    /**
     * Delete category FAQs
     * 
     * @param cateId
     * @author TaiTM
     */
    public void deleteById(Long cateEnId);

    /**
     * findById
     *
     * @param id
     * @return FaqsCategory
     * @author TaiTM
     */
    public FaqsCategory findById(Long id);

    /**
     * delete FaqsCategory by typeId
     *
     * @param typeId
     * @author TaiTM
     */
    public void deleteByTypeId(Long typeId);

    /**
     * find FaqsCategory by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    public FaqsCategory findByCode(String code);

    /**
     * add Or Edit FaqsCategory
     *
     * @param categoryEditDto
     * @author TaiTM
     */
    public void addOrEdit(FaqsCategoryEditDto categoryEditDto);

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author TaiTM
     */
    void updateSortAll(FaqsCategorySearchDto searchDto);

    public List<FaqsCategoryDto> findAllFaqsCategoryByCustomerId(String lang, Long customerId);

    public FaqsCategoryLanguage findByAliasCustomerId(String linkAlias, String languageCode, Long customerId);

    public List<MenuNode> getListTree(String lang, Long rootId);
}
