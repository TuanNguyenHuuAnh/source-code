/*******************************************************************************
 * Class        ：IntroduceInternetBankingDetailRepository
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.IntroduceInternetBankingDetail;
import vn.com.unit.db.repository.DbRepository;

/**
 * IntroduceInternetBankingDetailRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public interface IntroduceInternetBankingDetailRepository extends DbRepository<IntroduceInternetBankingDetail, Long> {

	/**
	 * findIntroduceInternetBankingDetailById
	 *
	 * @param introduceInternetBankingId
	 * @return List<IntroduceInternetBankingDetail>
	 * @author hoangnp
	 */
	List<IntroduceInternetBankingDetail> findIntroduceInternetBankingDetailById(
			@Param("introduceInternetBankingId") Long introduceInternetBankingId);

	/**
	 * deleteByIntroduceInternetBankingId
	 *
	 * @param introduceInternetBankingId
	 * @author hoangnp
	 */
	@Modifying
	void deleteByIntroduceInternetBankingId(@Param("introduceInternetBankingId") Long introduceInternetBankingId);

	/**
	 * deleteByTypeId
	 *
	 * @param id
	 * @param userName
	 * @author hoangnp
	 */
	@Modifying
	public void deleteByTypeId(@Param("id") Long typeId, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

}
