/*******************************************************************************
 * Class        ：NewsTypeLanguageRepository
 * Created date ：2017/03/01
 * Lasted date  ：2017/03/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.NewsTypeLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * NewsTypeLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsTypeLanguageRepository extends DbRepository<NewsTypeLanguage, Long> {

	/**
	 * find NewsTypeLanguageb by typeId
	 *
	 * @param newsTypeId
	 * @return
	 * @author hand
	 */
	List<NewsTypeLanguage> findByTypeId(@Param("newsTypeId") Long newsTypeId);

	/**
	 * delete NewsTypeLanguage by news type id
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param newsId
	 * @author hand
	 */
	@Modifying
	public void deleteByTypeId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("typeId") Long typeId);

	/**
	 * find NewsTypeLanguage by typeId and languageCode
	 *
	 * @param typeId
	 * @param languageCode
	 * @return
	 * @author hand
	 */
	NewsTypeLanguage findByTypeIdAndLanguageCode(@Param("typeId") Long typeId,
			@Param("languageCode") String languageCode);

}
