package vn.com.unit.ep2p.admin.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.utils.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.config.PageSizeConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.controller.QuartzController;
import vn.com.unit.ep2p.admin.dto.CronDto;
import vn.com.unit.ep2p.admin.dto.QrtzTriggerDto;
import vn.com.unit.ep2p.admin.repository.AppQrtzMJobLogRepository;
import vn.com.unit.ep2p.admin.repository.AppQrtzMJobScheduleRepository;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.QrtzMJobLogWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobScheduleWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMScheduleWebappService;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;
@Service
@Transactional
public class QrtzMJobScheduleWebappServiceImpl implements QrtzMJobScheduleWebappService {
	
	private static final Logger logger = LoggerFactory.getLogger(QrtzMJobScheduleWebappServiceImpl.class);

	private static final String REGEX_SLASH = "/";

	private static final String REGEX_COLON = ":";

	public static final String REGEX_VERTICAL_LINE = "|";

	public static final String REGEX_QUESTION_MARK = "?";

	private static final String DELAY_TIME = "DELAY_TIME";

	private static final int[] DATE_PATTERN_INDEX = { 4, 7 };

	private static final int[] DATE_REVERSE_PATTERN_INDEX = { 2, 5 };

	private static final int[] TIME_PATTERN_INDEX = { 2, 5 };

	private static final String HOUR_DB_PATTERN = "hh24:mi:ss";

	private static final String HOUR_PATTERN = "hh:mm:ss";
	public  static final String CALENDAR_NAME = "weeklyOff";
	
	private static final String HOUR24_PATTERN = "kk:mm:ss";

	@Autowired
	AppQrtzMJobScheduleRepository jobScheduleRepository;

	@Autowired
	AppQrtzMJobLogRepository jobLogRepository;

	@Autowired
	QrtzMScheduleWebappService scheduleService;

	@Autowired
	QrtzMJobWebappService jobService;

	@Autowired
	PageSizeConfig pageSizeConfig;

	@Autowired
	QrtzMJobLogWebappService jobLogService;
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	Scheduler scheduler;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	MessageSource msg;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public QrtzMJobSchedule createScheduleJob(QrtzMJobSchedule jobSchedule, JobStatus jobStatus, ValidFlagEnum validFlag)
			throws Exception {
		jobSchedule.setStatus(jobStatus.getLongValue());
		jobSchedule.setValidflag(validFlag.toLong());
		jobSchedule.setCreatedDate(new Date());
		jobSchedule.setCreatedId(UserProfileUtils.getAccountId());
		if (jobSchedule.getId() != null) {
		    return jobScheduleRepository.update(jobSchedule); 
        } else {
            return jobScheduleRepository.create(jobSchedule);
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] createScheduler(QrtzMSchedule schedule, QrtzMJob job) throws Exception {
		JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClassName()))
				.withIdentity(Key.createUniqueName(null), job.getJobGroup()).build();
		
		CronTrigger crTrigger = TriggerBuilder.newTrigger().withIdentity(jobDetail.getKey().getName(), job.getJobGroup()).withSchedule(CronScheduleBuilder
				.cronSchedule(schedule.getCronExpression()).withMisfireHandlingInstructionFireAndProceed()).forJob(jobDetail.getKey()).build();

