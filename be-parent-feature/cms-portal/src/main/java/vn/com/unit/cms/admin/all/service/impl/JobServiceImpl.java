/*******************************************************************************
 * Class        ：JobServiceImpl
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：TranLTH
 * Change log   ：2017/03/07：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
//import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.ConstantDto;
import vn.com.unit.cms.admin.all.dto.JobDto;
import vn.com.unit.cms.admin.all.dto.JobLanguageDto;
import vn.com.unit.cms.admin.all.dto.JobSearchDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.entity.Job;
import vn.com.unit.cms.admin.all.entity.JobLanguage;
//import vn.com.unit.cms.admin.all.enumdef.JobProcessEnum;
import vn.com.unit.cms.admin.all.enumdef.ProductProcessEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CityDto;
//import vn.com.unit.cms.admin.all.jcanary.enumdef.MasterType;
import vn.com.unit.cms.admin.all.jcanary.service.CityService;
import vn.com.unit.cms.admin.all.repository.JobLanguageRepository;
import vn.com.unit.cms.admin.all.repository.JobRepository;
import vn.com.unit.cms.admin.all.service.ConstantService;
import vn.com.unit.cms.admin.all.service.JobService;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.cms.admin.constant.ConstantHistoryApprove;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SelectItem;
//import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.common.exception.SystemException;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.core.dto.LanguageDto;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.HistoryApproveService;
import vn.com.unit.core.service.LanguageService;
//import vn.com.unit.jcanary.service.ProcessService;
//import vn.com.unit.util.Util;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;

/**
 * JobServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JobServiceImpl extends DocumentWorkflowCommonServiceImpl<JobDto, JobDto> implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobLanguageRepository jobLanguageRepository;

    @Autowired
    private ConstantService constantService;
    
    @Autowired
    private LanguageService languageService;

    @Autowired
    SystemConfig systemConfig;

//    @Autowired
//    ProcessService processService;

//    @Autowired
//    HistoryApproveService historyApproveService;

//    @Autowired
//    private ProcessRepository processRepository;

//    @Autowired
//    private JBPMService jBPMService;
    
    @Autowired
    private CityService cityService;

    @Override
    public void initScreenJobList(ModelAndView mav, String languageCode) {
        // Init location = address of org table
        try{
            List<CityDto> locationList = cityService.findAllCityList(languageCode);
//            List<BranchDto> locationList = jobRepository.findLocationByBranch();
            mav.addObject("locationList", locationList);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }

        // Init career
        try{
            List<ConstantDto> careerList = constantService.findByType(ConstDispType.JOB_CAREER.toString(), languageCode);
            mav.addObject("careerList", careerList);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
        
        // Init position
        try{
            List<ConstantDto> positionList = constantService.findByType(ConstDispType.JOB_POSITION.toString(), languageCode);
            mav.addObject("positionList", positionList);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }

        // Init division
        try{
            List<ConstantDto> divisionList = constantService.findByType(ConstDispType.JOB_DIVISION.toString(), languageCode);
            mav.addObject("divisionList", divisionList);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }

        // Init language
        try {
            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            mav.addObject("languageList", languageList);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
        
        // Status list
        try{
            List<SelectItem> statusList = new ArrayList<SelectItem>();
            for (ProductProcessEnum processEnum : ProductProcessEnum.values()) {
                SelectItem item = new SelectItem(processEnum.toString(), processEnum.getName());
                statusList.add(item);
            }
            mav.addObject("statusList", statusList);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
    }

    @Override
    public JobDto searchJobByCondition(JobDto jobDto) {
        return jobRepository.findJobListByCondition(jobDto);
    }

//    @Override
//    @Transactional
//    public void addOrEditJob(JobDto jobDto) throws Exception {
//        String userNameLogin = UserProfileUtils.getUserNameLogin();
//
//        Long processId = processService.getProcessIdByBusinessCode(MasterType.AJ1.toString());
//        Long jobId = jobDto.getJobId();
//        // Update data m_job table
//        Job updateJob = new Job();
//        if (null != jobId) {
//            updateJob = jobRepository.findOne(jobId);
//            if (null == updateJob) {
//                throw new BusinessException("Not found Job by id= " + jobId);
//            }
//            updateJob.setUpdateBy(userNameLogin);
//            updateJob.setUpdateDate(new Date());
//        } else {
//            jobId = Long.parseLong((jobRepository.findMaxIdJob() + 1) + "");
//            updateJob.setCreateBy(userNameLogin);
//            updateJob.setCreateDate(new Date());
//        }
//        updateJob.setCode("JOB" + jobId);
//        updateJob.setName(jobDto.getJobName());
//        updateJob.setEnabled(jobDto.getEnabled());
//        updateJob.setExpiryDate((Date) jobDto.getExpiryDate());
//        updateJob.setLocation(jobDto.getLocation().trim());
//        updateJob.setmCareerId(jobDto.getCareerId().trim());
//        updateJob.setNote(jobDto.getNote());
//        updateJob.setPosition(jobDto.getPosition());
//        updateJob.setSort(jobDto.getSort());
//        updateJob.setUrgent(jobDto.getUrgent());
//        updateJob.setExperience(jobDto.getExperience());
//        updateJob.setDivision(jobDto.getDivision());
//        updateJob.setRecruitmentNumber(jobDto.getRecruitmentNumber());
//        updateJob.setEffectiveDate((Date) jobDto.getEffectiveDate());
//        updateJob.setSalary(jobDto.getSalary().trim());
//        updateJob.setLinkAlias(jobDto.getLinkAlias());
//        // Add process role
////        UserProfile userProfile = UserProfileUtils.getUserProfile();
//        updateJob.setOwnerBranchId(UserProfileUtils.getBranchId());
//        updateJob.setOwnerId(UserProfileUtils.getAccountId());
//        updateJob.setOwnerSectionId(UserProfileUtils.getDepartmentId());
//
//        if (jobDto.isAction()) {
//            updateJob.setStatusCode(JobProcessEnum.SUBMIT.toString());
//        } else {
//            updateJob.setStatusCode(JobProcessEnum.CREATE.toString());
//        }
//
//        updateJob.setProcessId(processId);
//        // End add process
//
//        /* ---Task #16757 no process Start jBPM process -------------------------------- */
////        vn.com.unit.jcanary.entity.Process process = processRepository.findOne(processId);
////        if (process != null) {
////            if (UserProfileUtils.hasRole(RoleConstant.ROLE_USER.concat(ConstantCore.COLON_EDIT))
////                    || UserProfileUtils.hasRole(RoleConstant.ROLE_USER.concat(ConstantCore.COLON_DISP))) {
////                // Check id and status to update jBPM
////                if (!jobDto.isAction() && null == updateJob.getId()) {
////                    // start jBPM Process and action 'save'
////                    Hashtable<String, Object> params = new Hashtable<String, Object>();
////                    params.put(CommonConstant.PARAM_TYPE, CommonConstant.PROCESS_JOB);
////                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_SAVE);
////                    updateJob.setProcessInstanceId(jBPMService.startJBPMProcess(process.getDeploymentId(),
////                            process.getProcessDefinitionId(), RoleConstant.ROLE_USER, RoleConstant.ROLE_USER, params));
////                }
////                if (jobDto.isAction()) {
////                    if (null == updateJob.getId()) {
////                        // When user click direct to 'submit' button, it will
////                        // start jBPM process first.
////                        // After it will continue with updateJBPM status.
////                        Hashtable<String, Object> params = new Hashtable<String, Object>();
////                        params.put(CommonConstant.PARAM_TYPE, CommonConstant.PROCESS_JOB);
////                        params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_SAVE);
////                        updateJob.setProcessInstanceId(jBPMService.startJBPMProcess(process.getDeploymentId(),
////                                process.getProcessDefinitionId(), RoleConstant.ROLE_USER, RoleConstant.ROLE_USER,
////                                params));
////                    }
////                    // action 'submit'
////                    Hashtable<String, Object> params = new Hashtable<String, Object>();
////                    params.put(CommonConstant.PARAM_TYPE, CommonConstant.PROCESS_JOB);
////                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_SUBMIT);
////
////                    List<String> listCheck = new ArrayList<String>();
////                    listCheck.add(CommonConstant.STATUS_SAVED);
////                    listCheck.add(CommonConstant.STATUS_REJECTED);
////                    try {
////                        jBPMService.updateJBPMStatus(process.getDeploymentId(), updateJob.getProcessInstanceId(),
////                                RoleConstant.ROLE_USER, RoleConstant.ROLE_USER, params, CommonConstant.PARAM_STATUS,
////                                listCheck);
////                    } catch (Exception ex) {
////                        throw new SystemException(ex);
////                    }
////                }
////            }
////        }
//        /* --- End jBPM process ---------------------------------- */
//        try {
//            jobRepository.save(updateJob);
//            jobDto.setJobId(updateJob.getId());
//        } catch (Exception ex) {
//            throw new Exception(ex);
//        }
//
//        // Update job language
//        addOrEditJobLanguage(jobDto, userNameLogin);
//
//    }

    @Override
    @Transactional
    public void delete(Long jobId) {
        String userNameLogin = UserProfileUtils.getUserNameLogin();
        Job job = new Job();
        List<JobLanguage> jobLanguages = new ArrayList<JobLanguage>();
        if (null != jobId) {
            job = jobRepository.findOne(jobId);
            jobLanguages = jobRepository.findJobIdLanguage(jobId.toString());
        }
        // delete job
        job.setDeleteDate(new Date());
        job.setDeleteBy(userNameLogin);

        /* Task #16757 no process
         * --- Delete jBPM Process
         * ----------------------------------------------------------
         */
//        vn.com.unit.jcanary.entity.Process process = processRepository.findOne(job.getProcessId());
//        if (process != null) {
//            if (UserProfileUtils.hasRole(RoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(RoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_DISP))
//                    || UserProfileUtils.hasRole(RoleConstant.ROLE_USER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(RoleConstant.ROLE_USER.concat(ConstantCore.COLON_DISP))) {
//                try {
//                    jBPMService.abortProcess(process.getDeploymentId(), RoleConstant.ROLE_MANAGER,
//                            RoleConstant.ROLE_MANAGER, job.getProcessInstanceId());
//                } catch (Exception ex) {
//                    throw new SystemException(ex);
//                }
//            } else {
//                throw new SystemException("user not has role");
//            }
//        }
        /*
         * --- End delete jBPM Process
         * ------------------------------------------------------
         */
        try {
            jobRepository.save(job);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
        // delete job language
        if (null != jobLanguages) {
            for (JobLanguage jobLanguage : jobLanguages) {
                jobLanguage.setDeleteDate(new Date());
                jobLanguage.setDeleteBy(userNameLogin);
                try {
                    jobLanguageRepository.save(jobLanguage);
                } catch (Exception ex) {
                    throw new SystemException(ex);
                }
            }
        }
    }

    @Override
    public JobDto getJobDto(Long jobId, String languageCode) {

        JobDto jobDto = new JobDto();
        if (null == jobId) {
            int sort = jobRepository.findMaxSortJob();
            jobDto.setSort((sort + 1) + "");
            
            jobDto.setListLocation(new ArrayList<String>());
            jobDto.setListCareer(new ArrayList<String>());
            jobDto.setEffectiveDate(new Date());
            jobDto.setRecruitmentNumber(0);
            
            return jobDto;
        }
        jobDto.setJobId(jobId);
        jobDto = jobRepository.findJobDtoById(jobId, languageCode);
        // Set list location
        List<String> listLocation = new LinkedList<String>();
        if (jobDto.getLocation() != null) {
            String[] locationArray = jobDto.getLocation().split(",");
            if (locationArray.length > 0) {
                for (String s : locationArray) {
                    listLocation.add(s);
                }
            }
        }
        jobDto.setListLocation(listLocation);
        // Set list career
        List<String> listCareer = new LinkedList<String>();
        if (jobDto.getCareerId() != null) {
            String[] careerArray = jobDto.getCareerId().split(",");
            if (careerArray.length > 0) {
                for (String s : careerArray) {
                    listCareer.add(s);
                }
            }
        }
        jobDto.setListCareer(listCareer);

        // Get information m_job_language
        List<JobLanguageDto> jobListLanguageDtos = new ArrayList<JobLanguageDto>();
        List<JobLanguageDto> jobLanguageDtos = getInforJobLanguage(jobId.toString());
        // Get language
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        for (LanguageDto languageDto : languageList) {
            for (JobLanguageDto jobLanguageDto : jobLanguageDtos) {
                JobLanguageDto resultJobLanguage = new JobLanguageDto();
                if (languageDto.getCode().equals(jobLanguageDto.getmLanguageCode())) {
                    resultJobLanguage.setId(jobLanguageDto.getId());
                    resultJobLanguage.setJobDetail(jobLanguageDto.getJobDetail());
                    resultJobLanguage.setJobTitle(jobLanguageDto.getJobTitle());
                    resultJobLanguage.setJobDescription(jobLanguageDto.getJobDescription());
                    resultJobLanguage.setmJobId(jobLanguageDto.getmJobId());
                    resultJobLanguage.setmLanguageCode(jobLanguageDto.getmLanguageCode());
                    jobListLanguageDtos.add(jobLanguageDto);
                }
            }
        }
        jobDto.setReferenceType(ConstantHistoryApprove.APPROVE_JOB);
        jobDto.setJobLanguageDtos(jobListLanguageDtos);

        // Get information jca_m_history_approve
//        List<HistoryApproveDto> listHistoryApproveDto = historyApproveService
//                .getInforHistoryApprove(jobDto.getReferenceId(), jobDto.getReferenceType());
//        jobDto.setHistoryApproveDtos(listHistoryApproveDto);
        return jobDto;
    }

    @Override
    public PageWrapper<JobDto> search(int page, JobSearchDto jobSearchDto) {
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<JobDto> pageWrapper = new PageWrapper<JobDto>(page, sizeOfPage);
        if (null == jobSearchDto)
            jobSearchDto = new JobSearchDto();
        // set SearchParm
        // setSearchParm(jobSearchDto);

        int count = jobRepository.countJobByCondition(jobSearchDto);

        List<JobDto> result = new ArrayList<JobDto>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;

            result = jobRepository.findJobLimitByCondition(startIndex, sizeOfPage, jobSearchDto);
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

//    @Override
//    @Transactional
//    public void approver(JobDto jobDto) {
//        String userNameLogin = UserProfileUtils.getUserNameLogin();
//        Long jobId = jobDto.getJobId();
//
//        // update data m_job table
//        Job updateJob = new Job();
//        if (null != jobId) {
//            updateJob = jobRepository.findOne(jobId);
//            if (null == updateJob) {
//                throw new BusinessException("Not found Job by id= " + jobId);
//            }
//            updateJob.setUpdateBy(userNameLogin);
//            updateJob.setUpdateDate(new Date());
//        }
//
//        /* --- Task #16757 no process Start jBPM process -------------------------------- */
////        Process process = processRepository.findOne(updateJob.getProcessId());
////        if (process != null) {
////            if (UserProfileUtils.hasRole(RoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_EDIT))
////                    || UserProfileUtils.hasRole(RoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_DISP))) {
////                Hashtable<String, Object> params = new Hashtable<String, Object>();
////                if (jobDto.isAction()) {
////                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_APPROVE);
////                } else {
////                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_REJECT);
////                }
////
////                List<String> listCheck = new ArrayList<String>();
////                listCheck.add(CommonConstant.STATUS_SUBMITTED);
////
////                jBPMService.updateJBPMStatus(process.getDeploymentId(), updateJob.getProcessInstanceId(),
////                        RoleConstant.ROLE_MANAGER, RoleConstant.ROLE_MANAGER, params, CommonConstant.PARAM_STATUS,
////                        listCheck);
////            } else {
////                throw new SystemException("authority exception");
////            }
////        }
//        /* --- End jBPM process ---------------------------------- */
//        String statusCode = "";
//        if (jobDto.isAction()) {
//            statusCode = JobProcessEnum.APPROVAL.toString();
//        } else {
//            statusCode = JobProcessEnum.REJECT.toString();
//        }
//        updateJob.setStatusCode(statusCode);
//        try {
//            jobRepository.save(updateJob);
//        } catch (Exception ex) {
//            throw new SystemException(ex);
//        }
//        // Update history approve
//
//        HistoryApproveDto historyApproveDto = new HistoryApproveDto();
//        historyApproveDto.setComment(jobDto.getComment());
//        historyApproveDto.setProcessId(updateJob.getProcessId());
//        historyApproveDto.setReferenceId(updateJob.getId());
//        historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_JOB);
//        historyApproveDto.setStatusCode(statusCode);
//        historyApproveDto.setApprover(userNameLogin);
//
//        historyApproveService.addHistoryApprove(historyApproveDto, JobProcessEnum.BUSINESS_CODE.toString());
//    }

    /**
     * addOrEditJobLanguage
     *
     * @param jobDto
     * @param userNameLogin
     * @author TranLTH
     */
    @Transactional
    public void addOrEditJobLanguage(JobDto jobDto, String userNameLogin) {
        // update data m_job_language table
        List<JobLanguageDto> listJobLanguageDto = jobDto.getJobLanguageDtos();

        for (JobLanguageDto jobLanguageDto : listJobLanguageDto) {
            JobLanguage jobLanguage = new JobLanguage();
            if (null != jobLanguageDto.getmJobId()) {
                jobLanguage = jobLanguageRepository.findOne(jobLanguageDto.getId());
                if (null == jobLanguage) {
                    throw new BusinessException("Not found Job Language with id=" + jobDto.getJobId());
                }
                jobLanguage.setUpdateDate(new Date());
                jobLanguage.setUpdateBy(userNameLogin);
            } else {
                jobLanguage.setCreateDate(new Date());
                jobLanguage.setCreateBy(userNameLogin);
            }

            jobLanguage.setmLanguageCode(jobLanguageDto.getmLanguageCode());
            jobLanguage.setmJobId(jobDto.getJobId());
            jobLanguage.setJobDetail(CmsUtils.convertStringToByteUTF8(jobLanguageDto.getJobDetail().trim()));
            jobLanguage.setJobTitle(jobLanguageDto.getJobTitle().trim());
            jobLanguage.setJobDescription(CmsUtils.convertStringToByteUTF8(jobLanguageDto.getJobDescription().trim()));
            try {
                jobLanguageRepository.save(jobLanguage);
            } catch (Exception ex) {
                throw new SystemException(ex);
            }
        }
    }

    /**
     * getInforJobLanguage
     *
     * @param jobId
     * @return
     * @author TranLTH
     */
    public List<JobLanguageDto> getInforJobLanguage(String jobId) {
        List<JobLanguageDto> jobLanguageDtos = new ArrayList<JobLanguageDto>();
        List<JobLanguage> jobLanguages = jobRepository.findJobIdLanguage(jobId.toString());
        for (JobLanguage jobLanguage : jobLanguages) {
            JobLanguageDto jobLanguageDto = new JobLanguageDto();
            jobLanguageDto.setId(jobLanguage.getId());
            jobLanguageDto.setJobDetail(CmsUtils.converByteToStringUTF8(jobLanguage.getJobDetail()));
            jobLanguageDto.setJobDescription(CmsUtils.converByteToStringUTF8(jobLanguage.getJobDescription()));
            jobLanguageDto.setJobTitle(jobLanguage.getJobTitle());
            jobLanguageDto.setmJobId(jobLanguage.getmJobId());
            jobLanguageDto.setmLanguageCode(jobLanguage.getmLanguageCode());

            jobLanguageDtos.add(jobLanguageDto);
        }
        return jobLanguageDtos;
    }
    
    @Override
    public List<JobDto> getAllActive(String languageCode) {  
        Date date = new Date();
        List<JobDto> jobDto = jobRepository.findAllActiveNonPaging(date, languageCode);       
        return jobDto;
    }

    @Override
    public void updateModelsSort(SortPageDto sortPageModel) {
        if(sortPageModel.getSortList() != null){
            for(SortOrderDto sortItem : sortPageModel.getSortList()){
                Job jobEntity = jobRepository.findOne(sortItem.getObjectId());
                jobEntity.setSort(sortItem.getSortValue().toString());
                jobRepository.save(jobEntity);
            }
        }
    }

    @Override
    public JobDto getEdit(Long id, String customerAlias, Locale locale) {
        return getJobDto(id, locale.toString());
    }
}