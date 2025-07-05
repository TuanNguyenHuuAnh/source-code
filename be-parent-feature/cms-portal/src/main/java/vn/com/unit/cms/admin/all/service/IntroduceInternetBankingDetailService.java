/*******************************************************************************
 * Class        ：IntroduceInternetBankingDetailService
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.entity.IntroduceInternetBankingDetail;

/**
 * IntroduceInternetBankingDetailService
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public interface IntroduceInternetBankingDetailService {

    /**
     * findIntroduceInternetBankingDetailById
     *
     * @param introduceInternetBankingId
     * @return List<IntroduceInternetBankingDetail>
     * @author hoangnp
     */
	public List<IntroduceInternetBankingDetail> findIntroduceInternetBankingDetailById(Long introduceInternetBankingId);

	/**
	 * deleteByIntroduceInternetBankingId
	 *
	 * @param introduceInternetBankingId
	 * @author hoangnp
	 */
	
	public void deleteByIntroduceInternetBankingId(Long introduceInternetBankingId);

	/**
	 * saveDetail
	 *
	 * @param entity
	 * @author hoangnp
	 */
	public void saveDetail(IntroduceInternetBankingDetail entity);
	
	/**
	 * deleteByTypeId
	 *
	 * @param id
	 * @param userName
	 * @author hoangnp
	 */
	void deleteByTypeId(Long id, String userName);

}
