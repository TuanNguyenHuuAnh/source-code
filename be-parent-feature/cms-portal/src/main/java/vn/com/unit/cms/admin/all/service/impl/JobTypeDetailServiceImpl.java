package vn.com.unit.cms.admin.all.service.impl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.google.protobuf.TextFormat.ParseException;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.dto.JobTypeDetailDto;
import vn.com.unit.cms.admin.all.dto.JobTypeDetailLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobTypeDetailSearchDto;
import vn.com.unit.cms.admin.all.dto.JobTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubLanguageDto;
import vn.com.unit.cms.admin.all.entity.JobTypeDetail;
import vn.com.unit.cms.admin.all.entity.JobTypeDetailLanguage;
import vn.com.unit.cms.admin.all.enumdef.JobTypeSearchEnum;
import vn.com.unit.cms.admin.all.repository.JobTypeDetailLanguageRepository;
import vn.com.unit.cms.admin.all.repository.JobTypeDetailRepository;
import vn.com.unit.cms.admin.all.service.JobTypeDetailLanguageService;
import vn.com.unit.cms.admin.all.service.JobTypeDetailService;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.service.LanguageService;
//import vn.com.unit.jcanary.utils.Utils;
//import vn.com.unit.util.Util;

@Service
@Transactional(rollbackFor = Exception.class)
public class JobTypeDetailServiceImpl implements JobTypeDetailService{

	@Autowired
	private JobTypeDetailRepository jtdRepository;
	
	@Autowired
	private JobTypeDetailLanguageRepository jtdLanguageRepository;
	
	@Autowired
	private JobTypeDetailLanguageService jtdLangService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private SystemConfig systemConfig;
	
	@Override     
    public void initScreenJtdList(ModelAndView mav, String langCode) {
        try{
            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            mav.addObject("languageList", languageList);
            
            List<JobTypeLanguageDto> jtDto = jtdLangService.getAllJtLanguageDtoByLangCode(langCode);
            mav.addObject("jtDto", jtDto);

            List<JobTypeSubLanguageDto> jtsDto = jtdLangService.getAllJtsLanguageDtoByLangCode(langCode);
            mav.addObject("jtsDto", jtsDto);
        } catch (Exception ex){
            throw new SystemException(ex);
        }               
    }  
	
	/**
	 * TODO: ERROR IN HERE
	 * addOrEdit
	 * @param jobTypeDetailDto
	 */
	@Override
	public void addOrEdit(JobTypeDetailDto jtdDto) {
		String userName = UserProfileUtils.getUserNameLogin();
		
		Long jtdId = jtdDto.getId();
		JobTypeDetail targetJtd = new JobTypeDetail();
		
		// Check if jobTypeDetail already exists
		if (jtdId != null) {
			targetJtd = jtdRepository.findOne(jtdId);
			
			if (targetJtd == null) {
				throw new BusinessException("ID: " + jtdId + " not found");
			}
			targetJtd.setUpdateBy(userName);
			targetJtd.setUpdateDate(new Date());
		} else {
			targetJtd.setCreateBy(userName);
			targetJtd.setCreateDate(new Date());
		}
		
		// General tasks
		targetJtd.setCode(jtdDto.getCode().toUpperCase().trim());
		targetJtd.setmTypeId(jtdDto.getmTypeId());
		targetJtd.setmTypeSubId(jtdDto.getmTypeSubId());
		targetJtd.setNote(jtdDto.getNote());
		
		jtdRepository.save(targetJtd);
		jtdDto.setId(targetJtd.getId());
		
		// update jtd language
		addOrEditJtdLanguage(jtdDto, userName);
	}
	
	/**
	 * add or edit m_job_type_detail_language
	 * @param jtdDto
	 * @param userName
	 */
	@Transactional
	public void addOrEditJtdLanguage(JobTypeDetailDto jtdDto, String userName) {

		List<JobTypeDetailLanguageDto> listJtdLanguageDto = jtdDto.getJtdLanguageDto();
		
		for (JobTypeDetailLanguageDto jtdLanguageDto: listJtdLanguageDto) {
			JobTypeDetailLanguage jtdLanguage = new JobTypeDetailLanguage();
			// jtdLanguage exists, do edit
			if (jtdLanguageDto.getmTypeDetailId() != null) {
				jtdLanguage = jtdLanguageRepository.findOne(jtdLanguageDto.getId());

				if (jtdLanguage == null)
					throw new BusinessException("Language not found, id: " + jtdLanguageDto.getId());

				jtdLanguage.setUpdateDate(new Date());
				jtdLanguage.setUpdateBy(userName);
			// Do add
			} else {
				jtdLanguage.setCreateDate(new Date());
				jtdLanguage.setCreateBy(userName);
			}
			
			jtdLanguage.setLanguageCode(jtdLanguageDto.getmLanguageCode());
			jtdLanguage.setTypeId(jtdDto.getId());
			jtdLanguage.setTitle(jtdLanguageDto.getJtdTitle().trim());
			jtdLanguage.setDescription(CmsUtils.convertStringToByteUTF8(jtdLanguageDto.getJtdDescription()));

			jtdLanguageRepository.save(jtdLanguage);
		}
		
		CmsUtils.moveTempSubFolderToUpload(Paths.get(AdminConstant.JOB_TYPE_FOLDER, AdminConstant.EDITOR_FOLDER, jtdDto.getRequestToken()).toString());
	}
	
