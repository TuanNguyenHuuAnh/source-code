/*******************************************************************************
 * Class        ：FaqsTypeLanguageService
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/13
 * Author       ：hand
 * Change log   ：2017/04/13：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.entity.FaqsTypeLanguage;

/**
 * FaqsTypeLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface FaqsTypeLanguageService {

    /**
     * find FaqsTypeLanguage by typeId
     *
     * @param typeId
     * @return List<FaqsTypeLanguage>
     * @author hand
     */
    public List<FaqsTypeLanguage> findByTypeId(Long typeId);
    
    /**
     * find FaqsTypeLanguage by id
     *
     * @param id
     * @return FaqsTypeLanguage
     * @author hand
     */
    public FaqsTypeLanguage findById(Long id);
    
    /**
     * saveFaqsTypeLanguage
     *
     * @param entity
     * @author hand
     */
    public void saveFaqsTypeLanguage(FaqsTypeLanguage entity);
    
    /**
     * delete FaqsLanguage by typeId
     *
     * @param typeId
     * @param deleteBy
     * @author hand
     */
    public void deleteByTypeId(Long typeId, String deleteBy);
    
    /**
     * find FaqsTypeLanguage by typeId and languageCode
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author hand
     */              
    public FaqsTypeLanguage findByTypeIdAndLanguage(Long typeId, String languageCode);
}
