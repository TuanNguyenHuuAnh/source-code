package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.entity.JobTypeSubLanguage;

public interface JobTypeSubLanguageService {
	public List<JobTypeSubLanguage> findByTypeId(Long typeId);
	 public JobTypeSubLanguage findById(Long id);
	 public void saveJobTypeSubLanguage(JobTypeSubLanguage entity);
	 public void deleteByTypeId(Long typeId, String deleteBy);
}
