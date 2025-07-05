/*******************************************************************************
 * Class        ：ProductCategoryLanguageServiceImpl
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.ProductCategoryLanguage;
import vn.com.unit.cms.admin.all.repository.ProductCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.service.ProductCategoryLanguageService;

/**
 * ProductCategoryLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductCategoryLanguageServiceImpl implements ProductCategoryLanguageService {

    @Autowired
    private ProductCategoryLanguageRepository categoryLanguageRepository;

    /**
     * find ProductCategoryLanguage by id
     *
     * @param id
     * @return ProductCategoryLanguage
     * @author hand
     */
    @Override
    public ProductCategoryLanguage findByid(Long id) {
        return categoryLanguageRepository.findOne(id);
    }

    /**
     * save ProductCategoryLanguage entity
     *
     * @param entity
     *            ProductCategoryLanguage
     * @author hand
     */
    @Override
    @Transactional
    public void saveProductCategoryLanguage(ProductCategoryLanguage entity) {
        categoryLanguageRepository.save(entity);
    }

    /**
     * find ProductCategoryLanguageb by CategoryId
     *
     * @param mProductCategoryId
     * @return
     * @author hand
     */
    @Override
    public List<ProductCategoryLanguage> findByCategoryId(Long mProductCategoryId) {
        return categoryLanguageRepository.findByCategoryId(mProductCategoryId);
    }

    /**
     * delete ProductCategoryLanguage by category id
     *
     * @param deleteDate
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    @Override
    @Transactional
    public void deleteByCategoryId(Date deleteDate, String deleteBy, Long categoryId) {
        categoryLanguageRepository.deleteByCategoryId(deleteDate, deleteBy, categoryId);

    }

    /**
     * find ProductCategoryLanguageb by CategoryId and languageCode
     *
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    @Override
    public ProductCategoryLanguage findByCategoryIdAndLanguage(Long categoryId, String languageCode) {
        return categoryLanguageRepository.findByCategoryIdAndLanguageCode(categoryId, languageCode);
    }
}
