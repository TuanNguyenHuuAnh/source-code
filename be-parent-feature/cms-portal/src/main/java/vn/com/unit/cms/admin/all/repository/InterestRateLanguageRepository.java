/*******************************************************************************
 * Class        ：NewsLanguageRepository
 * Created date ：2017/03/02
 * Lasted date  ：2017/03/02
 * Author       ：hand
 * Change log   ：2017/03/02：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.InterestRateLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * InterestRateLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
public interface InterestRateLanguageRepository extends DbRepository<InterestRateLanguage, Long> {

	/**
	 * find all NewsLanguage by newsId
	 *
	 * @param interestRateId
	 * @return List<InterestRateLanguage>
	 * @author sonnt
	 */
	List<InterestRateLanguage> findByInterestRateId(@Param("interestRateId") Long interestRateId);

}
