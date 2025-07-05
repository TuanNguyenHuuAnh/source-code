/*******************************************************************************
 * Class        ：JobTypeSubLanguageServiceImpl
 * Created date ：2017/08/10
 * Lasted date  ：2017/08/10
 * Author       ：ToanNT
 * Change log   ：2017/08/10：01-00 ToanNT create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.JobTypeSubLanguage;
import vn.com.unit.cms.admin.all.repository.JobTypeSubLanguageRepository;
import vn.com.unit.cms.admin.all.service.JobTypeSubLanguageService;
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JobTypeSubLanguageServiceImpl implements JobTypeSubLanguageService{
	
	@Autowired 
	private JobTypeSubLanguageRepository typeSubLanguageRepository;
	
	@Override
	 public List<JobTypeSubLanguage> findByTypeId(Long typeId){
		 return typeSubLanguageRepository.findByTypeId(typeId);
	 }
	@Override
	 public JobTypeSubLanguage findById(Long id){
		return typeSubLanguageRepository.findOne(id);
	}
	@Override
	 public void saveJobTypeSubLanguage(JobTypeSubLanguage entity){
		 
		typeSubLanguageRepository.save(entity);
	 }
	@Override
	@Transactional
	public void deleteByTypeId(Long typeSubId, String deleteBy) {
		typeSubLanguageRepository.deleteByTypeId(typeSubId, deleteBy, new Date());
	}
}
