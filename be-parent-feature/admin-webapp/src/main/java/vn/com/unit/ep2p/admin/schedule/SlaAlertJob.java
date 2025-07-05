package vn.com.unit.ep2p.admin.schedule;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.QrtzMJobLogWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobScheduleWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobStoreWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.utils.JsonUtils;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;
import vn.com.unit.common.utils.CommonDateUtil
;

/**
 * SlaAlertJob
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenTD
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SlaAlertJob implements Job {

	@Autowired
	QrtzMJobStoreWebappService jobStoreService;

	@Autowired
	QrtzMJobLogWebappService jobLogService;

	@Autowired
	QrtzMJobScheduleWebappService jobScheduleService;

	@Autowired
	SystemLogsService systemLogsService;

	@Autowired
	QrtzMJobWebappService jobService;
	
//	@Autowired
//	private SlaAlertService alertService;

	@Autowired
	EmailJobService emailJobService;

	private static final Logger logger = LoggerFactory.getLogger(SlaAlertJob.class);

	String groupCode;

	@Override
	public void execute(JobExecutionContext context) {

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

			// Log start
			logger.info(String.format("START (%s)! - %s", serverAddress, groupCode));

			// Check groupCode is not null
			if (groupCode == null) {
				throw new Exception("Group code is null!");
			}

			jobSchedule = jobScheduleService.getJobScheduleByNameRef(context.getJobDetail().getKey().getName());
			companyId = jobSchedule.getCompanyId();
			job = jobService.getByJobGroup(companyId, groupCode);
/** TaiTT comment log system job */
			/*			// Log start job
			systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE, job.getJobName() + " start", LogTypeEnum.INFO.toInt(),
					serverAddress + " - " + context.getJobDetail().getKey().getName(), "JOB", companyId, null);
*/
			jobLog.setSchedId(jobSchedule.getSchedId());
			jobLog.setJobId(jobSchedule.getJobId());
			jobLog.setJobNameRef(context.getJobDetail().getKey().getName());
			jobLog.setStatus(JobStatus.WAITING.getLongValue());
			jobLog.setValidflag(ValidFlagEnum.ACTIVED.toLong());
			jobLog.setJobGroup(context.getJobDetail().getKey().getGroup());
			jobLog.setDescription(jobSchedule.getDescription());
			jobLog.setCreatedId(UserProfileUtils.getAccountId());
			jobLog.setCreatedDate(jobSchedule.getCreatedDate());
			jobLog.setStartTime(context.getFireTime());
			Date startTime = CommonDateUtil.removeTime(context.getFireTime());
            jobLog.setStartDate(startTime);
			jobLog.setNextFireTime(context.getNextFireTime());
			jobLog.setCompanyId(companyId);
			logger.info("##SlaAlertJob - before excute alert job (QrtzMJobLog) : " + JsonUtils.convertObjectToJSON(jobLog));
			jobLog = jobLogService.save(jobLog);

			jobScheduleService.updateScheduleJob(jobSchedule, JobStatus.RUNNING, ValidFlagEnum.ACTIVED);

//			alertService.sendMailSlaAlertScheduled(SecurityContextHolder.getContext(), companyId);
			if (context.getNextFireTime() == null) {
				jobSchedule.setStartTime(context.getFireTime());
				Date stTime = CommonDateUtil.removeTime(context.getFireTime());
                jobSchedule.setStartDate(stTime);
				jobSchedule.setEndTime(endTime);
				jobSchedule.setStatus(JobStatus.COMPLETED.getLongValue());
				jobSchedule.setValidflag(ValidFlagEnum.HISTORY.toLong());
				jobScheduleService.save(jobSchedule);
			} else {
				jobSchedule.setStartTime(context.getNextFireTime());
				Date stTime = CommonDateUtil.removeTime(context.getNextFireTime());
                jobSchedule.setStartDate(stTime);
				jobSchedule.setStatus(JobStatus.WAITING.getLongValue());
				jobSchedule.setValidflag(ValidFlagEnum.ACTIVED.toLong());
				jobScheduleService.save(jobSchedule);
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
			// Log error
			logger.error("##SlaAlertJob", e);

		} finally {
			jobLog.setEndTime(endTime);
			jobLog = jobLogService.save(jobLog);
			jobScheduleService.save(jobSchedule);
			/** TaiTT comment log system job */
			// Log end job
						/*systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE, job.getJobName() + " end", LogTypeEnum.INFO.toInt(),
								serverAddress + " - " + context.getJobDetail().getKey().getName(), "JOB", companyId, null);*/
		}

		// Send notification
        try {
            emailJobService.sendEmailForAlertJob(SecurityContextHolder.getContext(), job, jobLog);
        } catch (Exception e) {
            /** TaiTT comment log system job */
/*            systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE, job.getJobName() + " send email failed",
                    LogTypeEnum.ERROR.toInt(), e.getMessage(), "JOB", companyId, null);*/
        }
        // Log end
        logger.info(String.format("END (%s)! - %s", serverAddress, groupCode));
	}

}
