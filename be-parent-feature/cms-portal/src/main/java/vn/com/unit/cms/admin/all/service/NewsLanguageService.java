/*******************************************************************************
 * Class        ：NewsLanguageService
 * Created date ：2017/03/02
 * Lasted date  ：2017/03/02
 * Author       ：hand
 * Change log   ：2017/03/02：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.core.module.news.entity.NewsLanguage;

//import vn.com.unit.cms.admin.all.entity.NewsLanguage;

/**
 * NewsLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsLanguageService {

    /**
     * delete NewsLanguage by news id
     *
     * @param deleteDate
     * @param deleteBy
     * @param newsId
     * @author hand
     */
    public void deleteByNewsId(Date deleteDate, String deleteBy, Long newsId);

    /**
     * find NewsLanguage by id
     *
     * @param id
     * @return NewsLanguage
     * @author hand
     */
    public NewsLanguage findByid(Long id);
    
    /**
     * save NewsLanguage
     *
     * @param entity NewsLanguage
     * @author hand
     */
    public void saveNewsLanguage(NewsLanguage entity);
    
    /**
     * find all NewsLanguage by newsId
     *
     * @param newsId
     * @return List<NewsLanguage>
     * @author hand
     */
    public List<NewsLanguage> findByNewsId(Long newsId);
}
