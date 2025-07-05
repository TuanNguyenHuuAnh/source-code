package vn.com.unit.ep2p.admin.schedule;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.NotifyBaseDto;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.admin.repository.NotifyAdminRepository;
import vn.com.unit.ep2p.admin.repository.NotifyDetailAdminRepository;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.NotifyAdminService;
import vn.com.unit.ep2p.admin.service.NotifyBaseService;
import vn.com.unit.ep2p.admin.service.QrtzMJobLogWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobScheduleWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.dto.NotifyAdminEditDto;
import vn.com.unit.ep2p.dto.NotifyDetailAdminEditDto;
import vn.com.unit.ep2p.utils.SystemOutLogUtils;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class NotifySystemJob implements Job {

    @Autowired
    private Environment env;

    @Autowired
    QrtzMJobLogWebappService jobLogService;

    @Autowired
    QrtzMJobScheduleWebappService jobScheduleService;

    @Autowired
    SystemLogsService systemLogsService;
    
    @Autowired
    NotifyAdminRepository notifyRepository;

    @Autowired
    QrtzMJobWebappService jobService;

    @Autowired
    NotifyAdminService notifyAdminService;

    @Autowired
    Db2ApiService db2ApiService;
    
    @Autowired
    NotifyDetailAdminRepository notifyDetailRepository;
    
    @Autowired
    JcaAccountService jcaAccountService;
    
    @Autowired
    private NotifyBaseService notifyService;
    
    @Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

    private static final Logger logger = LoggerFactory.getLogger(NotifySystemJob.class);
    
    private final String NOTIFY_CODE_BIRTHDAY = "NOT9999.0004";

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
            
            try {
                List<Db2AgentDto> dataAgent = db2ApiService.getDobAgent();
                if(dataAgent != null) {
		            if(dataAgent.size() > 0) {
		                NotifyAdminEditDto data = notifyAdminService.findNotifyId(NOTIFY_CODE_BIRTHDAY);
		                List<NotifyBaseDto> listNoti = new ArrayList<NotifyBaseDto>();
		                NotifyBaseDto notiDto = null;
		                String contents = data.getContents();
		                for (Db2AgentDto agent: dataAgent) { 

		                	notiDto = new NotifyBaseDto();
		                	
		                	if (agent.getGender().equals("M")) {
		                		notiDto.setContents(String.format(contents, "Anh", agent.getAgentName()));             		
		                	} else {
		                		notiDto.setContents(String.format(contents, "Chị", agent.getAgentName()));             		
		                	}
		                	notiDto.setTitle("Chúc mừng sinh nhật");
		                	notiDto.setAgentCode(agent.getAgentCode());
		                	
		                	SystemOutLogUtils.printLogObjectDto("## NotifySystemJob HPBD ## DTO=", notiDto);
		                	
		                	listNoti.add(notiDto);
		                }
		                if(CollectionUtils.isNotEmpty(listNoti)) {
		                	notifyService.pushListNotifyForWeb(listNoti, true);
		                	pushNotificationToFirebaseCloud(listNoti);
		                }
		            }
                }
            
            }catch (Exception e) {
            	logger.error("## NotifySystemJob  ##", e);
            }
            //------END------

            System.out.println("NotifySystemJob  run at: " + CommonDateUtil.formatDateToString(new Date(), CommonDateUtil.YYYYMMDDHHMMSSFFF));
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

    private void pushNotificationToFirebaseCloud(List<NotifyBaseDto> notifications) {
    	try {
    		String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));

            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            try {
            	List<String> lstToken = new ArrayList<>();
                List<String> lstAgentNotToken = new ArrayList<>();
                List<String> agentCodes = notifications.stream().map(NotifyBaseDto::getAgentCode).collect(Collectors.toList());
                List<JcaAccount> lstAccount = jcaAccountService.getListByUserNameList(String.join(",", agentCodes));
                for (JcaAccount acc : lstAccount) {
					if (StringUtils.isNotEmpty(acc.getDeviceTokenMobile())) {
						// Add List Token
						lstToken.add(acc.getDeviceTokenMobile());
						if (lstToken.size() % 500 == 0) {
							sendNotify(lstToken, notifications.get(0).getTitle(), notifications.get(0).getContents());
							lstToken = new ArrayList<>();
						}
					} else {
						// Add list agent not token
						lstAgentNotToken.add(acc.getUsername());
					}
				}
                if (lstToken.size() > 0) {
                	sendNotify(lstToken, notifications.get(0).getTitle(), notifications.get(0).getContents());
                }
				if (!lstAgentNotToken.isEmpty()) {
					logger.error("List agent has not token: " + String.join(",", lstAgentNotToken));
				}
            } catch (Exception ex) {
            	systemLogsService.writeSystemLogs("## NotifySystemJob ##", "Send notif device", ex.getMessage(), null);
            	logger.error("PUSH_NOTIFICATION", ex);
            }
    	} catch (Exception e) {
            logger.error(e.getMessage(), e);
            systemLogsService.writeSystemLogs("## NotifySystemJob ##", "Send notif device", e.getMessage(), null);
        }
    }

    private void sendNotify(List<String> lstToken, String title, String content) throws FirebaseMessagingException {
    	MulticastMessage multicastMessage = allPlatformsMessage(lstToken, title, content);
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
			
			if (!failedTokens.isEmpty()) {
		        systemLogsService.writeSystemLogs("## NotifySystemJob ##", "Send notif device", "List of tokens that caused failures: " + String.join(",", failedTokens), null);
				logger.error("List of tokens that caused failures: " + String.join(",", failedTokens));
			}
		}
    }
    public MulticastMessage allPlatformsMessage(List<String> deviceToken, String title, String contents) {
        final String ACTION = "FLUTTER_NOTIFICATION_CLICK";
        final String NOTIFICATION = "notificationPage";
        MulticastMessage multicastMessage = MulticastMessage.builder()
        		  .setNotification(Notification.builder()
                          .setTitle(title)
                          .setBody(contents.replaceAll("<[^>]*>", ""))
                          .build())
                 .putData("click_action", ACTION)
                 .putData("route", NOTIFICATION)
                 .addAllTokens(deviceToken)
                 .build();
        return multicastMessage;
    }    

}
