/*******************************************************************************
 * Class        ：NewsLanguageRepository
 * Created date ：2017/03/02
 * Lasted date  ：2017/03/02
 * Author       ：hand
 * Change log   ：2017/03/02：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.news.entity.NewsLanguage;
//import vn.com.unit.cms.admin.all.entity.NewsLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * NewsLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsLanguageRepository extends DbRepository<NewsLanguage, Long> {

	/**
	 * delete NewsLanguage by news id
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param newsId
	 * @author hand
	 */
	@Modifying
	public void deleteByNewsId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("newsId") Long newsId);

	/**
	 * find all NewsLanguage by newsId
	 *
	 * @param newsId
	 * @return List<NewsLanguage>
	 * @author hand
	 */
	List<NewsLanguage> findByNewsId(@Param("newsId") Long newsId);

}
