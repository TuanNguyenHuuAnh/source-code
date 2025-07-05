package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.admin.all.dto.JobTypeEditDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.JobType;
import vn.com.unit.common.dto.PageWrapper;

public interface JobTypeService {
	
	public JobTypeEditDto findJobType(Long id);
	
	public void addOrEdit(JobTypeEditDto editDto);

	public PageWrapper<JobTypeSearchDto> findJobTypeList(JobTypeSearchDto condition, int page);

	public JobType findJobTypeByCode(String code);

	public void deleteJobType(Long id);

	
}
