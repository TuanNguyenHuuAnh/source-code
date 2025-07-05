/*******************************************************************************
 * Class        ：ProductCategorySubLanguageService
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.ProductCategorySubLanguage;

/**
 * ProductCategorySubLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductCategorySubLanguageService {

    /**
     * find ProductCategorySubLanguageb by CategorySubId
     *
     * @param productCategorySubId
     * @return
     * @author hand
     */
    public List<ProductCategorySubLanguage> findByCategorySubId(Long productCategorySubId);
    
    /**
     * find ProductCategorySubLanguage by id
     *
     * @param id
     * @return ProductCategorySubLanguage
     * @author hand
     */
    public ProductCategorySubLanguage findByid(Long id);
    
    /**
     * save ProductCategorySubLanguage entity
     *
     * @param entity ProductCategorySubLanguage
     * @author hand
     */
    public void saveProductCategorySubLanguage(ProductCategorySubLanguage entity);
    
    /**
     * delete ProductCategorySubLanguage by category id
     *
     * @param deleteDate
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    public void deleteByCategoryId(Date deleteDate, String deleteBy, Long categoryId);
    
    /**
     * find ProductCategorySubLanguageb by CategoryId and languageCode
     *
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    public ProductCategorySubLanguage findByCategoryIdAndLanguage(Long categoryId, String languageCode);
}
