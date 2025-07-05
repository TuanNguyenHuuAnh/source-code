package vn.com.unit.ep2p.admin.schedule;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

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
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.EventsAdminService;
import vn.com.unit.ep2p.admin.service.NotifyEventsService;
import vn.com.unit.ep2p.admin.service.QrtzMJobLogWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobScheduleWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.dto.NotifyEventsDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class EventsSaveSystemJob implements Job {

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
    Db2ApiService db2ApiService;

    @Autowired
    private Db2ApiService db2Service;

    @Autowired
    EventsAdminService eventsAdminService;

    @Autowired
    NotifyEventsService notifyEventsService;

    @Autowired
    JcaAccountService jcaAccountService;
    
    private static final Logger logger = LoggerFactory.getLogger(EventsSaveSystemJob.class);
    private static final String LINK_DETAIL = "/events-management/detail-event/";
    private static final String NOTIFY_ADD_NEW = "<p>Anh/Chị được mời tham dự sự kiện <b>%s</b> vào lúc <b>%s</b> ngày <b>%s</b>. "
			+ "Hân hạnh được đón tiếp Anh/Chị.</p>    <p>Trân trọng kính mời!</p>";
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
                List<Db2AgentDto> entity = eventsAdminService.getEventSaveDetail(new Date());
                if(entity !=null) {
                	  if (entity.size() > 0) {
                		  List<Db2AgentDto> getAllAgent = null;
                          for (Db2AgentDto event : entity) {
                        	  if (!StringUtils.equals(event.getApplicableObject(), "ALL")) {
                        		  continue;
                        	  }
                        	  String position = db2Service.getPosition(event.getCreateBy());
                        	  if (StringUtils.equalsIgnoreCase(position, "Support")) {
                        		  getAllAgent = db2Service.getAllAgentOfSupport(event.getCreateBy());
                        	  } else if (StringUtils.equalsIgnoreCase(position, "Manager")) {
                        		  getAllAgent = db2Service.getAllAgentOfManager(event.getCreateBy());
                        	  } else if (StringUtils.equalsIgnoreCase(position, "BDAH")
                        			  || StringUtils.equalsIgnoreCase(position, "BDOH")
                        			  || StringUtils.equalsIgnoreCase(position, "BDRH")
                        			  || StringUtils.equalsIgnoreCase(position, "BDTH")) {
                        		  getAllAgent = db2Service.getAllAgentOfBD(event.getCreateBy());
                        	  } else if (StringUtils.equalsIgnoreCase(position, "CAO")) {
                        		  getAllAgent = db2Service.getAllAgentOfCAO(event.getCreateBy());
                        	  } else {
                        		  // LDAP user
                        		  getAllAgent = db2Service.getAllAgentOfLDAP(event.getCreateBy());
                        	  } 
                        	  logger.error("Get all agent : " + getAllAgent.size());
                              ConnectDb2(event, event.getApplicableObject(), getAllAgent, event.getId());
                              
                              // push notify
                              NotifyEventsDto dto = new NotifyEventsDto();
                              dto.setContents(String.format(NOTIFY_ADD_NEW, event.getEventTitle()
                      				, CommonDateUtil.formatDateToString(event.getEventDate(), "HH:mm")
                    				, CommonDateUtil.formatDateToString(event.getEventDate(), "dd/MM/yyyy")));
                              dto.setLinkNotify(LINK_DETAIL + event.getEventCode() + "/" + event.getId().toString());
                              dto.setEventId(event.getId());
                              notifyEventsService.addNewNotify(dto, new Date());
                              List<String> lstAgent = notifyEventsService.getLsAgentFromEventId(dto.getEventId());
                          	this.pushNotificationToFirebaseCloud(dto, lstAgent);
                          }
                      } else {
                          logger.error("CAN NOT EVENT");
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

        // update processing status
        eventsAdminService.updateProcessing(id);
        if (StringUtils.equalsIgnoreCase(applicableObject, "ALL")) {
            dataInsert = getAllAgent;
        }  else if (StringUtils.equalsIgnoreCase(applicableObject, "SEL")) {
            Db2AgentNotifyParamDto db2AgentNotifyParamDto = prepareDateCallStoreDb2(data);
            db2Service.callStoreDb2("RPT_ODS.DS_SP_GET_AGENT_NOTIFY_DETAIL", db2AgentNotifyParamDto);
            dataInsert = db2AgentNotifyParamDto.lstAgent;
        }
        if(dataInsert.size()>0) {
        	 //insert detail
            eventsAdminService.insertDetailToDatabaseWithBatch(dataInsert, id);
        }
	     // update check save detail
        eventsAdminService.updateCheckSave(id);
    }

    private Db2AgentNotifyParamDto prepareDateCallStoreDb2(Db2AgentDto events){

        Db2AgentNotifyParamDto db2AgentNotifyParamDto = new Db2AgentNotifyParamDto();
        db2AgentNotifyParamDto.territory = StringUtils.isBlank(events.getTerritorry()) ? null : (";" + events.getTerritorry() + ";");
        db2AgentNotifyParamDto.area =  StringUtils.isBlank(events.getArea()) ? null : (";" + events.getArea() + ";");
        db2AgentNotifyParamDto.office = StringUtils.isBlank(events.getOffice()) ? null : (";" + events.getOffice() + ";");
        db2AgentNotifyParamDto.region =  StringUtils.isBlank(events.getRegion()) ? null : (";" + events.getRegion() + ";");
        db2AgentNotifyParamDto.position = StringUtils.isBlank(events.getPosition()) ? null : (";" + events.getPosition() + ";");
        return db2AgentNotifyParamDto;
    }
    
    private void pushNotificationToFirebaseCloud(NotifyEventsDto dto, List<String> listAgent) throws Exception {
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
                if (!listAgent.isEmpty()) {
                	int total = listAgent.size();
 					int maxParm = 2000;
 					int fromIndex = 0;
 					int toIndex = total > (fromIndex + maxParm) ? (fromIndex + maxParm) : total;
 					while (fromIndex < total) {
 						List<String> lstAgent = listAgent.subList(fromIndex, toIndex);
 						String dataList = String.join(",", lstAgent);
 						List<JcaAccount> lstAccount = jcaAccountService
 								.getListByUserNameList(dataList);
 						
 						for (JcaAccount acc : lstAccount) {
 							if (StringUtils.isNotEmpty(acc.getDeviceTokenMobile())) {
 								// Add List Token
 								lstToken.add(acc.getDeviceTokenMobile());
 							} else {
 								// Add list agent not token
 								lstAgentNotToken.add(acc.getUsername());
 							}
 						}

 						fromIndex = toIndex;
 						toIndex = total > (fromIndex + maxParm) ? (fromIndex + maxParm) : total;
 					}
 					
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
 						
 						if (!failedTokens.isEmpty()) {
 					        systemLogsService.writeSystemLogs("NOTIFY_CODE_EVENT", "Send notif device", "List of tokens that caused failures: " + String.join(",", failedTokens), null);
 							logger.error("List of tokens that caused failures: " + String.join(",", failedTokens));
 						}
 					}
 					
 					if (!lstAgentNotToken.isEmpty()) {
 						logger.error("List agent has not token: " + String.join(",", lstAgentNotToken));
 					}
                     
                 } else {
                     logger.error("List token is empty!");
                 }
             } catch (Exception ex) {
 		        systemLogsService.writeSystemLogs("NOTIFY_CODE_EVENT", "Send notif device", ex.getMessage(), null);
                 logger.error("PUSH_NOTIFICATION", ex);
             }
         } catch (Exception e) {
             logger.error(e.getMessage(), e);
 	        systemLogsService.writeSystemLogs("NOTIFY_CODE_EVENT", "Send notif device", e.getMessage(), null);
             throw new Exception("Push notification to firebase failed!");
         }
     }

     public MulticastMessage allPlatformsMessage(List<String> deviceToken,  NotifyEventsDto dto) {
         final String ACTION = "FLUTTER_NOTIFICATION_CLICK";
         final String NOTIFICATION = "notificationPage";
         MulticastMessage multicastMessage = MulticastMessage.builder()
                 .setNotification(Notification.builder()
                         .setTitle("Thông báo sự kiện")
                         .setBody(dto.getContents().replaceAll("<[^>]*>", "").replace("    ", " "))
                         .build())
                 .putData("click_action", ACTION)
                 .putData("route", NOTIFICATION)
                 .addAllTokens(deviceToken)
                 .build();
         return multicastMessage;
     }
}
