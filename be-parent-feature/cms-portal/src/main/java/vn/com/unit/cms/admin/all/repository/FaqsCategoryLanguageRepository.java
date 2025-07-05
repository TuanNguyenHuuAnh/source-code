/*******************************************************************************
 * Class        ：FaqsCategoryLanguageRepository
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：vinhnht
 * Change log   ：2017/02/28：01-00 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * FaqsCategoryLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
public interface FaqsCategoryLanguageRepository extends DbRepository<FaqsCategoryLanguage, Long> {
	/**
	 * find FaqsCategoryLanguageb by CategoryId
	 *
	 * @param categoryId
	 * @return
	 * @author hand
	 */
	List<FaqsCategoryLanguage> findByCategoryId(@Param("categoryId") Long categoryId);

	/**
	 * delete FaqsCategoryLanguage by category id
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param categoryId
	 * @author hand
	 */
	@Modifying
	void deleteByCategoryId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("categoryId") Long categoryId);

	/**
	 * find FaqsCategoryLanguageb by CategoryId and languageCode
	 *
	 * @param mFaqsCategoryId
	 * @param languageCode
	 * @return
	 * @author hand
	 */
	FaqsCategoryLanguage findByCategoryIdAndLanguageCode(@Param("categoryId") Long mFaqsCategoryId,
			@Param("languageCode") String languageCode);

}
