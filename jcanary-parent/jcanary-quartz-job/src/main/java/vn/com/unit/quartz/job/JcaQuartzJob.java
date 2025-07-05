/*******************************************************************************
 * Class        ：JcaQuartzJob
 * Created date ：2021/01/22
 * Lasted date  ：2021/01/22
 * Author       ：TrieuVD
 * Change log   ：2021/01/22：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.quartz.job;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobLogDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.email.dto.QuartzEmailDto;
import vn.com.unit.quartz.job.email.dto.QuartzJobEmailTemplateDto;
import vn.com.unit.quartz.job.email.service.QuartzJobEmailService;
import vn.com.unit.quartz.job.email.service.QuartzJobEmailTemplateService;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;
import vn.com.unit.quartz.job.scheduler.ScheduleTask.JobStatus;
import vn.com.unit.quartz.job.service.QrtzMJobLogService;
import vn.com.unit.quartz.job.service.QrtzMJobScheduleService;
import vn.com.unit.quartz.job.service.QrtzMJobService;
import vn.com.unit.quartz.job.service.impl.AbstractQrtzJobService;

/**
 * JcaQuartzJob
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public abstract class JcaQuartzJob extends AbstractQrtzJobService implements Job {

    @Autowired
    private QrtzMJobLogService jobLogService;

    @Autowired
    private QrtzMJobScheduleService jobScheduleService;

    @Autowired
    private QrtzMJobService jobService;

    @Autowired
    private QuartzJobEmailService quartzJobEmailService;

    @Autowired
    private QuartzJobEmailTemplateService quartzJobEmailTemplateService;

    String groupCode;

    /**
     * <p>
     * Execute jca quartz job.
     * </p>
     *
     * @param context
     *            type {@link JobExecutionContext}
     * @author TrieuVD
     */
    protected abstract void executeJcaQuartzJob(JobExecutionContext context);

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        executeJcaQuartzJob(context);
        String serverAddress = null;
        InetAddress ip;
        QrtzMJobLog jobLog = new QrtzMJobLog();
        QrtzMJobSchedule jobSchedule = new QrtzMJobSchedule();
        QrtzMJob job = new QrtzMJob();
        try {
            // Get ip server
            ip = InetAddress.getLocalHost();
            serverAddress = ip.getHostAddress();

            // Get group code
            if (groupCode == null) {
                groupCode = context.getJobDetail().getKey().getGroup();
            }
        } catch (UnknownHostException ex) {
            serverAddress = null;
        } catch (Exception ex) {
            groupCode = null;
        }

        String errorMessage = null;
        @SuppressWarnings("unused")
        int errorCode = 0;
        Date endTime = new Date(context.getFireTime().getTime() + context.getJobRunTime());
        Long companyId = null;

        try {

            // Check groupCode is not null
            if (groupCode == null) {
                throw new Exception("Group code is null!");
            }
            // Get job detail
            jobSchedule = jobScheduleService.getJobScheduleByNameRef(context.getJobDetail().getKey().getName());
            companyId = jobSchedule.getCompanyId();
            job = jobService.getByJobGroup(companyId, groupCode);

            jobLog.setSchedId(jobSchedule.getSchedId());
            jobLog.setJobId(jobSchedule.getJobId());
            jobLog.setJobNameRef(context.getJobDetail().getKey().getName());
            jobLog.setStatus(JobStatus.WAITING.getLongValue());
            jobLog.setValidflag(ValidFlagEnum.ACTIVED.toLong());
            jobLog.setJobGroup(context.getJobDetail().getKey().getGroup());
            jobLog.setDescription(jobSchedule.getDescription());
            jobLog.setCreatedId(jobSchedule.getCreatedId());
            jobLog.setCreatedDate(jobSchedule.getCreatedDate());
            jobLog.setStartTime(context.getFireTime());
            Date startTime = CommonDateUtil.removeTime(context.getFireTime());
            jobLog.setStartDate(startTime);
            jobLog.setNextFireTime(context.getNextFireTime());
            jobLog.setCompanyId(companyId);
            QrtzMJobLogDto createQrtzMJobLogDto = jobLogService.create(mapper.convertValue(jobLog, QrtzMJobLogDto.class));
            jobLog = mapper.convertValue(createQrtzMJobLogDto, QrtzMJobLog.class);

            jobScheduleService.updateScheduleJob(jobSchedule, JobStatus.RUNNING, ValidFlagEnum.ACTIVED);

            //
            //TimeUnit.SECONDS.sleep(30);

            if (context.getNextFireTime() == null) {
                jobSchedule.setStartTime(context.getFireTime());
                Date stTime = CommonDateUtil.removeTime(context.getFireTime());
                jobSchedule.setStartDate(stTime);
                jobSchedule.setEndTime(endTime);
                jobSchedule.setStatus(JobStatus.COMPLETED.getLongValue());
                jobSchedule.setValidflag(ValidFlagEnum.HISTORY.toLong());
                jobScheduleService.update(mapper.convertValue(jobSchedule, QrtzMJobScheduleDto.class));
            } else {
                jobSchedule.setStartTime(context.getNextFireTime());
                Date stTime = CommonDateUtil.removeTime(context.getNextFireTime());
                jobSchedule.setStartDate(stTime);
                jobSchedule.setStatus(JobStatus.WAITING.getLongValue());
                jobSchedule.setValidflag(ValidFlagEnum.ACTIVED.toLong());
                jobScheduleService.create(mapper.convertValue(jobSchedule, QrtzMJobScheduleDto.class));
            }

            jobLog.setStatus(JobStatus.COMPLETED.getLongValue());
            jobSchedule.setErrorMessage(StringUtils.EMPTY);

        } catch (Exception e) {
            if (e.getCause() instanceof SQLException) {
                errorMessage = ((SQLException) e.getCause()).getMessage();
                errorCode = ((SQLException) e.getCause()).getErrorCode();
            } else {
                if (e.getCause() != null) {
                    errorMessage = e.getCause().toString();
                } else {
                    errorMessage = e.getMessage();
                }
            }
            jobLog.setStatus(JobStatus.ERROR.getLongValue());
            jobLog.setErrorMessage(errorMessage);

            if (context.getNextFireTime() == null) {
                jobSchedule.setStatus(JobStatus.ERROR.getLongValue());
                jobSchedule.setErrorMessage(errorMessage);
            } else {
                jobSchedule.setStartTime(context.getNextFireTime());
                jobSchedule.setStatus(JobStatus.WAITING.getLongValue());
            }

        } finally {
            jobLog.setEndTime(endTime);
            try {
                jobLogService.update(mapper.convertValue(jobLog, QrtzMJobLogDto.class));
                jobScheduleService.update(mapper.convertValue(jobSchedule, QrtzMJobScheduleDto.class));
            } catch (DetailException e) {
                e.printStackTrace();
            }

        }
        if (null != job && job.getSendNotification()) {
            QuartzEmailDto emailDto = new QuartzEmailDto();
            if (null != job.getRecipientAddress()) {
                List<String> toEmail = new ArrayList<>();
                toEmail.add(job.getRecipientAddress());
                emailDto.setToAddress(toEmail);
            }
            if (null != job.getCcAddress()) {
                List<String> ccEmail = new ArrayList<>();
                ccEmail.add(job.getCcAddress());
                emailDto.setCcAddress(ccEmail);
            }
            if (null != job.getBccAddress()) {
                List<String> bccEmail = new ArrayList<>();
                bccEmail.add(job.getBccAddress());
                emailDto.setBccAddress(bccEmail);
            }
            if (null != job.getEmailTemplate()) {
                QuartzJobEmailTemplateDto emailTemplate = quartzJobEmailTemplateService
                        .getQuartzJobEmailTemplateById(job.getEmailTemplate());
                if (null != emailTemplate) {
                    emailDto.setSubject(emailTemplate.getSubject());
                    emailDto.setEmailContent(emailTemplate.getTemplateContent());
                    emailDto.setContentType("text/html; charset=utf-8");
                }
            }
            quartzJobEmailService.sendEmail(emailDto);
        }
    }

}
