/*******************************************************************************
 * Class        ：IntroduceInternetBankingLanguageServiceImpl
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.IntroduceInternetBankingLanguage;
import vn.com.unit.cms.admin.all.repository.IntroduceInternetBankingLanguageRepository;
import vn.com.unit.cms.admin.all.service.IntroduceInternetBankingLanguageService;

/**
 * IntroduceInternetBankingLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class IntroduceInternetBankingLanguageServiceImpl implements IntroduceInternetBankingLanguageService  {
	
	@Autowired
	private IntroduceInternetBankingLanguageRepository introduceInternetBankingLanguageRepository;

	/**
     * findByIntroduceInternetBankingId
     *
     * @param introduceInternetBankingId
     * @return List<IntroduceInternetBankingLanguage>
     * @author hoangnp
     */
	@Override
	public List<IntroduceInternetBankingLanguage> findByIntroduceInternetBankingId(Long introduceInternetBankingId) {
		return introduceInternetBankingLanguageRepository.findByIntroduceInternetBankingId(introduceInternetBankingId);
	}

	/**
     * findById
     *
     * @param id
     * @return IntroduceInternetBankingLanguage
     * @author hoangnp
     */
	@Override
	public IntroduceInternetBankingLanguage findById(Long id) {
		return introduceInternetBankingLanguageRepository.findOne(id);
	}

	/**
     * saveIntroduceInternetBankingLanguage
     *
     * @param entity
     * @author hoangnp
     */
	@Override
	@Transactional
	public void saveIntroduceInternetBankingLanguage(IntroduceInternetBankingLanguage entity) {
		introduceInternetBankingLanguageRepository.save(entity);
		
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
		introduceInternetBankingLanguageRepository.deleteByTypeId(id, deleteBy, new Date());
		
	}
	
}
