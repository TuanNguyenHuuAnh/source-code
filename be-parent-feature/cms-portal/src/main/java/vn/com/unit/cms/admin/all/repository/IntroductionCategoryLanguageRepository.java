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
import vn.com.unit.cms.admin.all.entity.IntroductionCategoryLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface IntroductionCategoryLanguageRepository extends DbRepository<IntroductionCategoryLanguage, Long> {

	public List<IntroductionCategoryLanguage> findByCategoryId(@Param("cateId") Long categoryId);

	@Modifying
	public int updateDeleteFields(@Param("id") long id, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

}
