/*******************************************************************************
 * Class        ：NewsCategoryLanguageServiceImpl
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

import vn.com.unit.cms.admin.all.entity.NewsCategoryLanguage;
import vn.com.unit.cms.admin.all.repository.NewsCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.service.NewsCategoryLanguageService;

/**
 * NewsCategoryLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NewsCategoryLanguageServiceImpl implements NewsCategoryLanguageService {

    @Autowired
    private NewsCategoryLanguageRepository categoryLanguageRepository;

    /**
     * find NewsCategoryLanguage by id
     *
     * @param id
     * @return NewsCategoryLanguage
     * @author hand
     */
    @Override
    public NewsCategoryLanguage findByid(Long id) {
        return categoryLanguageRepository.findOne(id);
    }

    /**
     * save NewsCategoryLanguage entity
     *
     * @param entity
     *            NewsCategoryLanguage
     * @author hand
     */
    @Override
    @Transactional
    public void saveNewsCategoryLanguage(NewsCategoryLanguage entity) {
        categoryLanguageRepository.save(entity);
    }

    /**
     * find NewsCategoryLanguageb by CategoryId
     *
     * @param mNewsCategoryId
     * @return
     * @author hand
     */
    @Override
    public List<NewsCategoryLanguage> findByCategoryId(Long mNewsCategoryId) {
        return categoryLanguageRepository.findByCategoryId(mNewsCategoryId);
    }

    /**
     * delete NewsCategoryLanguage by category id
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
     * find NewsCategoryLanguageb by CategoryId and languageCode
     *
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    @Override
    public NewsCategoryLanguage findByCategoryIdAndLanguage(Long categoryId, String languageCode) {
        return categoryLanguageRepository.findByCategoryIdAndLanguageCode(categoryId, languageCode);
    }
}
