package vn.com.unit.ep2p.admin.schedule;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.*;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.*;
import vn.com.unit.ep2p.dto.NotifyAdminEditDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
class NotifyJob implements Job {

    @Autowired
    private Environment env;

    @Autowired
    QrtzMJobLogWebappService jobLogService;

    @Autowired
    QrtzMJobScheduleWebappService jobScheduleService;

    @Autowired
    SystemLogsService systemLogsService;

    @Autowired
    QrtzMJobWebappService jobService;

    @Autowired
    NotifyAdminService notifyService;

    @Autowired
    JcaAccountService jcaAccountService;

    private static final Logger logger = LoggerFactory.getLogger(NotifyJob.class);

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
            /*systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE, job.getJobName() + " start", LogTypeEnum.INFO.toInt(),
                    serverAddress + " - " + context.getJobDetail().getKey().getName(), "JOB", companyId, null);*/

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
            //------push notify------

            //get Fire time
            Date FireTime = context.getFireTime();
            // get send day
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");  
            String format = formatter.format(new Date());
            List<NotifyAdminEditDto> dto = notifyService.getSendDate(format);
            if(dto != null) {  
            	for(NotifyAdminEditDto ls:dto) {
            		if(ls.getSendDate() != null ) {
   	                 List<Db2AgentDto> LsData = notifyService.getLsNotifyDetail(ls.getId());
   	            //    NotifyEditDto dto = notifyService.getLsNotify(context.getJobDetail().getKey().getName());
   	                pushNotificationToFirebaseCloud(LsData, ls);
   	            }
            	}
	            
            }
            // 
            System.out.println("SampleJob run at: " + CommonDateUtil.formatDateToString(new Date(), CommonDateUtil.YYYYMMDDHHMMSSFFF));
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
           /* systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE, job.getJobName() + " end", LogTypeEnum.INFO.toInt(),
                    serverAddress + " - " + context.getJobDetail().getKey().getName(), "JOB", companyId, null);*/
        }

        // Log end
        logger.info(String.format("END (%s)! - %s", serverAddress, groupCode));
    }

    private void pushNotificationToFirebaseCloud(List<Db2AgentDto> LstData, NotifyAdminEditDto dto) throws Exception {

        notifyService.updateIsSend(dto.getId());

        final String MESSAGE = "Message";
        final String NOTIFICATION = "notifications";
        try {
            List<String> lstDocument = LstData.parallelStream().map(Db2AgentDto::getAgentCode).collect(Collectors.toList());

            String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));

            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            Firestore db = FirestoreClient.getFirestore();
            Map<String, Object> docData = new HashMap<>();
            List<JcaAccount> lstAccount = jcaAccountService.getListByUserName(null);
            List<String> lstToken = new ArrayList<>();
            for (String agentCode : lstDocument) {
                JcaAccount  account = lstAccount.stream().filter(x->x.getUsername().equals(agentCode)).findAny().orElse(null);
                if(account == null) continue;
                docData.put("notifyCode", dto.getNotifyCode());
                docData.put("notifyTitle", dto.getNotifyTitle());
                docData.put("contents", dto.getContents());
                docData.put("linkNotify", dto.getLinkNotify());
                docData.put("createdDate", dto.getCreateDate());
                docData.put("createBy", dto.getCreateBy());

                try {
                    ApiFuture<WriteResult> future = db.collection(NOTIFICATION)
                            .document(agentCode.toString())
                            .collection(MESSAGE)
                            .document(dto.getNotifyCode())
                            .set(docData, SetOptions.merge());
                    if(future.isDone()){
                        logger.error("Update time : " + future.get().getUpdateTime());
                    } else {
                        logger.error("Write Firestore not done!");
                    }
                } catch (Exception ex){
                    logger.error("Firestore Error", ex);
                }
                //check agent login before?
                if(lstAccount.size() > 0){
                    String token = account.getDeviceTokenMobile();
                    if (StringUtils.isNotBlank(token)) {
                        lstToken.add(token);
                    }
                }
            }
            try {
                if(!lstToken.isEmpty()){
                    MulticastMessage multicastMessage = allPlatformsMessage(lstToken, dto);
                    BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);
                    if (response.getFailureCount() > 0) {
                        List<SendResponse> responses = response.getResponses();
                        List<String> failedTokens = new ArrayList<>();
                        for (int i = 0; i < responses.size(); i++) {
                            if (!responses.get(i).isSuccessful()) {
                                // The order of responses corresponds to the order of the registration tokens.
                                failedTokens.add(lstToken.get(i));
                            }
                        }

                        logger.error("List of tokens that caused failures: " + failedTokens);
                    }
                } else {
                    logger.error("List token is empty!");
                }
            } catch (Exception ex) {
                logger.error("PUSH_NOTIFICATION", ex);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Push notification to firebase failed!");
        }
    }
    public MulticastMessage allPlatformsMessage(List<String> deviceToken,  NotifyAdminEditDto dto) {
        final String ACTION = "FLUTTER_NOTIFICATION_CLICK";
        final String NOTIFICATION = "notificationPage";
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(dto.getNotifyTitle())
                        .setBody(dto.getContents().replaceAll("<[^>]*>", ""))
                        .build())
                .putData("click_action", ACTION)
                .putData("route", NOTIFICATION)
                .addAllTokens(deviceToken)
                .build();
        return multicastMessage;
    }
}
