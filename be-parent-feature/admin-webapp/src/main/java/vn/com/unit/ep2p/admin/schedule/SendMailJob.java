package vn.com.unit.ep2p.admin.schedule;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.ep2p.admin.service.QrtzMJobLogWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobScheduleWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobStoreWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.core.dto.AgencyReturnDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

/**
 * SendMailJob
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SendMailJob implements Job {
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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;

    @Autowired
    EmailJobService emailJobService;

    @Autowired
    private JcaEmailService jcaEmailService;
    
    private static final Logger logger = LoggerFactory.getLogger(SendMailJob.class);

    private static final String TR = "<tr><td width=\"66\" valign=\"top\" style=\"width: 49.6pt; border-right-width: 1pt; border-bottom: 1pt solid windowtext; border-left: 1pt solid windowtext; border-right-color: windowtext; border-image: initial; border-top: none; height: 13.1pt;\">\r\n"
            + "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-GB\" style=\"font-family: Cambria, serif;\">${stt}</span></p>\r\n"
            + "         </td>\r\n"
            + "         <td width=\"132\" nowrap=\"\" valign=\"bottom\" style=\"width: 99.2pt; border-top: none; border-bottom: 1pt solid windowtext; border-right-width: 1pt; border-right-color: windowtext; height: 13.1pt;\">\r\n"
            + "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-GB\" style=\"font-family: Cambria, serif;\">${canđiateName}</span></p>\r\n"
            + "         </td>\r\n"
            + "         <td width=\"95\" nowrap=\"\" valign=\"bottom\" style=\"width: 70.9pt; border-top: none; border-bottom: 1pt solid windowtext; border-right-width: 1pt; border-right-color: windowtext; height: 13.1pt;\">\r\n"
            + "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-GB\" style=\"font-family: Cambria, serif;\">${idNo}</span></p>\r\n"
            + "         </td>\r\n"
            + "         <td width=\"246\" nowrap=\"\" valign=\"bottom\" style=\"width: 184.25pt; border-top: none; border-bottom: 1pt solid windowtext; border-right-width: 1pt; border-right-color: windowtext; height: 13.1pt;\">\r\n"
            + "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-GB\" style=\"font-family: Cambria, serif;\">{note}</span></p>\r\n"
            + "         </td>\r\n"
            + "         <td width=\"132\" nowrap=\"\" valign=\"bottom\" style=\"width: 99.25pt; border-top: none; border-bottom: 1pt solid windowtext; border-right-width: 1pt; border-right-color: windowtext; height: 13.1pt;\">\r\n"
            + "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-GB\" style=\"font-family: Cambria, serif;\">${recruiterAdName}</span></p>\r\n"
            + "         </td></tr>";

    private static final String STT = "${stt}";
    private static final String CANDIDATE_NAME = "${canđiateName}";
    private static final String ID_NO = "${idNo}";
    private static final String NOTE = "${note}";
    private static final String RECRUITER_AD_NAME = "${recruiterAdName}";

    private static final String TR_REPLACE = "<tr style=\"mso-yfti-irow:2;mso-yfti-lastrow:yes;\"></tr>";

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

            // send list email
            sendListEmail();

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
            logger.error("## SendMailJob ##", e);

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

        // Send notification
        try {
            emailJobService.sendEmailForAlertJob(SecurityContextHolder.getContext(), job, jobLog);
        } catch (Exception e) {
            /** TaiTT comment log system job */
            /*
             * systemLogsService.writeSystemLogs(CommonConstant.SC1_S02_JOBSCHEDULE,
             * job.getJobName() + " send email failed", LogTypeEnum.ERROR.toInt(),
             * e.getMessage(), "JOB", companyId, null);
             */
        }
        // Log end
        logger.info(String.format("END (%s)! - %s", serverAddress, groupCode));

    }

    private void sendListEmail() {
        try {
            Map<String, Map<String, AgencyReturnDto>> mapData = new HashMap<String, Map<String, AgencyReturnDto>>();

            List<AgencyReturnDto> lstDataReturn = new ArrayList<>();

            if (lstDataReturn != null && !lstDataReturn.isEmpty()) {
                for (AgencyReturnDto data : lstDataReturn) {
                    if (StringUtils.isNotBlank(data.getEmailAddress())) {
                        String email = data.getEmailAddress();
                        if (mapData.containsKey(email)) {
                            Map<String, AgencyReturnDto> map = mapData.get(data.getEmailAddress());
                            if (StringUtils.isNotBlank(data.getIdNo()) && !map.containsKey(data.getIdNo())) {
                                map.put(data.getIdNo(), data);
                            }

                            mapData.put(email, map);
                        } else {
                            Map<String, AgencyReturnDto> map = new HashMap<String, AgencyReturnDto>();
                            if (StringUtils.isNotBlank(data.getIdNo())) {
                                map.put(data.getIdNo(), data);
                            }

                            mapData.put(email, map);
                        }
                    }
                }
            }

            Set<String> emails = mapData.keySet();
            if (!emails.isEmpty()) {
                for (String email : emails) {

                    Map<String, AgencyReturnDto> data = mapData.get(email);

                    sendMail(email, "TEMPLATE_EMAIL_RETURN", data);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("####sendListEmail####", e);
        }

    }

    public void sendMail(String email, String emailTemplateCode, Map<String, AgencyReturnDto> mapData) {
        try {
            List<String> toAddress = new ArrayList<>();
            toAddress.add(email);

            List<String> ccAddress = new ArrayList<>();

            List<AgencyReturnDto> datas = new ArrayList<AgencyReturnDto>();
            datas.addAll(mapData.values());

            // get template email
            JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(emailTemplateCode);
            if (templateDto != null) {
                String subject = templateDto.getTemplateSubject();
                String content = replaceParam(subject, datas);

                // set email
                JcaEmailDto jcaEmailDto = convertValue(templateDto);
                jcaEmailDto.setToString(email);
                jcaEmailDto.setToAddress(toAddress);
                jcaEmailDto.setCcAddress(ccAddress);
                jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_NO_SAVE.getValue());
                jcaEmailDto.setEmailContent(content);
                jcaEmailDto.setSubject(subject);
                jcaEmailService.sendEmail(jcaEmailDto);
            }
        } catch (Exception e) {
            logger.error("####sendMail####", e);
        }
    }

    private String replaceParam(String content, List<AgencyReturnDto> datas) {
        String result = "";

        StringBuffer trString = new StringBuffer();

        if (StringUtils.isNotBlank(content) && content.contains(TR_REPLACE)) {
            if (datas != null && !datas.isEmpty()) {
                for (int i = 0; i < datas.size(); i++) {
                    AgencyReturnDto data = datas.get(i);

                    String candidateName = data.getCandidateName() != null ? data.getCandidateName() : "";
                    String idNo = data.getIdNo() != null ? data.getIdNo() : "";
                    String note = data.getNote() != null ? data.getNote() : "";
                    String recruiterAdName = data.getRecruiterAdName() != null ? data.getRecruiterAdName() : "";

                    String str = TR.replace(STT, String.valueOf(i + 1)).replace(CANDIDATE_NAME, candidateName)
                            .replace(ID_NO, idNo).replace(NOTE, note).replace(RECRUITER_AD_NAME, recruiterAdName);
                    trString.append(str);
                }
                content.replace(TR_REPLACE, trString.toString());

            }
        }
        result = content;

        return result;
    }

    private JcaEmailDto convertValue(JcaEmailTemplateDto templateDto) {
        JcaEmailDto emailDto = objectMapper.convertValue(templateDto, JcaEmailDto.class);
        emailDto.setEmailContent(templateDto.getTemplateContent());
        emailDto.setSubject(templateDto.getTemplateSubject());
        emailDto.setContentType("text/html; charset=utf-8");
        return emailDto;
    }

    public static void main(String[] args) {
        String text = TR.replace(STT, "1");
        System.out.println(text);
    }
}
