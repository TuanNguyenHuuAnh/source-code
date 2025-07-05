/*******************************************************************************
 * Class        ：IntroduceInternetBankingDetailServiceImpl
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.IntroduceInternetBankingDetail;
import vn.com.unit.cms.admin.all.repository.IntroduceInternetBankingDetailRepository;
import vn.com.unit.cms.admin.all.service.IntroduceInternetBankingDetailService;

/**
 * IntroduceInternetBankingDetailServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class IntroduceInternetBankingDetailServiceImpl implements IntroduceInternetBankingDetailService {
	
	@Autowired
	private IntroduceInternetBankingDetailRepository introduceInternetBankingDetailRepository;

    /**
     * findIntroduceInternetBankingDetailById
     *
     * @param introduceInternetBankingId
     * @return List<IntroduceInternetBankingDetail>
     * @author hoangnp
     */
	@Override
	public List<IntroduceInternetBankingDetail> findIntroduceInternetBankingDetailById(
			Long introduceInternetBankingId) {
		return introduceInternetBankingDetailRepository.findIntroduceInternetBankingDetailById(introduceInternetBankingId);
	}

	/**
     * deleteByIntroduceInternetBankingId
     *
     * @param introduceInternetBankingId
     * @author hoangnp
     */
    
	@Override
	@Transactional
	public void deleteByIntroduceInternetBankingId(Long introduceInternetBankingId) {
		introduceInternetBankingDetailRepository.deleteByIntroduceInternetBankingId(introduceInternetBankingId);
		
	}

	/**
     * saveDetail
     *
     * @param entity
     * @author hoangnp
     */
	@Override
	@Transactional
	public void saveDetail(IntroduceInternetBankingDetail entity) {
		introduceInternetBankingDetailRepository.save(entity);
		
	}
	
	/**
     * deleteByTypeId
     *
     * @param id
     * @param userName
     * @author hoangnp
     */
	@Override
	public void deleteByTypeId(Long id, String deleteBy) {
		introduceInternetBankingDetailRepository.deleteByTypeId(id, deleteBy, new Date());
		
	}
}
