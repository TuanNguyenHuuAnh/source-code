package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.JobTypeDetailDto;
import vn.com.unit.cms.admin.all.dto.JobTypeDetailLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubLanguageDto;
import vn.com.unit.cms.admin.all.entity.JobTypeDetail;
import vn.com.unit.cms.admin.all.entity.JobTypeDetailLanguage;
import vn.com.unit.cms.admin.all.entity.JobTypeLanguage;
import vn.com.unit.cms.admin.all.entity.JobTypeSubLanguage;
import vn.com.unit.cms.admin.all.repository.JobTypeDetailLanguageRepository;
import vn.com.unit.cms.admin.all.repository.JobTypeDetailRepository;
import vn.com.unit.cms.admin.all.repository.JobTypeLanguageRepository;
import vn.com.unit.cms.admin.all.repository.JobTypeSubLanguageRepository;
import vn.com.unit.cms.admin.all.service.JobTypeDetailLanguageService;
import vn.com.unit.cms.core.utils.CmsUtils;
//import vn.com.unit.util.Util;

@Service
@Transactional(rollbackFor = Exception.class)
public class JobTypeDetailLanguageServiceImpl implements JobTypeDetailLanguageService{

	@Autowired
	private JobTypeDetailLanguageRepository jtdLanguageRepository;
	
	@Autowired
	private JobTypeDetailRepository jtdRepository;
	
	@Autowired
	private JobTypeLanguageRepository jtLanguageRepository;
	
	@Autowired
	private JobTypeSubLanguageRepository jtsLanguageRepository;
	
	/**
	 * getJtLanguageDtoList
	 */
	@Override
	public void setJtLanguageDtoList(Long jtdId, JobTypeDetailDto jtdDto) {
		JobTypeDetail jtd = jtdRepository.findOne(jtdId);
		Long jtdTypeId = jtd.getmTypeId();
		
		List<JobTypeLanguageDto> outputList = new ArrayList<>();
		List<JobTypeLanguage> listJtLanguage = jtLanguageRepository.findByTypeId(jtdTypeId);
		
		for (JobTypeLanguage jtLanguage: listJtLanguage) {
			JobTypeLanguageDto jtLanguageDto = new JobTypeLanguageDto();
			
			jtLanguageDto.setId(jtLanguage.getId());
			jtLanguageDto.setLanguageCode(jtLanguage.getLanguageCode());
			jtLanguageDto.setName(jtLanguage.getName());
			jtLanguageDto.setTypeId(jtLanguage.getTypeId());
			
			outputList.add(jtLanguageDto);
		}
		
		jtdDto.setJtLanguageDto(outputList);
	}
	
	/**
	 * Implement getJtsLanguageDtoList
	 */
	@Override
	public void setJtsLanguageDtoList(Long jtdId, JobTypeDetailDto jtdDto) {
		JobTypeDetail jtd = jtdRepository.findOne(jtdId);
		Long jtdTypeSubId = jtd.getmTypeSubId();
		
		List<JobTypeSubLanguageDto> outputList = new ArrayList<>();
		List<JobTypeSubLanguage> listJtsLanguage = jtsLanguageRepository.findByTypeId(jtdTypeSubId);
		
		for (JobTypeSubLanguage jtsLanguage: listJtsLanguage) {
			JobTypeSubLanguageDto jtsLanguageDto = new JobTypeSubLanguageDto();
			
			jtsLanguageDto.setId(jtsLanguage.getId());
			jtsLanguageDto.setM_language_code(jtsLanguage.getM_language_code());
			jtsLanguageDto.setM_type_sub_id(jtsLanguage.getM_type_sub_id());
			jtsLanguageDto.setName(jtsLanguage.getName());
			
			outputList.add(jtsLanguageDto);
		}
		
		jtdDto.setJtsLanguageDto(outputList);
	}
	
	/**
	 * Implement getJtdLanguageDtoList
	 */
	@Override
	public void setJtdLanguageDtoList(Long jtdId, JobTypeDetailDto jtdDto) {
		List<JobTypeDetailLanguageDto> outputList = new ArrayList<>();
		List<JobTypeDetailLanguage> listJtdLanguage = jtdRepository.findTypeDetailLanguageById(jtdId);
		
		for (JobTypeDetailLanguage jtdLanguage: listJtdLanguage) {
			JobTypeDetailLanguageDto jtdLanguageDto = new JobTypeDetailLanguageDto();

			jtdLanguageDto.setId(jtdLanguage.getId());
			jtdLanguageDto.setmLanguageCode(jtdLanguage.getLanguageCode());
			jtdLanguageDto.setmTypeDetail(jtdLanguage.getTypeId());
			jtdLanguageDto.setJtdTitle(jtdLanguage.getTitle());
			jtdLanguageDto.setJtdDescription(CmsUtils.converByteToStringUTF8(jtdLanguage.getDescription()));			
			
			outputList.add(jtdLanguageDto);
		}
		
		jtdDto.setJtdLanguageDto(outputList);
	}
	
	/**
	 * Implement getAllJtLanguageDtoByLangCode
	 */
	@Override
	public List<JobTypeLanguageDto> getAllJtLanguageDtoByLangCode(String langCode) {
		
		List<JobTypeLanguage> listJtLanguage = jtdLanguageRepository.getJtLanguageDtoByLangId(langCode);
		List<JobTypeLanguageDto> targetList = new ArrayList<>();
		
		for (JobTypeLanguage jtLanguage: listJtLanguage) {
			JobTypeLanguageDto jtLanguageDto = new JobTypeLanguageDto();
			
			jtLanguageDto.setId(jtLanguage.getId());
			jtLanguageDto.setLanguageCode(jtLanguage.getLanguageCode());
			jtLanguageDto.setName(jtLanguage.getName());
			jtLanguageDto.setTypeId(jtLanguage.getTypeId());
			
			targetList.add(jtLanguageDto);
		}
		
		return targetList;
	}
	
	/**
	 * Implement getAllJtsLanguageDtoByLangCode
	 */
	@Override
	public List<JobTypeSubLanguageDto> getAllJtsLanguageDtoByLangCode(String langCode) {
		
		List<JobTypeSubLanguage> listJtsLanguage = jtdLanguageRepository.getJtsLanguageDtoByLangId(langCode);
		List<JobTypeSubLanguageDto> targetList = new ArrayList<>();
		
		for (JobTypeSubLanguage jtsLanguage: listJtsLanguage) {
			JobTypeSubLanguageDto jtsLanguageDto = new JobTypeSubLanguageDto();
			
			jtsLanguageDto.setId(jtsLanguage.getId());
			jtsLanguageDto.setM_language_code(jtsLanguage.getM_language_code());
			jtsLanguageDto.setM_type_sub_id(jtsLanguage.getM_type_sub_id());
			jtsLanguageDto.setName(jtsLanguage.getName());
			
			targetList.add(jtsLanguageDto);
		}
		
		return targetList;
	}
}
