/*******************************************************************************
 * Class        ：ProductCategorySubLanguageServiceImpl
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

import vn.com.unit.cms.admin.all.entity.ProductCategorySubLanguage;
import vn.com.unit.cms.admin.all.repository.ProductCategorySubLanguageRepository;
import vn.com.unit.cms.admin.all.service.ProductCategorySubLanguageService;

/**
 * ProductCategorySubLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductCategorySubLanguageServiceImpl implements ProductCategorySubLanguageService {

    @Autowired
    private ProductCategorySubLanguageRepository categorySubLanguageRepository;

    /**
     * find ProductCategorySubLanguage by id
     *
     * @param id
     * @return ProductCategorySubLanguage
     * @author hand
     */
    @Override
    public ProductCategorySubLanguage findByid(Long id) {
        return categorySubLanguageRepository.findOne(id);
    }

    /**
     * save ProductCategorySubLanguage entity
     *
     * @param entity
     *            ProductCategorySubLanguage
     * @author hand
     */
    @Override
    @Transactional
    public void saveProductCategorySubLanguage(ProductCategorySubLanguage entity) {
        categorySubLanguageRepository.save(entity);
    }

    /**
     * find ProductCategorySubLanguageb by CategorySubId
     *
     * @param mProductCategorySubId
     * @return
     * @author hand
     */
    @Override
    public List<ProductCategorySubLanguage> findByCategorySubId(Long mProductCategorySubId) {
        return categorySubLanguageRepository.findByCategorySubId(mProductCategorySubId);
    }

    /**
     * delete ProductCategorySubLanguage by category id
     *
     * @param deleteDate
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    @Override
    @Transactional
    public void deleteByCategoryId(Date deleteDate, String deleteBy, Long categoryId) {
        categorySubLanguageRepository.deleteByCategoryId(deleteDate, deleteBy, categoryId);

    }

    /**
     * find ProductCategorySubLanguageb by CategoryId and languageCode
     *
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    @Override
    public ProductCategorySubLanguage findByCategoryIdAndLanguage(Long categoryId, String languageCode) {
        return categorySubLanguageRepository.findByCategoryIdAndLanguageCode(categoryId, languageCode);
    }
}
