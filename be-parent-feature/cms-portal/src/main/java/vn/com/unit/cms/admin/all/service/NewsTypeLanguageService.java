/*******************************************************************************
 * Class        ：NewsTypeLanguageService
 * Created date ：2017/03/02
 * Lasted date  ：2017/03/02
 * Author       ：hand
 * Change log   ：2017/03/02：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.NewsTypeLanguage;

/**
 * NewsTypeLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsTypeLanguageService {

    /**
     * find NewsTypeLanguage by typeId
     *
     * @param newsTypeId
     * @return List<NewsTypeLanguage>
     * @author hand
     */
    public List<NewsTypeLanguage> findByTypeId(Long newsTypeId);

    /**
     * find NewsTypeLanguage by id
     * 
     * @return NewsTypeLanguage
     * @author hand
     */
    public NewsTypeLanguage findByid(Long id);

    /**
     * save entity NewsTypeLanguage
     * 
     * @param entity
     * @author hand
     */
    public void saveNewsTypeLanguage(NewsTypeLanguage entity);

    /**
     * delete NewsTypeLanguage by news type id
     *
     * @param deleteDate
     * @param deleteBy
     * @param newsId
     * @author hand
     */
    public void deleteByTypeId(Date deleteDate, String deleteBy, Long typeId);
    
    /**
     * find NewsTypeLanguage by typeId and languageCode
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author hand
     */              
    public NewsTypeLanguage findByTypeIdAndLanguage(Long typeId, String languageCode);
}