		scheduler.start();
		scheduler.scheduleJob(jobDetail, crTrigger);
		return new Object[] { jobDetail.getKey().getName(), crTrigger.getNextFireTime() };
	}

	@Override
	public QrtzMJobSchedule getById(Long id) {
		if (id == null) {
			return new QrtzMJobSchedule();
		}
		return jobScheduleRepository.findOne(id);
	}

	@Override
	public String getCronExpression(Date date) {
		return new CronDto().addDate(date).getPattern();
	}

	private Date getCurrentDateWithDelayTime() {
		Long delayTime = Long.parseLong(systemConfig.getConfig(DELAY_TIME));
		return new Date(System.currentTimeMillis() + delayTime);
	}

	@Override
	public SimpleDateFormat getDateFormatter() {
	    String dateTimePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN).concat(" ").concat(HOUR_PATTERN);
		return new SimpleDateFormat(dateTimePattern);
	}

	@Override
	public QrtzMJobSchedule getJobScheduleByNameRef(String jobNameRef) {
		QrtzMJobSchedule qJobSchedule = jobScheduleRepository.getJobScheduleByNameRef(jobNameRef);
		return qJobSchedule == null ? new QrtzMJobSchedule() : qJobSchedule;
	}

	@Override
	public PageWrapper<QrtzMJobScheduleDto> getJobSchedules(QrtzMJobScheduleSearchDto qMJobSchedule, int pageSize,
			int page) {
		//qMJobSchedule.setStatus(refactorListStatus(qMJobSchedule.getStatus()));
		List<Integer> listPageSize = pageSizeConfig.getListPage(pageSize, systemConfig);
		int sizeOfPage = pageSizeConfig.getSizeOfPage(listPageSize, pageSize, systemConfig);
		PageWrapper<QrtzMJobScheduleDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
		pageWrapper.setListPageSize(listPageSize);
		pageWrapper.setSizeOfPage(sizeOfPage);
		
        if (StringUtils.isNotBlank(qMJobSchedule.getStatus())) {
            String[] lst = qMJobSchedule.getStatus().split(",");
            List<String> listSt = new ArrayList<>();
            for (String st : lst) {
                listSt.add(st);
            }
            qMJobSchedule.setStatusList(listSt);
        }
		
		List<QrtzMJobScheduleDto> result = new ArrayList<>();
		try {
			DateFormat dateFormat = new SimpleDateFormat(systemConfig.getConfig(SystemConfig.DATE_PATTERN));
			Date stDate = (qMJobSchedule.getStartTime() == null || StringUtils.isBlank(qMJobSchedule.getStartTime())) ? 
					null : CommonDateUtil.removeTime(dateFormat.parse(qMJobSchedule.getStartTime()));
			Date endDate = (qMJobSchedule.getEndTime() == null || StringUtils.isBlank(qMJobSchedule.getStartTime())) ? 
					null : CommonDateUtil.setMaxTime(dateFormat.parse(qMJobSchedule.getEndTime()));
			qMJobSchedule.setStartDate(stDate);
			qMJobSchedule.setEndDate(endDate);
			qMJobSchedule.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
			qMJobSchedule.setCompanyIdList(UserProfileUtils.getCompanyIdList());
			int count = jobScheduleRepository.countJobScheduleByCondition(qMJobSchedule);
			if (count > 0) {
				int currentPage = pageWrapper.getCurrentPage();
	            //int startIndex = (currentPage - 1) * sizeOfPage;
				int startIndex = CommonUtil.verifyOverflowStartIndex(currentPage, sizeOfPage);
				String dateTimePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN).concat(" ")
						.concat(HOUR_DB_PATTERN);
				List<Long> companyIds = UserProfileUtils.getCompanyIdList();
				result = jobScheduleRepository.getJobSchedules(qMJobSchedule, companyIds, dateTimePattern, startIndex, sizeOfPage);
			}
			pageWrapper.setDataAndCount(result, count);
		} catch (Exception e) {
			logger.error("#getJobSchedules", e);
		}
		return pageWrapper;
	}

	@Override
	public boolean haveEditPermission(String user) {
		return user != null && user.equalsIgnoreCase(UserProfileUtils.getUserNameLogin());
	}

	@Override
	public void initCreateScreen(ModelAndView mav, Long id, Long companyId, Locale locale) throws Exception {
		QrtzMJobSchedule jobSchedule = new QrtzMJobSchedule();
		if(id != null) {
			jobSchedule = getById(id);
			companyId = jobSchedule.getCompanyId();
			// set url ajax
            String url = QuartzController.CONTROLLER_QUARTZ.concat(QuartzController.CONTROLLER_JOB_SCHEDULE).concat(QuartzController.CONTROLLER_UPSERT).concat("?id=").concat(id.toString()).substring(1);
            jobSchedule.setUrl(url);
		}else {
		    companyId = UserProfileUtils.getCompanyId();
		    jobSchedule.setCompanyId(companyId);
		}
		Select2Dto job = companyId == null ? new Select2Dto() : jobService.getSelect2ByJobIdAndCompanyId(jobSchedule.getJobId(), companyId);
		Select2Dto schedule = companyId == null ? new Select2Dto() : scheduleService.getSelect2ByJobIdAndCompanyId(jobSchedule.getSchedId(), companyId);
		Select2Dto company = companyId == null ? new Select2Dto() : jobService.getCompanyById(companyId);
		
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), false);
		if(companyList != null && companyList.size() == 1) {
			Long comId = Long.valueOf(companyList.get(0).getId());
			jobSchedule.setCompanyId(comId);
		}
		if(null != jobSchedule.getStartTime()) {
		    SimpleDateFormat simpDate = new SimpleDateFormat(HOUR24_PATTERN);
		    String startTimeString = simpDate.format(jobSchedule.getStartTime());
		    jobSchedule.setStartTimeString(startTimeString);
		}
		mav.addObject("companyList", companyList);
		mav.addObject("company", company);
		mav.addObject("job", job);
		mav.addObject("schedule", schedule);
		mav.addObject("jobSchedule", jobSchedule);
		
		if(jobSchedule.getSchedId()!= null) {
		    mav.addObject(QuartzController.RECURRING, QuartzController.ON);
		}else {
		    mav.addObject(QuartzController.ONETIME, QuartzController.ON);
		}
	}
	
	@Override
	public void initCreateScreenForError(ModelAndView mav, QrtzMJobSchedule scheduleJob, Long companyId, Locale locale) {
		mav.addObject("jobSchedule", scheduleJob);
		Select2Dto job = jobService.getSelect2ByJobIdAndCompanyId(scheduleJob.getJobId(), companyId);
		Select2Dto schedule = scheduleService.getSelect2ByJobIdAndCompanyId(scheduleJob.getSchedId(), companyId);
        
         Select2Dto company = new Select2Dto(); try { company = jobService.getCompanyById(companyId); } catch (Exception e) {
         logger.error("#initCreateScreenForError", e); }
         
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), false);
		if(companyList != null && companyList.size() == 1) {
			Long comId = Long.valueOf(companyList.get(0).getId());
			scheduleJob.setCompanyId(comId);
		}
		mav.addObject("companyList", companyList);
		mav.addObject("company", company);
		mav.addObject("job", job);
		mav.addObject("schedule", schedule);
	}

	@Override
	public void pauseScheduler(Long id) throws Exception {
		QrtzMJobSchedule jobSchedule = getById(id);

		JobKey jobKey = new JobKey(jobSchedule.getJobNameRef(), jobSchedule.getJobGroup());
		if (scheduler.checkExists(jobKey)) {
			scheduler.pauseJob(jobKey);
		}
		updateScheduleJob(jobSchedule, JobStatus.PAUSED, ValidFlagEnum.CANCELED_NEW);
	}

	@Override
	public String refactorDate(String date) throws ParseException {
		String dateWithInterval = refactorDbDateTime(date, DATE_PATTERN_INDEX, REGEX_SLASH);
		List<String> dateElementList = Arrays.asList(dateWithInterval.split(REGEX_SLASH));
		List<String> reverseDateElementList = Lists.reverse(dateElementList);
		return refactorDbDateTime(String.join(StringUtils.EMPTY, reverseDateElementList), DATE_REVERSE_PATTERN_INDEX, REGEX_SLASH);
	}

	private String refactorDbDateTime(String value, int[] indexes, String interval) {
		StringBuilder refactoredDate = new StringBuilder(value);
		Arrays.stream(indexes).forEach(index -> refactoredDate.insert(index, interval));
		return refactoredDate.toString();
	}

	@SuppressWarnings("unused")
    private String refactorListStatus(String status) {
		String target = ConstantCore.COMMA;
		String replacement = REGEX_VERTICAL_LINE.concat(REGEX_QUESTION_MARK);
		return status == null || status.isEmpty() ? StringUtils.EMPTY
				: REGEX_QUESTION_MARK.concat(status).replace(target, replacement);
	}

	@Override
	public void resumeScheduler(Long id) throws Exception {
		QrtzMJobSchedule jobSchedule = getById(id);

		JobKey jobKey = new JobKey(jobSchedule.getJobNameRef(), jobSchedule.getJobGroup());
		if (scheduler.checkExists(jobKey)) {
			scheduler.resumeJob(jobKey);
		}
		updateScheduleJob(jobSchedule, JobStatus.WAITING, ValidFlagEnum.ACTIVED);

	}

	@Override
	public void runScheduler(Long id) throws Exception {
		QrtzMJobSchedule jobSchedule = jobScheduleRepository.findOne(id);
		QrtzMJobLog jobLog = jobLogRepository.findOne(id);
		if (jobSchedule == null) {
			jobSchedule = modelMapper.map(jobLog, QrtzMJobSchedule.class);
		}
		if (JobStatus.ERROR.getLongValue() == jobSchedule.getStatus()) {
			jobSchedule.setDescription(null);
		}

		jobSchedule.setId(null);
		jobSchedule.setSchedId(null);
		jobSchedule.setStartTime(null);
		createJobSchedule(jobSchedule);
	}

	@Override
	public void save(QrtzMJobSchedule qScheduleJob) {
		jobScheduleRepository.save(qScheduleJob);
	}

	@Override
	public QrtzMJobSchedule createJobSchedule(QrtzMJobSchedule jobSchedule) throws Exception {

		QrtzMSchedule schedMaster = jobSchedule.getSchedId() == null ? new QrtzMSchedule() : scheduleService.getByScheduleId(jobSchedule.getSchedId(), jobSchedule.getCompanyId());

		QrtzMJob qJobMaster = jobService.getByJobId(jobSchedule.getJobId());
		SimpleDateFormat formattter = getDateFormatter();

		if (isScheduleExists(jobSchedule)) {
			Date endTime = formattter.parse(refactorDate(schedMaster.getEndDate()) + StringUtils.SPACE
					+ refactorDbDateTime(schedMaster.getEndTime(), TIME_PATTERN_INDEX, REGEX_COLON));
			jobSchedule.setEndTime(endTime);
		} else if (jobSchedule.getStartTime() != null) {
			schedMaster.setCronExpression(getCronExpression(jobSchedule.getStartTime()));
			jobSchedule.setStartTime(jobSchedule.getStartTime());
			jobSchedule.setEndTime(jobSchedule.getStartTime());

		} else {
			Date currentDate = getCurrentDateWithDelayTime();
			schedMaster.setCronExpression(getCronExpression(currentDate));
			jobSchedule.setStartTime(currentDate);
			jobSchedule.setEndTime(currentDate);
			Date stDate = CommonDateUtil.removeTime(currentDate);
			jobSchedule.setStartDate(stDate); 
		}

		Object[] triggerData;

		if (jobSchedule.getId() != null) {
			triggerData = updateScheduler(schedMaster, jobSchedule.getJobNameRef(), qJobMaster);
		} else {
			triggerData = createScheduler(schedMaster, qJobMaster);
		}

		String jobNameRef = (String) triggerData[0];

		if (isScheduleExists(jobSchedule)) {
			Date jobStartTime = (Date) triggerData[1];
			jobSchedule.setStartTime(jobStartTime);
		}
		Date stDate = CommonDateUtil.removeTime(jobSchedule.getStartTime());
		jobSchedule.setStartDate(stDate); 
		jobSchedule.setJobNameRef(jobNameRef);
		jobSchedule.setJobGroup(qJobMaster.getJobGroup());
		return createScheduleJob(jobSchedule, JobStatus.WAITING, ValidFlagEnum.ACTIVED);
	}

	@Override
	public void deleteScheduler(Long id) throws Exception {
		QrtzMJobSchedule jobSchedule = getById(id);

		QrtzMJobLog jobLog;

		if (jobSchedule != null && jobSchedule.getId() != null) {

			JobKey jobKey = new JobKey(jobSchedule.getJobNameRef(), jobSchedule.getJobGroup());
			if (scheduler.checkExists(jobKey)) {
				scheduler.deleteJob(jobKey);
			}
			updateScheduleJob(jobSchedule, JobStatus.STOP, ValidFlagEnum.DELETED);
			jobLog = jobLogService.getByJobNameRefAndStartTime(id);
		} else {
			jobLog = jobLogRepository.findOne(id);
		}

		if (jobLog.getId() != null) {
			jobLog.setStatus(JobStatus.STOP.getLongValue());
			jobLog.setValidflag(ValidFlagEnum.DELETED.toLong());
			jobLogService.save(jobLog);
		}

	}

	@Override
	public void updateScheduleJob(QrtzMJobSchedule jobSchedule, JobStatus jobStatus, ValidFlagEnum validFlag)
			throws Exception {
		jobSchedule.setStatus(jobStatus.getLongValue());
		jobSchedule.setValidflag(validFlag.toLong());
		jobScheduleRepository.update(jobSchedule);
	}

	@Override
	public Object[] updateScheduler(QrtzMSchedule schedule, String jobName, QrtzMJob qJobMaster) throws Exception {
		QrtzTriggerDto triggerDto = jobScheduleRepository.getTriggerByJobName(jobName);
		if(triggerDto == null) {
			return createScheduler(schedule, qJobMaster);
		} else {
			TriggerKey triggerKey = new TriggerKey(triggerDto.getTriggerName(), triggerDto.getTriggerGroup());
			CronTrigger trigger =(CronTrigger) scheduler.getTrigger(triggerKey);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCronExpression()).withMisfireHandlingInstructionFireAndProceed(); 
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
			scheduler.rescheduleJob(triggerKey, trigger);
			return new Object[] { triggerDto.getJobName(), trigger.getNextFireTime() };
		}
	}

	@Override
	public void initScheduleCreateScreen(ModelAndView mav, Long id) throws Exception {

		QrtzMSchedule scheduleEntity = new QrtzMSchedule();
		if (id != null) {
			scheduleEntity = scheduleService.getById(id);
			scheduleEntity.setStartDate(reverseDate(scheduleEntity.getStartDate()));
			scheduleEntity.setStartTime(reformatTime(scheduleEntity.getStartTime()));
			scheduleEntity.setEndDate(reverseDate(scheduleEntity.getEndDate()));
			scheduleEntity.setEndTime(reformatTime(scheduleEntity.getEndTime()));
			
			// format date
            String paternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            Date start = CommonDateUtil.formatStringToDate(scheduleEntity.getStartDate(), CoreConstant.DDMMYYYY_SLASH);
            Date end = CommonDateUtil.formatStringToDate(scheduleEntity.getEndDate(), CoreConstant.DDMMYYYY_SLASH);
            String startDate = CommonDateUtil.formatDateToString(start, paternDate);
            String endDate = CommonDateUtil.formatDateToString(end, paternDate);
            scheduleEntity.setStartDate(startDate);
            scheduleEntity.setEndDate(endDate);
			
			// set url ajax
            String url = QuartzController.CONTROLLER_QUARTZ.concat(QuartzController.CONTROLLER_SCHEDULE).concat(QuartzController.CONTROLLER_UPSERT).concat("?id=").concat(id.toString()).substring(1);
            scheduleEntity.setUrl(url);
		}else {
		    scheduleEntity.setCompanyId(UserProfileUtils.getCompanyId());
		}
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), false);
		if(companyList != null && companyList.size() == 1) {
			Long comId = Long.valueOf(companyList.get(0).getId());
			scheduleEntity.setCompanyId(comId);
		} else {
			Long comId = scheduleEntity.getCompanyId();		
			Select2Dto company = comId == null ? new Select2Dto() : jobService.getCompanyById(comId);
			mav.addObject("company", company);
		}
		mav.addObject("companyList", companyList);
		mav.addObject("schedule", scheduleEntity);
	}

	private boolean isScheduleExists(QrtzMJobSchedule jobSchedule) {
		return jobSchedule.getSchedId() != null;
	}

	private String reverseDate(String date) {
		return date.substring(6) + REGEX_SLASH + date.substring(4, 6) + REGEX_SLASH + date.substring(0, 4);
	}

	private String reformatTime(String time) {
		return time.substring(0, 2) + REGEX_COLON + time.substring(2, 4) + REGEX_COLON + time.substring(4);
	}
	
	/**
	 * createCutoffScheduler
	 * @param id
	 * @return
	 * @throws Exception
	 * @author HungHT
	 */
	@Override
    public Object[] createCutoffScheduler(Long id) throws Exception {
/*
        String jobGroup = String.format(CommonConstant.CUTOFF_JOBGROUP + "%d", id);

        Date currentDate = getCurrentDateWithDelayTime();

        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) CutoffJob.class).withIdentity(Key.createUniqueName(null), jobGroup)
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(getCronExpression(currentDate)).withMisfireHandlingInstructionFireAndProceed())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        return new Object[] { jobDetail.getKey().getName(), trigger.getNextFireTime() };
        */
		return null;
    }

    @Override
    public QrtzMJobSchedule createJobScheduleArchive(QrtzMJobSchedule jobSchedule, Long archiveId) throws Exception {

        QrtzMSchedule schedMaster = jobSchedule.getSchedId() == null ? new QrtzMSchedule() : scheduleService.getByScheduleId(jobSchedule.getSchedId(), jobSchedule.getCompanyId());

        QrtzMJob qJobMaster = jobService.getByJobId(jobSchedule.getJobId());
        SimpleDateFormat formattter = getDateFormatter();

        if (isScheduleExists(jobSchedule)) {
            Date endTime = formattter.parse(refactorDate(schedMaster.getEndDate()) + StringUtils.SPACE
                    + refactorDbDateTime(schedMaster.getEndTime(), TIME_PATTERN_INDEX, REGEX_COLON));
            jobSchedule.setEndTime(endTime);
        } else if (jobSchedule.getStartTime() != null) {
            schedMaster.setCronExpression(getCronExpression(jobSchedule.getStartTime()));
            jobSchedule.setStartTime(jobSchedule.getStartTime());
            jobSchedule.setEndTime(jobSchedule.getStartTime());

        } else {
            Date currentDate = getCurrentDateWithDelayTime();
            schedMaster.setCronExpression(getCronExpression(currentDate));
            jobSchedule.setStartTime(currentDate);
            jobSchedule.setEndTime(currentDate);
            Date stDate = CommonDateUtil.removeTime(currentDate);
            jobSchedule.setStartDate(stDate); 
        }

        Object[] triggerData;

        if (jobSchedule.getId() != null) {
            triggerData = updateScheduler(schedMaster, jobSchedule.getJobNameRef(), qJobMaster);
        } else {
            triggerData = createScheduler(schedMaster, qJobMaster, archiveId);
        }

        String jobNameRef = (String) triggerData[0];

        if (isScheduleExists(jobSchedule)) {
            Date jobStartTime = (Date) triggerData[1];
            jobSchedule.setStartTime(jobStartTime);
        }
        Date stDate = CommonDateUtil.removeTime(jobSchedule.getStartTime());
        jobSchedule.setStartDate(stDate); 
        jobSchedule.setJobNameRef(jobNameRef);
        jobSchedule.setJobGroup(qJobMaster.getJobGroup());
        return createScheduleJob(jobSchedule, JobStatus.WAITING, ValidFlagEnum.ACTIVED);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] createScheduler(QrtzMSchedule schedule, QrtzMJob job, Long archiveId) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClassName()))
                .withIdentity(Key.createUniqueName(null), job.getJobGroup()).build();
        jobDetail.getJobDataMap().put("archiveId", archiveId);
        
        CronTrigger crTrigger = TriggerBuilder.newTrigger().withIdentity(jobDetail.getKey().getName(), job.getJobGroup()).withSchedule(CronScheduleBuilder
                .cronSchedule(schedule.getCronExpression()).withMisfireHandlingInstructionFireAndProceed()).forJob(jobDetail.getKey()).build();

        scheduler.start();
        scheduler.scheduleJob(jobDetail, crTrigger);
        return new Object[] { jobDetail.getKey().getName(), crTrigger.getNextFireTime() };
    }
    
    @Override
    public Object[] updateScheduler(QrtzMSchedule schedule, String jobName, QrtzMJob qJobMaster, Long archiveId) throws Exception {
        QrtzTriggerDto triggerDto = jobScheduleRepository.getTriggerByJobName(jobName);
        if(triggerDto == null) {
            return createScheduler(schedule, qJobMaster, archiveId);
        } else {
            TriggerKey triggerKey = new TriggerKey(triggerDto.getTriggerName(), triggerDto.getTriggerGroup());
            CronTrigger trigger =(CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCronExpression()).withMisfireHandlingInstructionFireAndProceed(); 
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
            scheduler.rescheduleJob(triggerKey, trigger);
            return new Object[] { triggerDto.getJobName(), trigger.getNextFireTime() };
        }
    }

}