	/**
	 * Delete jtd and jtdLanguage given id
	 * @param jtdId
	 */
	@Override
	@Transactional
	public void delete(Long jtdId) {
		String userName = UserProfileUtils.getUserNameLogin();
		JobTypeDetail jtd = new JobTypeDetail();
		List<JobTypeDetailLanguage> listJtdLanguage = new ArrayList<JobTypeDetailLanguage>();
		
		if (jtd != null) {
			jtd = jtdRepository.findOne(jtdId);
			listJtdLanguage = jtdRepository.findTypeDetailLanguageById(jtdId);
		}
		// delete jtd
		jtd.setDeleteDate(new Date());
		jtd.setDeleteBy(userName);
		jtdRepository.save(jtd);
		
		// delete jtdLanguage
		if (listJtdLanguage != null) {
			for (JobTypeDetailLanguage jtdLanguage: listJtdLanguage) {
				jtdLanguage.setDeleteDate(new Date());
				jtdLanguage.setDeleteBy(userName);
				jtdLanguageRepository.save(jtdLanguage);
			}
		}
	}
	
	/**
	 * retrieve JobTypeDetailDto object given id
	 */
	@Override
	public JobTypeDetailDto getJobTypeDetailDto(Long jtdId) {
		
		JobTypeDetailDto jtdDto = new JobTypeDetailDto();
		
		if (jtdId == null) return jtdDto;
		
		jtdDto = jtdRepository.findJtdDtoById(jtdId);
		
		// call helper
		jtdLangService.setJtdLanguageDtoList(jtdId, jtdDto);
		jtdLangService.setJtLanguageDtoList(jtdId, jtdDto);
		jtdLangService.setJtsLanguageDtoList(jtdId, jtdDto);
		
		return jtdDto;
	}
	
	/**
	 * search 
	 * @param page
	 * @param jtdSearchDto
	 * @return PageWrapper<JobTypeDetailDto> for current page
	 */
	@Override
	public PageWrapper<JobTypeDetailDto> search(int page, JobTypeDetailSearchDto jtdSearchDto)
			throws ParseException {
		int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<JobTypeDetailDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
		
		if (jtdSearchDto == null)
			jtdSearchDto = new JobTypeDetailSearchDto();
		setSearchParam(jtdSearchDto);
		
		int count = jtdRepository.countJtd(jtdSearchDto);
		System.out.println("COUNT IS: " + count);
		List<JobTypeDetailDto> result = new LinkedList<>();
		List<JobTypeDetailDto> listJtdPage = new LinkedList<>();
		
		int index = 0;
		int currentPage = pageWrapper.getCurrentPage();
		
		// Manage what to show on current page
		if (count > 0) {
			
			int startIndex = (currentPage - 1) * sizeOfPage;
			int endIndex;
			
			result = jtdRepository.findJtdByCondition(jtdSearchDto);
			
			if (startIndex + sizeOfPage >= result.size()) {
				endIndex = result.size();
			} else {
				endIndex = startIndex + sizeOfPage;
			}
			
			listJtdPage = result.subList(startIndex, endIndex);
		}
		
		// Set data to pages
		pageWrapper.setDataAndCount(listJtdPage, count);
		if (page > 1) {
			pageWrapper.setStartIndexCurrent(index);
		}
		System.out.println("TOTAL PAGE IS: " + pageWrapper.getTotalPages());
		
		return pageWrapper;
	}
	
	/**
	 * Implement getAllJtdsDto
	 */
	@Override
	public List<JobTypeDetailDto> getAllJtdDto() {
		List<JobTypeDetailDto> result = jtdRepository.findAllJtdDto();
		return result;
	}
    /**
     * setSearchParm
     *
     * @param jtdSearchDto
     * @author TranLTH
     */
    private void setSearchParam(JobTypeDetailSearchDto jtdSearchDto) {
        if (null == jtdSearchDto.getFieldValues()) {
            jtdSearchDto.setFieldValues(new ArrayList<String>());
        }

        if (jtdSearchDto.getFieldValues().isEmpty()) {
            jtdSearchDto.setCode(jtdSearchDto.getFieldSearch() != null ? jtdSearchDto.getFieldSearch().trim() : jtdSearchDto.getFieldSearch());      
        } else {
            for (String field : jtdSearchDto.getFieldValues()) {
                if (StringUtils.equals(field, JobTypeSearchEnum.CODE.name())) {
                    jtdSearchDto.setCode(jtdSearchDto.getFieldSearch().trim());
                    continue;
                }       
            }
        }
    }
}
