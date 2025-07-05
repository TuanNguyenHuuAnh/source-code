/*******************************************************************************
 * Class        ：QrtzMJobScheduleServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.CronDto;
import vn.com.unit.quartz.job.dto.QrtzMJobLogDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.dto.QrtzTriggerDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;
import vn.com.unit.quartz.job.enumdef.StartTypeEnum;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;
import vn.com.unit.quartz.job.repository.QrtzMJobLogRepository;
import vn.com.unit.quartz.job.repository.QrtzMJobScheduleRepository;
import vn.com.unit.quartz.job.scheduler.ScheduleTask.JobStatus;
import vn.com.unit.quartz.job.service.QrtzMJobLogService;
import vn.com.unit.quartz.job.service.QrtzMJobScheduleService;
import vn.com.unit.quartz.job.service.QrtzMJobService;
import vn.com.unit.quartz.job.service.QrtzMScheduleService;

/**
 * <p>
 * QrtzMJobScheduleServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional
public class QrtzMJobScheduleServiceImpl extends AbstractQrtzJobService implements QrtzMJobScheduleService {

   
    /** The Constant REGEX_SLASH. */
    private static final String REGEX_SLASH = "/";

    /** The Constant REGEX_COLON. */
    private static final String REGEX_COLON = ":";

    /** The Constant REGEX_VERTICAL_LINE. */
    public static final String REGEX_VERTICAL_LINE = "|";

    /** The Constant REGEX_QUESTION_MARK. */
    public static final String REGEX_QUESTION_MARK = "?";

    /** The Constant DATE_PATTERN_INDEX. */
    private static final int[] DATE_PATTERN_INDEX = { 4, 7 };

    /** The Constant DATE_REVERSE_PATTERN_INDEX. */
    private static final int[] DATE_REVERSE_PATTERN_INDEX = { 2, 5 };

    /** The Constant TIME_PATTERN_INDEX. */
    private static final int[] TIME_PATTERN_INDEX = { 2, 5 };

    /** The Constant HOUR_PATTERN. */
    private static final String HOUR_PATTERN = "hh:mm:ss";
    
    public static final String DATE_PATTERN = "yyyy/MM/dd";

    /** The Constant CALENDAR_NAME. */
    public static final String CALENDAR_NAME = "weeklyOff";

    /** The job schedule repository. */
    @Autowired
    private QrtzMJobScheduleRepository jobScheduleRepository;

    /** The job log repository. */
    @Autowired
    private QrtzMJobLogRepository jobLogRepository;

    /** The schedule service. */
    @Autowired
    private QrtzMScheduleService scheduleService;

    /** The job service. */
    @Autowired
    private QrtzMJobService jobService;


    /** The job log service. */
    @Autowired
    private QrtzMJobLogService jobLogService;


    /** The scheduler. */
    @Autowired
    private Scheduler scheduler;

    /** The model mapper. */
    private ModelMapper modelMapper = new ModelMapper();
    
    private static final Long SYSTEM_ID = 0l;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#createScheduler(vn.com.unit.quartz.job.entity.QrtzMSchedule,
     * vn.com.unit.quartz.job.entity.QrtzMJob)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object[] createScheduler(QrtzMSchedule schedule, QrtzMJob job) throws DetailException {
        JobDetail jobDetail;
        try {
            jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClassName()))
                    .withIdentity(Key.createUniqueName(null), job.getJobGroup()).build();

            CronTrigger crTrigger = TriggerBuilder.newTrigger().withIdentity(jobDetail.getKey().getName(), job.getJobGroup())
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(schedule.getCronExpression()).withMisfireHandlingInstructionFireAndProceed())
                    .forJob(jobDetail.getKey()).build();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, crTrigger);
            return new Object[] { jobDetail.getKey().getName(), crTrigger.getNextFireTime() };
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException(e.getMessage());
        }

    }
    
    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#getCronExpression(java.util.Date)
     */
    @Override
    public String getCronExpression(Date date) {
        return new CronDto().addDate(date).getPattern();
    }

    /**
     * <p>
     * Get current date with delay time.
     * </p>
     *
     * @author khadm
     * @return {@link Date}
     */
    private Date getCurrentDateWithDelayTime() {
         Long delayTime = 30l;
         return new Date(System.currentTimeMillis() + delayTime);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#getDateFormatter()
     */
    @Override
    public SimpleDateFormat getDateFormatter() {
         String dateTimePattern = DATE_PATTERN.concat(" ").concat(HOUR_PATTERN);
         return new SimpleDateFormat(dateTimePattern);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#getJobScheduleByNameRef(java.lang.String)
     */
    @Override
    public QrtzMJobSchedule getJobScheduleByNameRef(String jobNameRef) {
        QrtzMJobSchedule qJobSchedule = jobScheduleRepository.getJobScheduleByNameRef(jobNameRef);
        return qJobSchedule == null ? new QrtzMJobSchedule() : qJobSchedule;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#haveEditPermission(java.lang.String)
     */
    @Override
    public boolean haveEditPermission(String user) {
        // return user != null && user.equalsIgnoreCase(UserProfileUtils.getUserNameLogin());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#pauseScheduler(java.lang.Long)
     */
    @Override
    public void pauseScheduler(Long id) throws DetailException {
        try {
            QrtzMJobSchedule jobSchedule = mapper.convertValue(detail(id), QrtzMJobSchedule.class);

            JobKey jobKey = new JobKey(jobSchedule.getJobNameRef(), jobSchedule.getJobGroup());

            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseJob(jobKey);
            }

            updateScheduleJob(jobSchedule, JobStatus.PAUSED, ValidFlagEnum.CANCELED_NEW);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#refactorDate(java.lang.String)
     */
    @Override
    public String refactorDate(String date) throws ParseException {
        String dateWithInterval = refactorDbDateTime(date, DATE_PATTERN_INDEX, REGEX_SLASH);
        List<String> dateElementList = Arrays.asList(dateWithInterval.split(REGEX_SLASH));
        List<String> reverseDateElementList = Lists.reverse(dateElementList);
        return refactorDbDateTime(String.join(StringUtils.EMPTY, reverseDateElementList), DATE_REVERSE_PATTERN_INDEX, REGEX_SLASH);
    }

    /**
     * <p>
     * Refactor db date time.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link String}
     * @param indexes
     *            type {@link int[]}
     * @param interval
     *            type {@link String}
     * @return {@link String}
     */
    private String refactorDbDateTime(String value, int[] indexes, String interval) {
        StringBuilder refactoredDate = new StringBuilder(value);
        Arrays.stream(indexes).forEach(index -> refactoredDate.insert(index, interval));
        return refactoredDate.toString();
    }


    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#resumeScheduler(java.lang.Long)
     */
    @Override
    public void resumeScheduler(Long id) throws DetailException {
        try {

            QrtzMJobSchedule jobSchedule = mapper.convertValue(detail(id), QrtzMJobSchedule.class);

            JobKey jobKey = new JobKey(jobSchedule.getJobNameRef(), jobSchedule.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                scheduler.resumeJob(jobKey);
            }
            updateScheduleJob(jobSchedule, JobStatus.WAITING, ValidFlagEnum.ACTIVED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#runScheduler(java.lang.Long)
     */
    @Override
    public void runScheduler(Long id) throws DetailException {
        try {
            QrtzMJobSchedule jobSchedule = jobScheduleRepository.findOne(id);
            QrtzMJobLog jobLog = jobLogRepository.findOne(id);
            if (jobSchedule == null) {
                jobSchedule = modelMapper.map(jobLog, QrtzMJobSchedule.class);
            }
            if (JobStatus.ERROR.getLongValue() == jobSchedule.getStatus()) {
                jobSchedule.setDescription(null);
            }

            //jobSchedule.setId(null);
            //jobSchedule.setSchedCode(null);
            jobSchedule.setStartTime(getCurrentDateWithDelayTime());

            createJobSchedule(jobSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#createJobSchedule(vn.com.unit.quartz.job.entity.QrtzMJobSchedule)
     */
    @Override
    public QrtzMJobSchedule createJobSchedule(QrtzMJobSchedule jobSchedule) throws DetailException {

        QrtzMSchedule schedMaster = jobSchedule.getSchedId() == null ? new QrtzMSchedule()
                : scheduleService.getByScheduleId(jobSchedule.getSchedId(), jobSchedule.getCompanyId());

        QrtzMJob qJobMaster = jobService.getByJobId(jobSchedule.getJobId());
        SimpleDateFormat formattter = getDateFormatter();

        if (StartTypeEnum.RECURRING.getValue().equals(jobSchedule.getStartType())) {
            Date endTime;
            try {
                endTime = formattter.parse(refactorDate(schedMaster.getEndDate()) + StringUtils.SPACE
                        + refactorDbDateTime(schedMaster.getEndTime(), TIME_PATTERN_INDEX, REGEX_COLON));
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DetailException(e.getMessage());
            }
            jobSchedule.setEndTime(endTime);
        } else if (StartTypeEnum.ONE_TIME.getValue().equals(jobSchedule.getStartType())) {
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

        jobSchedule.setStatus(JobStatus.WAITING.getLongValue());
        jobSchedule.setValidflag(ValidFlagEnum.ACTIVED.toLong());
        jobSchedule.setCreatedDate(new Date());
        jobSchedule.setCreatedId(SYSTEM_ID);
        return jobScheduleRepository.create(jobSchedule);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#deleteScheduler(java.lang.Long)
     */
    @Override
    public void deleteScheduler(Long id) throws DetailException {
        try {
            if (null == id) {
                throw new DetailException("ID NULL");
            }
            QrtzMJobSchedule jobSchedule = mapper.convertValue(detail(id), QrtzMJobSchedule.class);

            QrtzMJobLog jobLog;

            if (jobSchedule != null && jobSchedule.getId() != null) {

                JobKey jobKey = new JobKey(jobSchedule.getJobNameRef(), jobSchedule.getJobGroup());
                if (scheduler.checkExists(jobKey)) {
                    scheduler.deleteJob(jobKey);
                }
                jobSchedule.setDeletedId(SYSTEM_ID);
                jobSchedule.setDeletedDate(new Date());
                updateScheduleJob(jobSchedule, JobStatus.STOP, ValidFlagEnum.DELETED);
                jobLog = jobLogService.getByJobNameRefAndStartTime(id);
            } else {
                jobLog = jobLogRepository.findOne(id);
            }

            if (jobLog.getId() != null) {
                jobLog.setStatus(JobStatus.STOP.getLongValue());
                jobLog.setValidflag(ValidFlagEnum.DELETED.toLong());
                jobLogService.create(mapper.convertValue(jobLog, QrtzMJobLogDto.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException(e.getMessage());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#updateScheduleJob(vn.com.unit.quartz.job.entity.QrtzMJobSchedule,
     * vn.com.unit.quartz.job.scheduler.ScheduleTask.JobStatus, vn.com.unit.quartz.job.enumdef.ValidFlagEnum)
     */
    @Override
    public void updateScheduleJob(QrtzMJobSchedule jobSchedule, JobStatus jobStatus, ValidFlagEnum validFlag) throws DetailException {
        jobSchedule.setStatus(jobStatus.getLongValue());
        jobSchedule.setValidflag(validFlag.toLong());
        update(mapper.convertValue(jobSchedule, QrtzMJobScheduleDto.class));
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobScheduleService#updateScheduler(vn.com.unit.quartz.job.entity.QrtzMSchedule,
     * java.lang.String, vn.com.unit.quartz.job.entity.QrtzMJob)
     */
    @Override
    public Object[] updateScheduler(QrtzMSchedule schedule, String jobName, QrtzMJob qJobMaster) throws DetailException {
        try {
            QrtzTriggerDto triggerDto = jobScheduleRepository.getTriggerByJobName(jobName);
            if (triggerDto == null) {
                return createScheduler(schedule, qJobMaster);
            } else {
                TriggerKey triggerKey = new TriggerKey(triggerDto.getTriggerName(), triggerDto.getTriggerGroup());
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCronExpression())
                        .withMisfireHandlingInstructionFireAndProceed();
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey, trigger);
                return new Object[] { triggerDto.getJobName(), trigger.getNextFireTime() };
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException(e.getMessage());
        }
    }

    
    /**
     * <p>
     * Checks if is schedule exists.
     * </p>
     *
     * @author khadm
     * @param jobSchedule
     *            type {@link QrtzMJobSchedule}
     * @return true, if is schedule exists
     */
    private boolean isScheduleExists(QrtzMJobSchedule jobSchedule) {
        return jobSchedule.getSchedId() != null;
    }


    ///////////////////////////////////////// NEW////////////////////////////////////////

    @Override
    public QrtzMJobScheduleDto create(QrtzMJobScheduleDto qrtzMJobScheduleDto) throws DetailException {
        if (null == qrtzMJobScheduleDto) {
            throw new DetailException("REQUSET NULL");
        }
        QrtzMJobSchedule entity = createJobSchedule(mapper.convertValue(qrtzMJobScheduleDto, QrtzMJobSchedule.class));
        return mapper.convertValue(entity, QrtzMJobScheduleDto.class);
    }

    @Override
    public QrtzMJobScheduleDto update(QrtzMJobScheduleDto qrtzMJobSchedule) throws DetailException {
        if (null == qrtzMJobSchedule) {
            throw new DetailException("REQUSET NULL");
        }
        if (null != qrtzMJobSchedule.getId()) {
            QrtzMJobSchedule entity = jobScheduleRepository.findOne(qrtzMJobSchedule.getId());
            if (null != entity) {
                entity = mapper.convertValue(qrtzMJobSchedule, QrtzMJobSchedule.class);
                entity.setUpdatedDate(new Date());
                entity.setUpdatedId(SYSTEM_ID);
                entity = jobScheduleRepository.update(entity);
                return mapper.convertValue(entity, QrtzMJobScheduleDto.class);
            } else {
                throw new DetailException("NOT FOUNT");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }
    
    @Override
    public List<QrtzMJobScheduleDto> list(QrtzMJobScheduleSearchDto searchDto, Pageable pageable) throws DetailException {
        return jobScheduleRepository.getJobSchedules(searchDto, pageable).getContent();
    }

    @Override
    public int count(QrtzMJobScheduleSearchDto searchDto) throws DetailException {
        return jobScheduleRepository.countJobScheduleByCondition(searchDto);
    }

    @Override
    public QrtzMJobScheduleDto detail(Long id) throws DetailException {
        if (null != id) {
            QrtzMJobSchedule entity = jobScheduleRepository.findOne(id);
            if (null != entity) {
                QrtzMJobScheduleDto qrtzMJobScheduleDto = mapper.convertValue(entity, QrtzMJobScheduleDto.class);
                return qrtzMJobScheduleDto;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("REQUEST NULL");
        }
    }
     
}
