/*******************************************************************************
 * Class        ：FaqsCategoryService
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/13
 * Author       ：hand
 * Change log   ：2017/04/13：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;

/**
 * FaqsServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface FaqsLanguageService {

    /**
     * find FaqsLanguage by faqsId
     *
     * @param faqsId
     * @return List<FaqsLanguage>
     * @author hand
     */
    public List<FaqsLanguage> findByFaqsId(Long faqsId);
    
    /**
     * find FaqsLanguage by id
     *
     * @param id
     * @return FaqsLanguage
     * @author hand
     */
    public FaqsLanguage findById(Long id);
    
    /**
     * saveFaqsLanguage
     *
     * @param entity
     * @author hand
     */
    public void saveFaqsLanguage(FaqsLanguage entity);
    
    /**
     * delete FaqsLanguage by fawsId
     *
     * @param faqsId
     * @param deleteBy
     * @author hand
     */
    public void deleteByFawsId(Long faqsId, String deleteBy);
    
    public FaqsLanguage findByIdAndLang(Long id,String lang);
    
}
