/*******************************************************************************
 * Class        ：NewsTypeLanguageServiceImpl
 * Created date ：2017/03/02
 * Lasted date  ：2017/03/02
 * Author       ：hand
 * Change log   ：2017/03/02：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.NewsTypeLanguage;
import vn.com.unit.cms.admin.all.repository.NewsTypeLanguageRepository;
import vn.com.unit.cms.admin.all.service.NewsTypeLanguageService;

/**
 * NewsTypeLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NewsTypeLanguageServiceImpl implements NewsTypeLanguageService {

    @Autowired
    private NewsTypeLanguageRepository typeLanguageRepository;

    /**
     * find NewsTypeLanguageb by typeId
     *
     * @param newsTypeId
     * @return
     * @author hand
     */
    @Override
    public List<NewsTypeLanguage> findByTypeId(Long newsTypeId) {
        return typeLanguageRepository.findByTypeId(newsTypeId);
    }

    /**
     * find NewsTypeLanguage by id
     * 
     * @return NewsTypeLanguage
     * @author hand
     */
    @Override
    public NewsTypeLanguage findByid(Long id) {
        return typeLanguageRepository.findOne(id);
    }

    /**
     * save entity NewsTypeLanguage
     * 
     * @param entity
     * @author hand
     */
    @Override
    @Transactional
    public void saveNewsTypeLanguage(NewsTypeLanguage entity) {
        typeLanguageRepository.save(entity);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.cms.admin.all.service.NewsTypeLanguageService#deleteByTypeId(java.util.Date, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional
    public void deleteByTypeId(Date deleteDate, String deleteBy, Long typeId) {
        typeLanguageRepository.deleteByTypeId(deleteDate, deleteBy, typeId);
        
    }

    /**
     * find NewsTypeLanguage by typeId and languageCode
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author hand
     */         
    @Override
    public NewsTypeLanguage findByTypeIdAndLanguage(Long typeId, String languageCode) {
        return typeLanguageRepository.findByTypeIdAndLanguageCode(typeId, languageCode);
    }

}
