/*******************************************************************************
 * Class        ：JobTypeSubServiceImpl
 * Created date ：2017/08/10
 * Lasted date  ：2017/08/10
 * Author       ：ToanNT
 * Change log   ：2017/08/10：01-00 ToanNT create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.dto.JobTypeSubDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubEditDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubSearchDto;
import vn.com.unit.cms.admin.all.entity.JobTypeSub;
import vn.com.unit.cms.admin.all.entity.JobTypeSubLanguage;
import vn.com.unit.cms.admin.all.enumdef.JobTypeSubSearchEnum;
import vn.com.unit.cms.admin.all.repository.JobTypeSubRepository;
import vn.com.unit.cms.admin.all.service.JobTypeSubLanguageService;
import vn.com.unit.cms.admin.all.service.JobTypeSubService;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JobTypeSubServiceImpl implements JobTypeSubService {

	@Autowired
	private JobTypeSubRepository jobTypeSubRepository;

	@Autowired
	JobTypeSubLanguageService jobTypeSubLanguageService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	SystemConfig systemConfig;

	@Override
	public JobTypeSubEditDto findTypeSub(Long id, String lang) {
		
		JobTypeSubEditDto resultDto = new JobTypeSubEditDto();
		List<JobTypeSubDto> jobTypeNameList = getJobTypeSubName(lang);
		resultDto.setJobTypeSubDtosTitleList(jobTypeNameList);
		
		if (id == null) {
			Long sort = jobTypeSubRepository.findMaxSort();
			resultDto.setSort(sort == null ? 1 : sort + 1);
			return resultDto;
		}

		// set JobTypeSub
		JobTypeSub entity = jobTypeSubRepository.findOne(id);
		if (null != entity) {
			resultDto.setId(entity.getId());
			resultDto.setCode(entity.getCode());
			resultDto.setDescription(entity.getDescription());
			resultDto.setNote(entity.getNote());
			resultDto.setSort(entity.getSort());
			resultDto.setTypeId(entity.getTypeId());
		}
		List<JobTypeSubLanguageDto> jobTypeSubLanguageList = getJobTypeSubLanguageList(id);
		resultDto.setJobTypeSubLanguageList(jobTypeSubLanguageList);

		return resultDto;
	}

	private List<JobTypeSubDto> getJobTypeSubName(String lang) {
		// TODO Auto-generated method stub
		
		return jobTypeSubRepository.findAllTypeSub(lang);

	}

	/**
	 * get JobTypeSubLangguage
	 */
	private List<JobTypeSubLanguageDto> getJobTypeSubLanguageList(Long typeSubId) {
		List<JobTypeSubLanguageDto> resultList = new ArrayList<JobTypeSubLanguageDto>();

		List<JobTypeSubLanguage> jobSubLanguageList = jobTypeSubLanguageService.findByTypeId(typeSubId);

		// languageList
		List<Language> languageList = languageService.findAllActive();

		// loop language
		for (Language language : languageList) {
			// loop type Sub Languages
			for (JobTypeSubLanguage entity : jobSubLanguageList) {
				// typeSubLanguageId is languageId
				if (StringUtils.equals(entity.getM_language_code(), language.getCode())) {
					JobTypeSubLanguageDto jobTypeSubLanguageDto = new JobTypeSubLanguageDto();
					jobTypeSubLanguageDto.setId(entity.getId());
					jobTypeSubLanguageDto.setM_type_sub_id(entity.getM_type_sub_id());
					jobTypeSubLanguageDto.setName(entity.getName());
					jobTypeSubLanguageDto.setM_language_code(entity.getM_language_code());
					resultList.add(jobTypeSubLanguageDto);
					break;
				}
			}
		}
		return resultList;

	}

	@Override
	@Transactional
	public void addOrEdit(JobTypeSubEditDto editDto) {
		// user login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();

		createOrEditJobTypeSub(editDto, UserProfileUtils.getUserNameLogin());

		createOrEditLanguage(editDto, UserProfileUtils.getUserNameLogin());
	}

	/**
	 * create or update jobTypeSub
	 *
	 * 
	 */
	private void createOrEditJobTypeSub(JobTypeSubEditDto editDto, String userName) {
		// m_job_type_sub entity
		JobTypeSub entity = new JobTypeSub();

		// job exists id
		if (null != editDto.getId()) {
			entity = jobTypeSubRepository.findOne(editDto.getId());

			if (null == entity) {
				throw new BusinessException("Not found FaqsType with id=" + editDto.getId());
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(userName);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(userName);
		}

		entity.setDescription(editDto.getDescription());
		entity.setNote(editDto.getNote());
		entity.setSort(editDto.getSort());
		entity.setCode(editDto.getCode());
		entity.setTypeId(editDto.getTypeId());
		
		jobTypeSubRepository.save(entity);

		editDto.setId(entity.getId());
	}

	private void createOrEditLanguage(JobTypeSubEditDto editDto, String userName) {
		for (JobTypeSubLanguageDto typeSubDto : editDto.getJobTypeSubLanguageList()) {

			// m_faqs_language entity
			JobTypeSubLanguage entity = new JobTypeSubLanguage();

			if (null != typeSubDto.getId()) {
				entity = jobTypeSubLanguageService.findById(typeSubDto.getId());
				if (null == entity) {
					throw new BusinessException("Not found FaqsLanguag with id=" + typeSubDto.getId());
				}
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(userName);
			} else {
				entity.setCreateDate(new Date());
				entity.setCreateBy(userName);

			}
			entity.setM_type_sub_id(editDto.getId());
			entity.setName(typeSubDto.getName());
			entity.setM_language_code(typeSubDto.getM_language_code());
			jobTypeSubLanguageService.saveJobTypeSubLanguage(entity);
		}
	}

	@Override
	public PageWrapper<JobTypeSubSearchDto> findJobTypeSubList(JobTypeSubSearchDto typeSubDto, int page) {
		int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		
		  PageWrapper<JobTypeSubSearchDto> pageWrapper = new PageWrapper<JobTypeSubSearchDto>(page, sizeOfPage);
	        if (null == typeSubDto)
	        	typeSubDto = new JobTypeSubSearchDto();
	        
	     // set SearchParm
	       setSearchParm(typeSubDto);
	        
	        int count = jobTypeSubRepository.countJobTypeSub(typeSubDto);
	        List<JobTypeSubSearchDto> result = null;
	        if (count > 0) {
	            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
	            result = jobTypeSubRepository.findJobTypeSubList(offsetSQL, sizeOfPage, typeSubDto);
	        }
	        pageWrapper.setDataAndCount(result, count);
	        return pageWrapper;
	}
	
	
	 private void setSearchParm(JobTypeSubSearchDto condition){
		 if (null == condition.getFieldValues()) {
	            condition.setFieldValues(new ArrayList<String>());
	        }
		 
		 if (condition.getFieldValues().isEmpty()) {
	            condition.setCode(condition.getFieldSearch());
	            condition.setTitle(condition.getFieldSearch());
	            condition.setDescription(condition.getFieldSearch());
	        }
		 else {
	            for (String field : condition.getFieldValues()) {
	                if (StringUtils.equals(field, JobTypeSubSearchEnum.CODE.name())) {
	                    condition.setCode(condition.getFieldSearch());
	                    continue;
	                }
	                if (StringUtils.equals(field, JobTypeSubSearchEnum.NAME.name())) {
	                    condition.setTitle(condition.getFieldSearch());
	                    continue;
	                }

	                if (StringUtils.equals(field, JobTypeSubSearchEnum.DESCRIPTION.name())) {
	                    condition.setDescription(condition.getFieldSearch());
	                    continue;
	                }
	            }
	        }
	 }
	 @Override
	 @Transactional
	    public void deleteJobTypeSub(Long id){
		 // check exits JobTypeSUb
		 JobTypeSub jobTypeSub = jobTypeSubRepository.findOne(id);
		 if(null == jobTypeSub){
			 throw new BusinessException("Not found FaqsType with id=" + id);
		 }
		 // user name login
	        String userName = UserProfileUtils.getUserNameLogin();
	    // delete JObTypeSubLanguage
	      jobTypeSubLanguageService.deleteByTypeId(id, userName);
	      
	      //delate JObTypeSub
	      jobTypeSub.setDeleteDate(new Date());
	      jobTypeSub.setDeleteBy(userName);
	      jobTypeSubRepository.save(jobTypeSub);
	    }
	 
	 @Override
	 public JobTypeSub findJobTypeSubByCode(String code){
		 return jobTypeSubRepository.findJobTypeSubByCode(code);
		 
	 }
}
