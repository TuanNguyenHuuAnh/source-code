/*******************************************************************************
 * Class        ：FaqsCategoryLanguageServiceImpl
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.FaqsCategoryLanguage;
import vn.com.unit.cms.admin.all.repository.FaqsCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.service.FaqsCategoryLanguageService;

/**
 * FaqsCategoryLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FaqsCategoryLanguageServiceImpl implements FaqsCategoryLanguageService {

    @Autowired
    private FaqsCategoryLanguageRepository categoryLanguageRepository;

    /**
     * find FaqsCategoryLanguage by id
     *
     * @param id
     * @return FaqsCategoryLanguage
     * @author hand
     */
    @Override
    public FaqsCategoryLanguage findByid(Long id) {
        return categoryLanguageRepository.findOne(id);
    }

    /**
     * save FaqsCategoryLanguage entity
     *
     * @param entity
     *            FaqsCategoryLanguage
     * @author hand
     */
    @Override
    @Transactional
    public void saveFaqsCategoryLanguage(FaqsCategoryLanguage entity) {
        categoryLanguageRepository.save(entity);
    }

    /**
     * find FaqsCategoryLanguageb by CategoryId
     *
     * @param mFaqsCategoryId
     * @return
     * @author hand
     */
    @Override
    public List<FaqsCategoryLanguage> findByCategoryId(Long mFaqsCategoryId) {
        return categoryLanguageRepository.findByCategoryId(mFaqsCategoryId);
    }

    /**
     * delete FawsCategoryLanguage by category id
     *
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    @Override
    @Transactional
    public void deleteByCategoryId(Long categoryId, String deleteBy) {
        categoryLanguageRepository.deleteByCategoryId(new Date(), deleteBy, categoryId);

    }
    
    /**
     * find FaqsCategoryLanguageb by CategoryId and languageCode
     *
     * @param mFaqsCategoryId
     * @param languageCode
     * @return
     * @author hand
     */
    @Override
    public FaqsCategoryLanguage findByCategoryIdAndLanguage(Long mFaqsCategoryId, String languageCode) {
        return categoryLanguageRepository.findByCategoryIdAndLanguageCode(mFaqsCategoryId, languageCode);
    }
    
}
