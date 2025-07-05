/*******************************************************************************
 * Class        ：FaqsServiceImpl
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/13
 * Author       ：hand
 * Change log   ：2017/04/13：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.repository.FaqsLanguageRepository;
import vn.com.unit.cms.admin.all.service.FaqsLanguageService;
import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;

/**
 * FaqsServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FaqsLanguageServiceImpl implements FaqsLanguageService {

    @Autowired
    FaqsLanguageRepository faqsLangRep;

    /**
     * find FaqsLanguage by faqsId
     *
     * @param faqsId
     * @return List<FaqsLanguage>
     * @author hand
     */
    @Override
    public List<FaqsLanguage> findByFaqsId(Long faqsId) {
        return faqsLangRep.findByFaqsId(faqsId);
    }

    /**
     * find FaqsLanguage by id
     * 
     * @param id
     * @return FaqsLanguage
     * @author hand
     */
    @Override
    public FaqsLanguage findById(Long id) {
        return faqsLangRep.findOne(id);
    }

    /**
     * saveFaqsLanguage
     *
     * @param entity
     * @author hand
     */
    @Override
    public void saveFaqsLanguage(FaqsLanguage entity) {
        faqsLangRep.save(entity);
    }

    /**
     * delete FaqsLanguage by fawsId
     *
     * @param faqsId
     * @param deleteBy
     * @author hand
     */
    @Override
    @Transactional
    public void deleteByFawsId(Long faqsId, String deleteBy) {
        faqsLangRep.deleteByFawsId(faqsId, deleteBy, new Date());
    }

    @Override
    public FaqsLanguage findByIdAndLang(Long id, String lang) {
        return faqsLangRep.findByIdAndLang(id,lang);
    }

}
