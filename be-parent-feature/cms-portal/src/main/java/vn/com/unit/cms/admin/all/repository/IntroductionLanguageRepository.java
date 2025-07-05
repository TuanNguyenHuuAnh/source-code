/*******************************************************************************
 * Class        ：IntroduceManagementRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.IntroductionLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface IntroductionLanguageRepository extends DbRepository<IntroductionLanguage, Long> {

	public List<IntroductionLanguage> findByIntroductionId(@Param("introId") Long introductionId);

	@Modifying
	public int updateDeleteFields(@Param("id") long id, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

	public String findTitleByIntroductionIdAndLanguageCode(@Param("introductionId") Long introductionId,
			@Param("languageCode") String languageCode);

}
