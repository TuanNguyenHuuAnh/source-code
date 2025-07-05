/*******************************************************************************
 * Class        ：ProductCategorySubLanguageRepository
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.ProductCategorySubLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * ProductCategorySubLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductCategorySubLanguageRepository extends DbRepository<ProductCategorySubLanguage, Long> {

	/**
	 * find ProductCategorySubLanguageb by CategoryId
	 *
	 * @param mProductCategorySubId
	 * @return
	 * @author hand
	 */
	List<ProductCategorySubLanguage> findByCategorySubId(@Param("productCategorySubId") Long mProductCategorySubId);

	/**
	 * delete ProductCategorySubLanguage by category id
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param categoryId
	 * @author hand
	 */
	@Modifying
	public void deleteByCategoryId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("categoryId") Long categoryId);

	/**
	 * find ProductCategorySubLanguageb by CategoryId and languageCode
	 *
	 * @param categoryId
	 * @param languageCode
	 * @return
	 * @author hand
	 */
	ProductCategorySubLanguage findByCategoryIdAndLanguageCode(@Param("categorySubId") Long categoryId,
			@Param("languageCode") String languageCode);

}
