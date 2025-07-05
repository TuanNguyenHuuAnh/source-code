/*******************************************************************************
 * Class        ：NewsLanguageServiceImpl
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

//import vn.com.unit.cms.admin.all.entity.NewsLanguage;
import vn.com.unit.cms.admin.all.repository.NewsLanguageRepository;
import vn.com.unit.cms.admin.all.service.NewsLanguageService;
import vn.com.unit.cms.core.module.news.entity.NewsLanguage;

/**
 * NewsLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NewsLanguageServiceImpl implements NewsLanguageService {

    @Autowired
    NewsLanguageRepository languageRepository;

    /**
     * delete NewsLanguage by news id
     *
     * @param deleteDate
     * @param deleteBy
     * @param newsId
     * @author hand
     */
    @Override
    @Transactional
    public void deleteByNewsId(Date deleteDate, String deleteBy, Long newsId) {
        languageRepository.deleteByNewsId(deleteDate, deleteBy, newsId);

    }

    /**
     * find NewsLanguage by id
     *
     * @param id
     * @return NewsLanguage
     * @author hand
     */
    @Override
    public NewsLanguage findByid(Long id) {
        return languageRepository.findOne(id);
    }

    /**
     * save NewsLanguage
     *
     * @param entity NewsLanguage
     * @author hand
     */
    @Override
    @Transactional
    public void saveNewsLanguage(NewsLanguage entity) {
        languageRepository.save(entity);
    }

    /**
     * find all NewsLanguage by newsId
     *
     * @param newsId
     * @return List<NewsLanguage>
     * @author hand
     */
    @Override
    public List<NewsLanguage> findByNewsId(Long newsId) {
        return languageRepository.findByNewsId(newsId);
    }

}
