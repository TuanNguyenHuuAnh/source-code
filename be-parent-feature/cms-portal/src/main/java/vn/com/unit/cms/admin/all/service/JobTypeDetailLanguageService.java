package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.JobTypeDetailDto;
import vn.com.unit.cms.admin.all.dto.JobTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubLanguageDto;

public interface JobTypeDetailLanguageService {
	
	/**
	 * helper function for getJobTypeDetailDto
	 * set JtLanguageDtoList to jtdDto
	 * @param jtdId
	 */
	public void setJtLanguageDtoList(Long jtdId, JobTypeDetailDto jtdDto);
	
	/**
	 * helper function for getJobTypeDetailDto
	 * set JtsLanguageDtoList to jtdDto
	 * @param jtdId
	 */
	public void setJtsLanguageDtoList(Long jtdId, JobTypeDetailDto jtdDto);
	
	/**
	 * helper function for getJobTypeDetailDto
	 * set JtdLanguageDtoList to jtdDto
	 * retrieve List<JobTypeDetailLanguageDto> by jtdId
	 * @param jtdId
	 */
	public void setJtdLanguageDtoList(Long jtdId, JobTypeDetailDto jtdDto);
	
	/**
	 * get all JtLanguageDto based on language code
	 * @param langCode
	 * @return
	 */
	public List<JobTypeLanguageDto> getAllJtLanguageDtoByLangCode(String langCode);
	
	/**
	 * get all JtsLanguageDto based on language code
	 * @param langCode
	 * @return
	 */
	public List<JobTypeSubLanguageDto> getAllJtsLanguageDtoByLangCode(String langCode);
}
