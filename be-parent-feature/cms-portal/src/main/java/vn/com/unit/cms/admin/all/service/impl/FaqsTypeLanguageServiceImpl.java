/*******************************************************************************
 * Class        ：FaqsTypeLanguageServiceImpl
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

import vn.com.unit.cms.admin.all.entity.FaqsTypeLanguage;
import vn.com.unit.cms.admin.all.repository.FaqsLanguageRepository;
import vn.com.unit.cms.admin.all.repository.FaqsTypeLanguageRepository;
import vn.com.unit.cms.admin.all.service.FaqsTypeLanguageService;

/**
 * FaqsTypeLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FaqsTypeLanguageServiceImpl implements FaqsTypeLanguageService {

    @Autowired
    FaqsLanguageRepository faqsLangRep;

    @Autowired
    private FaqsTypeLanguageRepository typeLanguageRepository;

    /**
     * find list FaqsTypeLanguage by tyepId
     *
     * @param typeId
     * @return List<FaqsTypeLanguage>
     * @author hand
     */
    @Override
    public List<FaqsTypeLanguage> findByTypeId(Long tyepId) {
        return typeLanguageRepository.findByTypeId(tyepId);
    }

    /**
     * find FaqsTypeLanguage by id
     * 
     * @param id
     * @return FaqsTypeLanguage
     * @author hand
     */
    @Override
    public FaqsTypeLanguage findById(Long id) {
        return typeLanguageRepository.findOne(id);
    }

    /**
     * saveFaqsTypeLanguage
     *
     * @param entity
     * @author hand
     */
    @Override
    public void saveFaqsTypeLanguage(FaqsTypeLanguage entity) {
        typeLanguageRepository.save(entity);
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
    public void deleteByTypeId(Long faqsId, String deleteBy) {
        typeLanguageRepository.deleteByTypeId(faqsId, deleteBy, new Date());
    }

    /**
     * find FaqsTypeLanguage by typeId and languageCode
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author hand
     */              
    @Override
    public FaqsTypeLanguage findByTypeIdAndLanguage(Long typeId, String languageCode) {
        return typeLanguageRepository.findByTypeIdAndLanguageCode(typeId, languageCode);
    }

}
