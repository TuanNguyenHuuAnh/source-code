/*******************************************************************************
 * Class        ：JobTypeServiceImpl
 * Created date ：2017/08/10
 * Lasted date  ：2017/08/10
 * Author       ：HoangNP
 * Change log   ：2017/08/10：01-00 HoangNP create a new
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
import vn.com.unit.cms.admin.all.dto.JobTypeEditDto;
import vn.com.unit.cms.admin.all.dto.JobTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.JobType;
import vn.com.unit.cms.admin.all.entity.JobTypeLanguage;
import vn.com.unit.cms.admin.all.enumdef.JobTypeSearchEnum;
import vn.com.unit.cms.admin.all.repository.JobTypeRepository;
import vn.com.unit.cms.admin.all.service.JobTypeLanguageService;
import vn.com.unit.cms.admin.all.service.JobTypeService;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly=true, rollbackFor = Exception.class)
public class JobTypeServiceImpl implements JobTypeService {

	@Autowired
    SystemConfig systemConfig;
	
	@Autowired
	JobTypeRepository jobTypeRepository;
	
	@Autowired
    private LanguageService languageService;
	
	@Autowired
    private JobTypeLanguageService jobTypeLanguageService;
	
	@Override
	public JobTypeEditDto findJobType(Long id) {
		JobTypeEditDto resultDto = new JobTypeEditDto();

		if (id == null) {
            Long sort = jobTypeRepository.findMaxSort();
            //Long delete = jobTypeRepository.countDelete();
            resultDto.setSort(sort == null ? 1 : sort + 1);
            return resultDto;
        }
		
		JobType entity = jobTypeRepository.findOne(id);
		
		if(null != entity){
			resultDto.setId(entity.getId());
			resultDto.setCode(entity.getCode());
			resultDto.setDescription(entity.getDescription());
			resultDto.setColUrl(entity.getUrl());
			resultDto.setSort(entity.getSort());
		}
		
		List<JobTypeLanguageDto> jobTypeLanguageList = getJobTypeLanguageList(id);
		resultDto.setJobTypeLanguageList(jobTypeLanguageList);
		
        return resultDto;
		
	}
	
	private List<JobTypeLanguageDto> getJobTypeLanguageList(Long jobId) {
        List<JobTypeLanguageDto> resultList = new ArrayList<JobTypeLanguageDto>();

        List<JobTypeLanguage> jobTypeLanguageList = jobTypeLanguageService.findByTypeId(jobId);

        // languageList
        List<Language> languageList = languageService.findAllActive();

        // loop language
        for (Language language : languageList) {
            // loop categoryLanguages
            for (JobTypeLanguage entity : jobTypeLanguageList) {
            	if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                   JobTypeLanguageDto jobTypeLanguageDto = new JobTypeLanguageDto();
                   jobTypeLanguageDto.setId(entity.getId());
                   jobTypeLanguageDto.setTypeId(entity.getTypeId());
                   jobTypeLanguageDto.setLanguageCode(entity.getLanguageCode());
                   jobTypeLanguageDto.setName(entity.getName());
                   
                   resultList.add(jobTypeLanguageDto);
                   break;
                }
			}
            
        }
        return resultList;
    }

	@Override
	@Transactional
	public void addOrEdit(JobTypeEditDto editDto) {
//		UserProfile userProfile = UserProfileUtils.getUserProfile();
		
		createOrEditJobType(editDto, UserProfileUtils.getUserNameLogin());
	
		createOrEditLanguage(editDto, UserProfileUtils.getUserNameLogin());
		
	}
	
	private void createOrEditJobType(JobTypeEditDto editDto, String userName){
		JobType entity = new JobType();
		
		if(null != editDto.getId()){
			entity = jobTypeRepository.findOne(editDto.getId());
			
			if (null == entity) {
                throw new BusinessException("Not found JobType with id=" + editDto.getId());
            }
			
			entity.setUpdateDate(new Date());
            entity.setUpdateBy(userName);
		} else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(userName);
        }
		
		entity.setCode(editDto.getCode().toUpperCase());
		entity.setDescription(editDto.getDescription());
		entity.setUrl(editDto.getColUrl());
		entity.setSort(editDto.getSort());
		 
		try{
			jobTypeRepository.save(entity);
		}catch(Exception e){
			System.out.println("Msg: Error: " + e);
		}
		
		editDto.setId(entity.getId());
	}
	
	private void createOrEditLanguage(JobTypeEditDto editDto, String userName){
		
		for (JobTypeLanguageDto jobTypeDto : editDto.getJobTypeLanguageList()){
			
			JobTypeLanguage entity = new JobTypeLanguage();
			
			if(null != jobTypeDto.getId()){
				entity = jobTypeLanguageService.findById(jobTypeDto.getId());
				if (null == entity) {
                    throw new BusinessException("Not found JobTypeLanguage with id=" + jobTypeDto.getId());
                }
				entity.setUpdateDate(new Date());
                entity.setUpdateBy(userName);
			} else {
                entity.setCreateDate(new Date());
                entity.setCreateBy(userName);
			}
			
			entity.setTypeId(editDto.getId());
			entity.setLanguageCode(jobTypeDto.getLanguageCode());
			entity.setName(jobTypeDto.getName());
			
			jobTypeLanguageService.saveJobTypeLanguage(entity);						
		}
	}

	@Override
	public PageWrapper<JobTypeSearchDto> findJobTypeList(JobTypeSearchDto condition, int page) {
		int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
				
		PageWrapper<JobTypeSearchDto> pageWrapper = new PageWrapper<JobTypeSearchDto>(page, sizeOfPage);
		if(null == condition)
			condition = new JobTypeSearchDto();
		// set SearchParm
        setSearchParm(condition);
		
        int count = jobTypeRepository.countJobType(condition);
        List<JobTypeSearchDto> result = null;
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
            result = jobTypeRepository.findJobTypeList(offsetSQL, sizeOfPage, condition);
        }
        
        pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}
	
	private void setSearchParm(JobTypeSearchDto condition) {
        if (null == condition.getFieldValues()) {
            condition.setFieldValues(new ArrayList<String>());
        }

        if (condition.getFieldValues().isEmpty()) {
            condition.setCode(condition.getFieldSearch());
            condition.setName(condition.getFieldSearch());
            condition.setDescription(condition.getFieldSearch());
        } else {
            for (String field : condition.getFieldValues()) {
                if (StringUtils.equals(field, JobTypeSearchEnum.CODE.name())) {
                    condition.setCode(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, JobTypeSearchEnum.NAME.name())) {
                    condition.setName(condition.getFieldSearch());
                    continue;
                }

                if (StringUtils.equals(field, JobTypeSearchEnum.DESCRIPTION.name())) {
                    condition.setDescription(condition.getFieldSearch());
                    continue;
                }
            }
        }
    }

	@Override
	public JobType findJobTypeByCode(String code) {
		return jobTypeRepository.findJobTypeByCode(code);
	}

	@Override
	@Transactional
	public void deleteJobType(Long id) {
		JobType jobType = jobTypeRepository.findOne(id);
		
		// user name login
        String userName = UserProfileUtils.getUserNameLogin();
        
		//delete job type language
		jobTypeLanguageService.deleteByTypeId(id, userName);
		
		//delete job type
		jobType.setDeleteDate(new Date());
		jobType.setDeleteBy(userName);
		jobTypeRepository.save(jobType);
	}	
}