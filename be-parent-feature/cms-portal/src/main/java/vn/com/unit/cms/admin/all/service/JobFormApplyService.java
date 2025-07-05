/**
 * 
 */
package vn.com.unit.cms.admin.all.service;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.JobFormApllySearchDto;
import vn.com.unit.cms.admin.all.dto.JobFormApplyEditDto;
import vn.com.unit.common.dto.PageWrapper;


public interface JobFormApplyService {

	public PageWrapper<JobFormApllySearchDto> findJobFormApplyList(JobFormApllySearchDto condition, int page);

	public JobFormApplyEditDto findJobFormApply(Long jobFormApplyId);

	public void initScreenTypeList(ModelAndView mav);

	public void editJobFormApply(JobFormApplyEditDto condition);

}
