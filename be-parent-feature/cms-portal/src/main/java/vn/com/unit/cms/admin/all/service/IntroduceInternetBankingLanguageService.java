/*******************************************************************************
 * Class        ：IntroduceInternetBankingLanguageService
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.entity.IntroduceInternetBankingLanguage;

/**
 * IntroduceInternetBankingLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public interface IntroduceInternetBankingLanguageService {

    /**
     * findByIntroduceInternetBankingId
     *
     * @param introduceInternetBankingId
     * @return List<IntroduceInternetBankingLanguage>
     * @author hoangnp
     */
	public List<IntroduceInternetBankingLanguage> findByIntroduceInternetBankingId(Long introduceInternetBankingId);

	/**
	 * findById
	 *
	 * @param id
	 * @return IntroduceInternetBankingLanguage
	 * @author hoangnp
	 */
	public IntroduceInternetBankingLanguage findById(Long id);

	/**
	 * saveIntroduceInternetBankingLanguage
	 *
	 * @param entity
	 * @author hoangnp
	 */
	public void saveIntroduceInternetBankingLanguage(IntroduceInternetBankingLanguage entity);

	/**
	 * deleteByTypeId
	 *
	 * @param id
	 * @param userName
	 * @author hoangnp
	 */
	void deleteByTypeId(Long id, String userName);
}
