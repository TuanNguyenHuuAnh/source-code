package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.JobTypeLanguage;
import vn.com.unit.cms.admin.all.repository.JobTypeLanguageRepository;
import vn.com.unit.cms.admin.all.service.JobTypeLanguageService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JobTypeLanguageServiceImpl implements JobTypeLanguageService {

	@Autowired
	JobTypeLanguageRepository jobTypeLanguageRep;
	
	@Override
	public List<JobTypeLanguage> findByTypeId(Long jobId) {
		return jobTypeLanguageRep.findByTypeId(jobId);
	}

	@Override
	public JobTypeLanguage findById(Long id) {
		return jobTypeLanguageRep.findOne(id);
	}

	@Override
	public void saveJobTypeLanguage(JobTypeLanguage entity) {
		jobTypeLanguageRep.save(entity);
		
	}

	@Override
	@Transactional
	public void deleteByTypeId(Long id, String userName) {
		jobTypeLanguageRep.deleteByTypeId(id, userName, new Date());
		
	}

}
