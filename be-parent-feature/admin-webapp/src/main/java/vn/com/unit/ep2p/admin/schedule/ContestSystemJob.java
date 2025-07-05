package vn.com.unit.ep2p.admin.schedule;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.dto.Db2ContestSummaryParamDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;
import vn.com.unit.ep2p.admin.entity.ContestApplicableDetailAdmin;
import vn.com.unit.ep2p.admin.entity.ContestSummaryAdmin;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.ContestSummaryAdminService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.QrtzMJobLogWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobScheduleWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ContestSystemJob implements Job {

	@Autowired
	QrtzMJobLogWebappService jobLogService;

	@Autowired
	QrtzMJobScheduleWebappService jobScheduleService;

	@Autowired
	SystemLogsService systemLogsService;

	@Autowired
	QrtzMJobWebappService jobService;

	@Autowired
	Db2ApiService db2ApiService;

	@Autowired
	private Db2ApiService db2Service;

	@Autowired
	private ContestSummaryAdminService contestSummaryAdminService;

	private static final Logger logger = LoggerFactory.getLogger(ContestSystemJob.class);

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
			// Get job detail
			jobSchedule = jobScheduleService.getJobScheduleByNameRef(context.getJobDetail().getKey().getName());
			companyId = jobSchedule.getCompanyId();
			job = jobService.getByJobGroup(companyId, groupCode);
			/** TaiTT comment log system job */
			// Log start job
			/*
			 * systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE,
			 * job.getJobName() + " start", LogTypeEnum.INFO.toInt(), serverAddress + " - "
			 * + context.getJobDetail().getKey().getName(), "JOB", companyId, null);
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
			jobLog = jobLogService.save(jobLog);

			jobScheduleService.updateScheduleJob(jobSchedule, JobStatus.RUNNING, ValidFlagEnum.ACTIVED);

			// ------push notify------

			try {
				Db2SummaryDto summary = new Db2SummaryDto();
				// check contest save detail
				List<ContestSummaryAdmin> entity = contestSummaryAdminService.getContestSaveDetail(new Date());
				if(entity != null) {
					if (entity.size() > 0) {
						ContestSummaryAdmin findValue = entity.stream()
								.filter(x -> StringUtils.endsWithIgnoreCase(x.getApplicableObject(), "ALL")).findAny()
								.orElse(null);
						List<Db2SummaryDto> getAllAgent = null;
						if (findValue != null) {
							// GET ALL
							getAllAgent = db2Service.getAllAgent();
							logger.error("Get all agent : " + getAllAgent.size());
						}
						for (ContestSummaryAdmin ls : entity) {
							if (ls.isOds() == true) {
								summary = db2Service.findAllAgentType(ls.getMemoNo());
							}
							ConnectDb2(summary, ls, ls.getApplicableObject(), ls.isOds(), getAllAgent, ls.getId());
						}
					} else {
						logger.error("CAN NOT CONTEST");
					}
				}

			} catch (Exception e) {
				logger.error("##Error##" + e);
			}
			// ------END------

			System.out.println("SampleJob run at: "
					+ CommonDateUtil.formatDateToString(new Date(), CommonDateUtil.YYYYMMDDHHMMSSFFF));
			TimeUnit.SECONDS.sleep(30);

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
			logger.error("## SampleJob ##", e);

		} finally {
			jobLog.setEndTime(endTime);
			jobLog = jobLogService.save(jobLog);
			jobScheduleService.save(jobSchedule);
			/** TaiTT comment log system job */
			// Log end job
			/*
			 * systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE,
			 * job.getJobName() + " end", LogTypeEnum.INFO.toInt(), serverAddress + " - " +
			 * context.getJobDetail().getKey().getName(), "JOB", companyId, null);
			 */
		}

		// Log end
		logger.info(String.format("END (%s)! - %s", serverAddress, groupCode));
	}

	public void ConnectDb2(Db2SummaryDto summary, ContestSummaryAdmin entity, String applicableObject, boolean isOds,
			List<Db2SummaryDto> getAllAgent, Long id) throws SQLException {
		Db2ContestSummaryParamDto db2 = new Db2ContestSummaryParamDto();
		if (isOds == true) {
			if (summary != null) {
				entity.setTerritory(null);
				entity.setArea(null);
				entity.setRegion(null);
				entity.setOffice(null);
				entity.setPosition(null);
				entity.setContestName(null);
				callStoreDb2(db2, summary, entity);
				List<Db2SummaryDto> lstData = db2.lstAgent;
				contestSummaryAdminService.saveDetail(lstData, id);
			}
		} else {
			if (StringUtils.equalsIgnoreCase(applicableObject, "ALL")) {
				contestSummaryAdminService.saveDetail(getAllAgent, id);
			} else if (StringUtils.equalsIgnoreCase(applicableObject, "IMP")) {
				entity.setTerritory(null);
				entity.setArea(null);
				entity.setRegion(null);
				entity.setOffice(null);
				entity.setPosition(null);
				entity.setReportingtoCode(null);
				summary = null;
				callStoreDb2(db2, summary, entity);
				List<Db2SummaryDto> lstData = db2.lstAgent;
				contestSummaryAdminService.saveDetail(lstData, id);
			} else {
				summary = null;
				if(StringUtils.isNotBlank(entity.getTerritory()) 
						|| StringUtils.isNotBlank(entity.getArea()) 
						|| StringUtils.isNotBlank(entity.getRegion())
						|| StringUtils.isNotBlank(entity.getOffice())
						|| StringUtils.isNotBlank(entity.getPosition())
						|| StringUtils.isNotBlank(entity.getReportingtoCode())) {
				callStoreDb2(db2, summary, entity);
				List<Db2SummaryDto> lstData = db2.lstAgent;
				contestSummaryAdminService.saveDetail(lstData, id);
				}
			}
		}
		// update check save detail
		contestSummaryAdminService.updateCheckSave(id);

	}

	public void callStoreDb2(Db2ContestSummaryParamDto db2, Db2SummaryDto summary, ContestSummaryAdmin entity) {

		db2.territory = StringUtils.isBlank(entity.getTerritory()) ? null: (";" + entity.getTerritory().replace(",", ";") + ";");
		db2.agentCode = null;
		db2.region = StringUtils.isBlank(entity.getRegion()) ? null: (";" + entity.getRegion().replace(",", ";") + ";");
		db2.area = StringUtils.isBlank(entity.getArea()) ? null : (";" + entity.getArea().replace(",", ";") + ";");
		db2.office = StringUtils.isBlank(entity.getOffice()) ? null: (";" + entity.getOffice().replace(",", ";") + ";");
		db2.leader = entity.getReportingtoCode();
		if (summary != null) {
			db2.agentType = StringUtils.isBlank(summary.getAgentType()) ? null: (";" + summary.getAgentType().replace(",", ";") + ";");
		} else {
			db2.agentType = StringUtils.isBlank(entity.getPosition()) ? null: (";" + entity.getPosition().replace(",", ";") + ";");
		}
		db2Service.callStoreAgentDetailDb2("RPT_ODS.DS_SP_GET_ODS_AGENT_DETAIL", db2);
	}
}
