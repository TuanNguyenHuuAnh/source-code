/*******************************************************************************
 * Class        ：NewsCategoryService
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：TaiTM
 * Change log   ：2017/02/27：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.NewsCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.entity.NewsCategory;
import vn.com.unit.cms.admin.all.entity.NewsCategoryLanguage;

/**
 * NewsCategoryService
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface NewsCategoryService
        extends CmsCommonSearchFillterService<NewsCategorySearchDto, NewsCategorySearchResultDto, NewsCategoryEditDto> {
    /**
     * add Or Edit NewsCategory
     *
     * @param categoryEditDto
     * @author TaiTM
     */
    public void addOrEdit(NewsCategoryEditDto categoryEditDto);

    /**
     * find all NewsCategory not delete
     * 
     * @param typeId
     * @param languageCode
     * @return List<NewsCategoryDto>
     * @author TaiTM
     */
    public List<NewsCategoryDto> findByTypeIdAndLanguageCode(Long typeId, String languageCode);

    /**
     * find NewsCategory by typeId
     *
     * @return List<NewsCategory>
     * @author TaiTM
     */
    public List<NewsCategory> findByTypeId(Long typeId);

    /**
     * find NewsCategory by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    public NewsCategory findByCode(String code);

    public List<NewsCategoryEditDto> getNewsCategoryByCustomerIdAndTypeId(Long customerId, Long typeId, String lang);

    public String getBeforeIdSelectJsonByTypeId(Long customerId, Long typeId, Long categoryId, String lang);

    public NewsCategoryLanguage findByAliasTypeId(String linkAlias, String languageCode, Long customerId, Long typeId);
}
