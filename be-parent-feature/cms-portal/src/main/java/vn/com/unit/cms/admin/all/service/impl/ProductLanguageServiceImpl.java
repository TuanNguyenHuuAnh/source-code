/*******************************************************************************
 * Class        ：ProductLanguageServiceImpl
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.ProductLanguage;
import vn.com.unit.cms.admin.all.repository.ProductLanguageRepository;
import vn.com.unit.cms.admin.all.service.ProductLanguageService;

/**
 * ProductLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductLanguageServiceImpl implements ProductLanguageService {

    @Autowired
    ProductLanguageRepository languageRepository;

    /**
     * delete ProductLanguage by product id
     *
     * @param deleteDate
     * @param deleteBy
     * @param productId
     * @author hand
     */
    @Override
    @Transactional
    public void deleteByProductId(Date deleteDate, String deleteBy, Long productId) {
        languageRepository.deleteByProductId(deleteDate, deleteBy, productId);

    }

    /**
     * find ProductLanguage by id
     *
     * @param id
     * @return ProductLanguage
     * @author hand
     */
    @Override
    public ProductLanguage findByid(Long id) {
        return languageRepository.findOne(id);
    }

    /**
     * save ProductLanguage
     *
     * @param entity
     *            ProductLanguage
     * @author hand
     */
    @Override
    @Transactional
    public void saveProductLanguage(ProductLanguage entity) {
        languageRepository.save(entity);
    }

    /**
     * find all ProductLanguage by productId
     *
     * @param productId
     * @return List<ProductLanguage>
     * @author hand
     */
    @Override
    public List<ProductLanguage> findByProductId(Long productId) {
        return languageRepository.findByProductId(productId);
    }

}
