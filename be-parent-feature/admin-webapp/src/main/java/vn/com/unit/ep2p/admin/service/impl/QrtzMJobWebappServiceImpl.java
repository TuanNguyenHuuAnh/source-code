package vn.com.unit.ep2p.admin.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.config.PageSizeConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.controller.QuartzController;
import vn.com.unit.ep2p.admin.repository.AppQrtzMJobClassRepository;
import vn.com.unit.ep2p.admin.repository.AppQrtzMJobRepository;
import vn.com.unit.ep2p.admin.repository.AppQrtzMJobTypeRepository;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.QrtzMJobStoreWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;
import vn.com.unit.quartz.job.enumdef.JobTypeEnum;
import vn.com.unit.quartz.job.enumdef.QrtzMJobSearchEnum;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzMJobWebappServiceImpl implements QrtzMJobWebappService {

    private static final Logger logger = LoggerFactory.getLogger(QrtzMJobWebappServiceImpl.class);
    /*
     * private static final String JOB_TYPE = "JOB_TYPE";
     * 
     * private static final String REGEX_MATCH_ANY_CHAR = ".*";
     */
    @Autowired
    AppQrtzMJobRepository jobRepository;

    @Autowired
    AppQrtzMJobClassRepository jobClassRepository;

    @Autowired
    AppQrtzMJobTypeRepository jobTypeRepository;

    @Autowired
    QrtzMJobStoreWebappService jobStoreService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    PageSizeConfig pageSizeConfig;

    @Autowired
    MessageSource msg;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private CompanyService companyService;

    @Autowired
    CommonService commonService;

    @Override
    public void deleteJob(Long id) throws Exception {
//        String user = UserProfileUtils.getUserNameLogin();
        Date date = commonService.getSystemDateTime();
        QrtzMJob jobEntity = jobRepository.findOne(id);
        jobEntity.setValidflag(ValidFlagEnum.DELETED.toLong());
        jobEntity.setDeletedId(UserProfileUtils.getAccountId());
        jobEntity.setDeletedDate(date);
        if (jobEntity.getId() != null) {
            jobRepository.update(jobEntity);
        }
    }

    @Override
    public QrtzMJob getById(Long id) throws Exception {
        return jobRepository.findOne(id);
    }

    @Override
    public QrtzMJob getByJobId(Long jobId) throws Exception {
        QrtzMJob qJobMaster = jobRepository.getByJobId(jobId);
        return qJobMaster == null ? new QrtzMJob() : qJobMaster;
    }

    @Override
    public PageWrapper<QrtzMJobDto> getJobs(QrtzMJobSearchDto jobSearch, int pageSize, int page) {
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<QrtzMJobDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        List<QrtzMJobDto> result = new ArrayList<>();
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        int count = 0;
        try {
            setSearchParam(jobSearch);
            count = jobRepository.countJobByCondition(jobSearch);
            if (count > 0) {
                int currentPage = pageWrapper.getCurrentPage();
                int startIndex = (currentPage - 1) * sizeOfPage;
                result = jobRepository.getJobs(jobSearch, startIndex, sizeOfPage);
            }
        } catch (Exception e) {
            logger.error("#getJobs", e);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    @Override
    public List<Select2Dto> getListForCombobox(String term, List<Long> companyIdList) throws SQLException {
        return jobRepository.getListForCombobox(term, companyIdList);
    }

    @Override
    public List<Select2Dto> getListJobClass(String term, Long id) throws SQLException {
        return jobClassRepository.findListForCombobox(term, id);
    }

    @Override
    public List<Select2Dto> getListJobType() throws Exception {
        return jobTypeRepository.findListForCombobox();
    }

    @Override
    public Select2Dto getSelect2ByJobIdAndCompanyId(Long jobId, Long id) {
        try {
            Select2Dto job = jobRepository.findSelect2ByJobIdAndCompanyId(jobId, id);
            return job == null ? new Select2Dto() : job;
        } catch (Exception e) {
            logger.error("#getSelect2ByCompanyId", e);
        }
        return new Select2Dto();
    }

    @Override
    public void initCreateScreen(ModelAndView mav, Long jobId) throws Exception {

        QrtzMJob jobEntity = new QrtzMJob();
        QrtzMJobStore jobStoreEntity = new QrtzMJobStore();

        List<String> lstSendStatus = new ArrayList<>();
        if (jobId != null) {
            jobEntity = getById(jobId);
            String status = jobEntity.getSendStatus();
            if (!StringUtils.isEmpty(status)) {
                String[] arrSendStatus = StringUtils.isBlank(status) ? new String[] {}
                        : status.replaceAll(StringUtils.SPACE, StringUtils.EMPTY).split(ConstantCore.COMMA);
                for (String itemSendStatus : arrSendStatus) {
                    lstSendStatus.add(itemSendStatus);
                }
            }
            String groupName = jobEntity.getJobGroup();
            jobStoreEntity = jobStoreService.getByGroupCode(groupName);
            // set url ajax
            String url = QuartzController.CONTROLLER_QUARTZ.concat(QuartzController.CONTROLLER_JOB)
                    .concat(QuartzController.CONTROLLER_UPSERT).concat("?id=").concat(jobId.toString()).substring(1);
            jobEntity.setUrl(url);
        }
        initScreen(mav, jobEntity, jobStoreEntity, lstSendStatus);
    }

    @Override
    public List<Select2Dto> getEmailTemplateSelection(Long comId) throws Exception {
        return templateService.getTemplateByCompanyId(null, null, comId);
    }

    @Override
    public boolean isJobInUse(Long jobId) {
        return jobRepository.getJobInUse(jobId) != null;
    }

    @Override
    public void save(QrtzMJob jobEntity, QrtzMJobStore jobStoreEntity, Locale locale) throws Exception {
        boolean hasExisted = jobStoreService.isGroupCodeExists(jobEntity.getJobGroup());
        if (jobEntity.getId() == null && hasExisted) {
            throw new Exception(msg.getMessage("quartz.error.group.code.exists", null, locale));
        }

        if (JobTypeEnum.EXECUTE_SP.name().equalsIgnoreCase(String.valueOf(jobEntity.getJobType()))
                || JobTypeEnum.REMIND_SP.name().equalsIgnoreCase(String.valueOf(jobEntity.getJobType()))) {
            QrtzMJobStore jobStoreTemp = jobStoreService.getByGroupCode(jobEntity.getJobGroup());
            jobStoreTemp.setGroupCode(jobEntity.getJobCode());
            jobStoreTemp.setExecOrder(1L);
            jobStoreTemp.setStoreName(jobStoreEntity.getStoreName());
            jobStoreService.save(jobStoreTemp);
        }
        jobEntity.setJobGroup(jobEntity.getJobCode());
        saveJob(jobEntity);
    }

    public void saveJob(QrtzMJob jobEntity) throws Exception {
        @SuppressWarnings("unused")
		String userName = UserProfileUtils.getUserNameLogin();
        jobEntity.setCreatedId(UserProfileUtils.getAccountId());
        jobEntity.setCreatedDate(new Date());
        jobEntity.setValidflag(1L);
        if (jobEntity.getId() != null) {
            jobRepository.update(jobEntity);
        } else {
            jobRepository.create(jobEntity);
        }

    }

    private void setSearchParam(QrtzMJobSearchDto jobSearch) throws Exception {
        List<String> fieldValues = jobSearch.getFieldValues();
        String fieldSearch = jobSearch.getFieldSearch();
        if (fieldValues == null || fieldValues.isEmpty()) {
            jobSearch.setJobCode(fieldSearch);
            jobSearch.setJobName(fieldSearch);
            jobSearch.setStoreName(fieldSearch);
            jobSearch.setJobType(fieldSearch);
            jobSearch.setJobClassName(fieldSearch);
        } else {
            for (String i : fieldValues) {
                if (QrtzMJobSearchEnum.JOB_CODE.name().equals(i)) {
                    jobSearch.setJobCode(fieldSearch);
                }
                if (QrtzMJobSearchEnum.JOB_NAME.name().equals(i)) {
                    jobSearch.setJobName(fieldSearch);
                }
                /*
                 * if (QrtzMJobSearchEnum.JOB_STORE.name().equals(i)) {
                 * jobSearch.setStoreName(fieldSearch); }
                 */
                if (QrtzMJobSearchEnum.JOB_TYPE.name().equals(i)) {
                    jobSearch.setJobType(fieldSearch);
                }
                if (QrtzMJobSearchEnum.JOB_CLASS_NAME.name().equals(i)) {
                    jobSearch.setJobClassName(fieldSearch);
                }
            }
        }

        jobSearch.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
        jobSearch.setCompanyIdList(UserProfileUtils.getCompanyIdList());
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.dmcs.service.QrtzMJobService#getByJobGroup(java.lang.String)
     */
    @Override
    public QrtzMJob getByJobGroup(Long companyId, String jobGroup) throws Exception {
        return jobRepository.getByJobGroup(companyId, jobGroup);
    }

    @Override
    public Select2Dto getCompanyById(Long id) throws Exception {
        Select2Dto dto = new Select2Dto();
        if (id != null) {
            CompanyDto company = companyService.findById(id);
            if (company != null && company.getId() != null) {
                String comId = company.getId().toString();
                String name = company.getName();
                dto.setId(comId);
                dto.setName(name);
                dto.setText(name);
            }
        }
        return dto;
    }

    @Override
    public void initScreen(ModelAndView mav, QrtzMJob jobEntity, QrtzMJobStore jobStoreEntity,
            List<String> lstSendStatus) throws Exception {
        Long comId = jobEntity.getCompanyId();
        String path = jobEntity.getJobClassName();
        List<Select2Dto> emailTemplateSelection = getEmailTemplateSelection(comId);
        // Select2Dto company = getCompanyById(comId);
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), false);
        if (comId == null) {
            jobEntity.setCompanyId(UserProfileUtils.getCompanyId());
        }
        mav.addObject("companyList", companyList);
        mav.addObject("lstSendStatus", lstSendStatus);
        mav.addObject("emailTemplateSelection", emailTemplateSelection);
        mav.addObject("className", getClassNameByPath(path));
        mav.addObject("jobTypes", getListJobType());
        mav.addObject("job", jobEntity);
        mav.addObject("jobStore", jobStoreEntity);
        // mav.addObject("company", company);
    }

    @Override
    public String getClassNameByPath(String path) throws Exception {
        return jobClassRepository.findClassNameByPath(path);
    }

    @Override
    public boolean hasCode(Long companyId, String code, Long id) throws SQLException {
        return jobRepository.hasCode(companyId, code, id) > 0 ? true : false;
    }

    @Override
    public List<Select2Dto> getListSelect2DtoByCompanyId(String term, Long companyId, boolean isPaging)
            throws SQLException {
        if (null != companyId && companyId.equals(0L)) {
            companyId = UserProfileUtils.getCompanyId();
        }
        return jobRepository.getListSelect2DtoByCompanyId(term, companyId, isPaging);
    }

}
