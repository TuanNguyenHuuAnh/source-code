/*******************************************************************************
 * Class        ：NewsCategoryLanguageService
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.NewsCategoryLanguage;

/**
 * NewsCategoryLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsCategoryLanguageService {
    
    /**
     * find NewsCategoryLanguageb by CategoryId
     *
     * @param mNewsCategoryId
     * @return
     * @author hand
     */
    public List<NewsCategoryLanguage> findByCategoryId(Long mNewsCategoryId);
    
    /**
     * find NewsCategoryLanguage by id
     *
     * @param id
     * @return NewsCategoryLanguage
     * @author hand
     */
    public NewsCategoryLanguage findByid(Long id);
    
    /**
     * save NewsCategoryLanguage entity
     *
     * @param entity NewsCategoryLanguage
     * @author hand
     */
    public void saveNewsCategoryLanguage(NewsCategoryLanguage entity);
    
    /**
     * delete NewsCategoryLanguage by category id
     *
     * @param deleteDate
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    public void deleteByCategoryId(Date deleteDate, String deleteBy, Long categoryId);
    
    /**
     * find NewsCategoryLanguageb by CategoryId and languageCode
     *
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    public NewsCategoryLanguage findByCategoryIdAndLanguage(Long categoryId, String languageCode);

}
