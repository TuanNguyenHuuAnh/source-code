package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.admin.all.dto.JobTypeSubEditDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubSearchDto;
import vn.com.unit.cms.admin.all.entity.JobTypeSub;
import vn.com.unit.common.dto.PageWrapper;

public interface JobTypeSubService {
	public JobTypeSubEditDto findTypeSub(Long id, String Lang);

	public void addOrEdit(JobTypeSubEditDto editDto);

	public PageWrapper<JobTypeSubSearchDto> findJobTypeSubList(JobTypeSubSearchDto conditon, int page);

	public void deleteJobTypeSub(Long id);
	
	public JobTypeSub findJobTypeSubByCode(String code);
}
