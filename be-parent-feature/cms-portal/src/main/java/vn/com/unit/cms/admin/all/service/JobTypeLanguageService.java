package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.entity.JobTypeLanguage;

public interface JobTypeLanguageService {

	List<JobTypeLanguage> findByTypeId(Long jobId);
	
	public JobTypeLanguage findById(Long id);
	
	public void saveJobTypeLanguage(JobTypeLanguage entity);

	public void deleteByTypeId(Long id, String userName);

}
