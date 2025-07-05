/*******************************************************************************
 * Class        ：ProductLanguageService
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.ProductLanguage;

/**
 * ProductLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductLanguageService {

    /**
     * delete ProductLanguage by product id
     *
     * @param deleteDate
     * @param deleteBy
     * @param productId
     * @author hand
     */
    public void deleteByProductId(Date deleteDate, String deleteBy, Long productId);

    /**
     * find ProductLanguage by id
     *
     * @param id
     * @return ProductLanguage
     * @author hand
     */
    public ProductLanguage findByid(Long id);

    /**
     * save ProductLanguage
     *
     * @param entity
     *            ProductLanguage
     * @author hand
     */
    public void saveProductLanguage(ProductLanguage entity);

    /**
     * find all ProductLanguage by productId
     *
     * @param productId
     * @return List<ProductLanguage>
     * @author hand
     */
    public List<ProductLanguage> findByProductId(Long productId);
}
