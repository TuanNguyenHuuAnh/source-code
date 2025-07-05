/*******************************************************************************
 * Class        ：ProductCategoryLanguageService
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.ProductCategoryLanguage;

/**
 * ProductCategoryLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductCategoryLanguageService {

    /**
     * find ProductCategoryLanguageb by CategoryId
     *
     * @param productCategoryId
     * @return
     * @author hand
     */
    public List<ProductCategoryLanguage> findByCategoryId(Long productCategoryId);
    
    /**
     * find ProductCategoryLanguage by id
     *
     * @param id
     * @return ProductCategoryLanguage
     * @author hand
     */
    public ProductCategoryLanguage findByid(Long id);
    
    /**
     * save ProductCategoryLanguage entity
     *
     * @param entity ProductCategoryLanguage
     * @author hand
     */
    public void saveProductCategoryLanguage(ProductCategoryLanguage entity);
    
    /**
     * delete ProductCategoryLanguage by category id
     *
     * @param deleteDate
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    public void deleteByCategoryId(Date deleteDate, String deleteBy, Long categoryId);
    
    /**
     * find ProductCategoryLanguageb by CategoryId and languageCode
     *
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    public ProductCategoryLanguage findByCategoryIdAndLanguage(Long categoryId, String languageCode);
}
