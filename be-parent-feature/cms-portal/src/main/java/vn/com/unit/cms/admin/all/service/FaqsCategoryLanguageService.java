/*******************************************************************************
 * Class        ：FaqsCategoryLanguageService
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.entity.FaqsCategoryLanguage;

/**
 * FaqsCategoryLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface FaqsCategoryLanguageService {

    /**
     * find FaqsCategoryLanguageb by CategoryId
     *
     * @param mFaqsCategoryId
     * @return
     * @author hand
     */
    public List<FaqsCategoryLanguage> findByCategoryId(Long mFaqsCategoryId);

    /**
     * find FaqsCategoryLanguage by id
     *
     * @param id
     * @return FaqsCategoryLanguage
     * @author hand
     */
    public FaqsCategoryLanguage findByid(Long id);

    /**
     * save FaqsCategoryLanguage entity
     *
     * @param entity
     *            FaqsCategoryLanguage
     * @author hand
     */
    public void saveFaqsCategoryLanguage(FaqsCategoryLanguage entity);

    /**
     * delete FaqsCategoryLanguage by category id
     *
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    public void deleteByCategoryId(Long categoryId, String deleteBy);

    /**
     * find FaqsCategoryLanguageb by CategoryId and languageCode
     *
     * @param mFaqsCategoryId
     * @param languageCode
     * @return
     * @author hand
     */
    public FaqsCategoryLanguage findByCategoryIdAndLanguage(Long mFaqsCategoryId, String languageCode);

}
