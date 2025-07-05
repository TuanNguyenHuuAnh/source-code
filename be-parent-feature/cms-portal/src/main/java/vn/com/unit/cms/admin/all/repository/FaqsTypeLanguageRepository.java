/*******************************************************************************
 * Class        ：FaqsTypeRepository
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
import vn.com.unit.cms.admin.all.entity.FaqsTypeLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * FaqsTypeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
public interface FaqsTypeLanguageRepository extends DbRepository<FaqsTypeLanguage, Long> {
	/**
	 * find FaqsTypeLanguage by typeId
	 *
	 * @param typeId
	 * @return List<FaqsTypeLanguage>
	 * @author hand
	 */
	List<FaqsTypeLanguage> findByTypeId(@Param("typeId") Long typeId);

	/**
	 * delete FaqsTypeLanguage by faqs type id
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param faqsId
	 * @author hand
	 */
	@Modifying
	public void deleteByTypeId(@Param("typeId") Long typeId, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

	/**
	 * find FaqsTypeLanguage by typeId and languageCode
	 *
	 * @param typeId
	 * @param languageCode
	 * @return
	 * @author hand
	 */
	FaqsTypeLanguage findByTypeIdAndLanguageCode(@Param("typeId") Long typeId,
			@Param("languageCode") String languageCode);

}
