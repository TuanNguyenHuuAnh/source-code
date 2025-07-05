package vn.com.unit.ep2p.admin.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.admin.schedule.ScheduleTask.JobStatus;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

public interface QrtzMJobScheduleWebappService {

	public QrtzMJobSchedule createScheduleJob(QrtzMJobSchedule qMJobSchedule, JobStatus jobStatus, ValidFlagEnum validFlag)
			throws Exception;

	public Object[] createScheduler(QrtzMSchedule qMSchedule, QrtzMJob qMJob) throws Exception;

	public QrtzMJobSchedule getById(Long id);

	public String getCronExpression(Date date);

	public SimpleDateFormat getDateFormatter();

	public QrtzMJobSchedule getJobScheduleByNameRef(String jobNameRef);

	public PageWrapper<QrtzMJobScheduleDto> getJobSchedules(QrtzMJobScheduleSearchDto qScheduleJob, int pageSize, int page);

	public boolean haveEditPermission(String user);

	public void initCreateScreen(ModelAndView mav, Long id, Long companyId, Locale locale) throws Exception;
	
	public void initCreateScreenForError(ModelAndView mav, QrtzMJobSchedule scheduleJob, Long companyId, Locale locale);

	public void initScheduleCreateScreen(ModelAndView mav, Long id) throws Exception;

	public void pauseScheduler(Long id) throws Exception;

	public String refactorDate(String date) throws ParseException;

	public void resumeScheduler(Long id) throws Exception;

	public void runScheduler(Long id) throws Exception;

	public QrtzMJobSchedule createJobSchedule(QrtzMJobSchedule qScheduleJob) throws Exception;
	
	public void save(QrtzMJobSchedule qScheduleJob);

	public void deleteScheduler(Long id) throws Exception;

	public void updateScheduleJob(QrtzMJobSchedule qMJobSchedule, JobStatus jobStatus, ValidFlagEnum validFlag)
			throws Exception;

	public Object[] updateScheduler(QrtzMSchedule qMSchedule, String jobName, QrtzMJob qJobMaster) throws Exception;

    /**
     * createCutoffScheduler
     * @param id
     * @return
     * @throws Exception
     * @author HungHT
     */
    Object[] createCutoffScheduler(Long id) throws Exception;

    /**
     * 
     * createJobScheduleArchive
     * @param qScheduleJob
     * @param archiveId
     * @author tantm
     * @throws Exception 
     */
    public QrtzMJobSchedule createJobScheduleArchive(QrtzMJobSchedule qScheduleJob, Long archiveId) throws Exception;

    /**
     * 
     * createScheduler
     * @param schedule
     * @param job
     * @param archiveId
     * @return
     * @throws Exception
     * @author tantm
     */
    Object[] createScheduler(QrtzMSchedule schedule, QrtzMJob job, Long archiveId) throws Exception;

    /**
     * 
     * updateScheduler
     * @param schedule
     * @param jobName
     * @param qJobMaster
     * @param archiveId
     * @return
     * @throws Exception
     * @author tantm
     */
    Object[] updateScheduler(QrtzMSchedule schedule, String jobName, QrtzMJob qJobMaster, Long archiveId) throws Exception;

}
