/*******************************************************************************
 * Class        ：FaqsLanguageRepository
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
import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * FaqsLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
public interface FaqsLanguageRepository extends DbRepository<FaqsLanguage, Long> {
	/**
	 * find FaqsLanguage by faqsId
	 *
	 * @param faqsId
	 * @return List<FaqsLanguage>
	 * @author hand
	 */
	List<FaqsLanguage> findByFaqsId(@Param("faqsId") Long faqsId);

	/**
	 * delete FaqsLanguage by fawsId
	 *
	 * @param faqsId
	 * @param deleteBy
	 * @author hand
	 */
	@Modifying
	void deleteByFawsId(@Param("faqsId") Long faqsId, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);
	
	public FaqsLanguage findByIdAndLang(@Param("faqsId") Long faqsId,@Param("lang") String lang);
}
