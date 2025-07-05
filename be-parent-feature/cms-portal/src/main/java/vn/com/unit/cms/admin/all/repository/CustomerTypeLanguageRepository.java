/*******************************************************************************
 * Class        ：CustomerTypeLanguageRepository
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
import vn.com.unit.cms.admin.all.entity.CustomerTypeLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * CustomerTypeLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface CustomerTypeLanguageRepository extends DbRepository<CustomerTypeLanguage, Long> {

	/**
	 * find CustomerTypeLanguageb by typeId
	 *
	 * @param customerTypeId
	 * @return
	 * @author hand
	 */
	List<CustomerTypeLanguage> findByTypeId(@Param("customerTypeId") Long customerTypeId);

	/**
	 * delete CustomerTypeLanguage by customer type id
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param customerId
	 * @author hand
	 */
	@Modifying
	public void deleteByTypeId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("typeId") Long typeId);

	/**
	 * find CustomerTypeLanguage by typeId and languageCode
	 *
	 * @param typeId
	 * @param languageCode
	 * @return
	 * @author hand
	 */
	CustomerTypeLanguage findByTypeIdAndLanguageCode(@Param("typeId") Long typeId,
			@Param("languageCode") String languageCode);

}
