package vn.com.unit.ep2p.admin.schedule;

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
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.*;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class NotifySaveSystemJob implements Job {

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
    NotifyAdminService notifyAdminService;

    private static final Logger logger = LoggerFactory.getLogger(NotifySaveSystemJob.class);

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
                // check save detail
                List<Db2AgentDto> entity = notifyAdminService.getNotifySaveDetail(new Date());
                if(entity !=null) {            
	                if (entity.size() > 0) {
	                    Db2AgentDto findValue = entity.stream()
	                            .filter(x -> StringUtils.endsWithIgnoreCase(x.getApplicableObject(), "ALL")).findAny()
	                            .orElse(null);
	                    List<Db2AgentDto> getAllAgent = null;
	                    if (findValue != null) {
	                        // GET ALL
	                        getAllAgent = db2Service.getAllAgentCode();
	                        logger.error("Get all agent : " + getAllAgent.size());
	                    }
	                    for (Db2AgentDto ls : entity) {
	                        ConnectDb2(ls, ls.getApplicableObject(), getAllAgent, ls.getId());
	                    }
	                } else {
	                    logger.error("CAN NOT NOTIFY");
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

    public void ConnectDb2(Db2AgentDto data, String applicableObject, List<Db2AgentDto> getAllAgent, Long id) throws Exception {
        List<Db2AgentDto> dataInsert = new ArrayList<>();

        if (StringUtils.equalsIgnoreCase(applicableObject, "ALL")) {
            dataInsert = getAllAgent;
        }  else if (StringUtils.equalsIgnoreCase(applicableObject, "SEL")) {
            Db2AgentNotifyParamDto db2AgentNotifyParamDto = prepareDateCallStoreDb2(data);
            db2Service.callStoreDb2("RPT_ODS.DS_SP_GET_AGENT_NOTIFY_DETAIL", db2AgentNotifyParamDto);
            dataInsert = db2AgentNotifyParamDto.lstAgent;
        }
        if(dataInsert.size()>0) {
        	 //insert detail
            notifyAdminService.insertDetailToDatabaseWithBatch(dataInsert, id, data.getSendDate());
            //push notification to firebase
            if(data.isSendImmediately() == true){
                notifyAdminService.pushNotificationToFirebaseCloud(dataInsert, data);
                notifyAdminService.updateIsSend(id);
            }
        }    
	     // update check save detail
	        notifyAdminService.updateCheckSave(id);
    }

    private Db2AgentNotifyParamDto prepareDateCallStoreDb2(Db2AgentDto notify){

        Db2AgentNotifyParamDto db2AgentNotifyParamDto = new Db2AgentNotifyParamDto();
        db2AgentNotifyParamDto.territory = StringUtils.isBlank(notify.getTerritorry()) ? null : (";" + notify.getTerritorry() + ";");
        db2AgentNotifyParamDto.region = StringUtils.isBlank(notify.getRegion()) ? null : (";" + notify.getRegion() + ";");
        db2AgentNotifyParamDto.office = StringUtils.isBlank(notify.getOffice()) ? null : (";" + notify.getOffice() + ";");
        db2AgentNotifyParamDto.area = StringUtils.isBlank(notify.getArea()) ? null : (";" + notify.getArea() + ";");
        db2AgentNotifyParamDto.position = StringUtils.isBlank(notify.getPosition()) ? null : (";" + notify.getPosition() + ";");
        return db2AgentNotifyParamDto;
    }



}


