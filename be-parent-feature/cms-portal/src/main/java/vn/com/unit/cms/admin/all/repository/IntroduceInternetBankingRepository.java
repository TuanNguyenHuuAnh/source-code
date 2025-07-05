/*******************************************************************************
 * Class        ：IntroduceInternetBankingRepository
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingSearchDto;
import vn.com.unit.cms.admin.all.entity.IntroduceInternetBanking;
import vn.com.unit.db.repository.DbRepository;

/**
 * IntroduceInternetBankingRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public interface IntroduceInternetBankingRepository extends DbRepository<IntroduceInternetBanking, Long> {

	/**
	 * findByCode
	 *
	 * @param code
	 * @return IntroduceInternetBanking
	 * @author hoangnp
	 */
	IntroduceInternetBanking findByCode(@Param("code") String code);

	/**
	 * search
	 *
	 * 
	 * @param introduceInternetBankingSearchDto
	 * @return int
	 * @author hoangnp
	 */
	int countByIntroduceInternetBankingSearchDto(@Param("searchCond") IntroduceInternetBankingSearchDto searchDto);

	/**
	 * search
	 *
	 * @param page
	 * @param introduceInternetBankingSearchDto
	 * @return PageWrapper<IntroduceInternetBankingLanguageSearchDto>
	 * @author hoangnp
	 */
	List<IntroduceInternetBankingLanguageSearchDto> findByIntroduceInternetBankingSearchDto(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchCond") IntroduceInternetBankingSearchDto searchDto);

}
