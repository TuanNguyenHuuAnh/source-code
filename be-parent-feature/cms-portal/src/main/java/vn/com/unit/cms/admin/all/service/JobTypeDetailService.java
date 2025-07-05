package vn.com.unit.cms.admin.all.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.google.protobuf.TextFormat.ParseException;

import vn.com.unit.cms.admin.all.dto.JobTypeDetailDto;
import vn.com.unit.cms.admin.all.dto.JobTypeDetailSearchDto;
import vn.com.unit.common.dto.PageWrapper;

/**
 * JobTypeDetailService
 * @author hand
 *
 */
public interface JobTypeDetailService {

	/**
	 * add or edit JobTypeDetail
	 * @param jobTypeDetailDto
	 * @throws Exception
	 */
	public void addOrEdit(JobTypeDetailDto jtdDto);
	
	/**
	 * delete JobTypeDetail by id
	 * @param jobId
	 */
	public void delete(Long jobId);
	
	/**
	 * retrieve JobTypeDetailDto by id
	 * @param jtdId
	 */
	public JobTypeDetailDto getJobTypeDetailDto(Long jtdId);
	
	/**
	 * search
	 * @param page
	 * @param jobTypeDetailSearchDto
	 * @return
	 * @throws ParseException
	 */
	public PageWrapper<JobTypeDetailDto> search(int page, 
			JobTypeDetailSearchDto jtdSearchDto) throws ParseException;
	
	/**
	 * initScreenJtd
	 * @param mav
	 */
	public void initScreenJtdList(ModelAndView mav, String langCode);
	
	/**
	 * retrieve all JtdDto
	 */
	public List<JobTypeDetailDto> getAllJtdDto();
	
}
