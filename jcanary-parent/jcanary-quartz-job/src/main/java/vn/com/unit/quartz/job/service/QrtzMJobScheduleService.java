/*******************************************************************************
 * Class        ：QrtzMJobScheduleService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;
import vn.com.unit.quartz.job.scheduler.ScheduleTask.JobStatus;

/**
 * <p>
 * QrtzMJobScheduleService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobScheduleService {

    /**
     * <p>
     * Creates the scheduler.
     * </p>
     *
     * @author khadm
     * @param qMSchedule
     *            type {@link QrtzMSchedule}
     * @param qMJob
     *            type {@link QrtzMJob}
     * @return {@link Object[]}
     * @throws DetailException
     *             the detail exception
     */
    public Object[] createScheduler(QrtzMSchedule qMSchedule, QrtzMJob qMJob) throws DetailException;

    /**
     * <p>
     * Get cron expression.
     * </p>
     *
     * @author khadm
     * @param date
     *            type {@link Date}
     * @return {@link String}
     */
    public String getCronExpression(Date date);

    /**
     * <p>
     * Get date formatter.
     * </p>
     *
     * @author khadm
     * @return {@link SimpleDateFormat}
     */
    public SimpleDateFormat getDateFormatter();

    /**
     * <p>
     * Get job schedule by name ref.
     * </p>
     *
     * @author khadm
     * @param jobNameRef
     *            type {@link String}
     * @return {@link QrtzMJobSchedule}
     */
    public QrtzMJobSchedule getJobScheduleByNameRef(String jobNameRef);

    /**
     * <p>
     * Have edit permission.
     * </p>
     *
     * @author khadm
     * @param user
     *            type {@link String}
     * @return true, if successful
     */
    public boolean haveEditPermission(String user);

    /**
     * <p>
     * Pause scheduler.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     */
    public void pauseScheduler(Long id) throws DetailException;

    /**
     * <p>
     * Refactor date.
     * </p>
     *
     * @author khadm
     * @param date
     *            type {@link String}
     * @return {@link String}
     * @throws ParseException
     *             the parse exception
     */
    public String refactorDate(String date) throws ParseException;

    /**
     * <p>
     * Resume scheduler.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     */
    public void resumeScheduler(Long id) throws DetailException;

    /**
     * <p>
     * Run scheduler.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     */
    public void runScheduler(Long id) throws DetailException;

    /**
     * <p>
     * Creates the job schedule.
     * </p>
     *
     * @author khadm
     * @param qScheduleJob
     *            type {@link QrtzMJobSchedule}
     * @return {@link QrtzMJobSchedule}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobSchedule createJobSchedule(QrtzMJobSchedule qScheduleJob) throws DetailException;


    /**
     * <p>
     * Delete scheduler.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     */
    public void deleteScheduler(Long id) throws DetailException;

    /**
     * <p>
     * Update schedule job.
     * </p>
     *
     * @author khadm
     * @param qMJobSchedule
     *            type {@link QrtzMJobSchedule}
     * @param jobStatus
     *            type {@link JobStatus}
     * @param validFlag
     *            type {@link ValidFlagEnum}
     * @throws DetailException
     *             the detail exception
     */
    public void updateScheduleJob(QrtzMJobSchedule qMJobSchedule, JobStatus jobStatus, ValidFlagEnum validFlag) throws DetailException;

    /**
     * <p>
     * Update scheduler.
     * </p>
     *
     * @author khadm
     * @param qMSchedule
     *            type {@link QrtzMSchedule}
     * @param jobName
     *            type {@link String}
     * @param qJobMaster
     *            type {@link QrtzMJob}
     * @return {@link Object[]}
     * @throws DetailException
     *             the detail exception
     */
    public Object[] updateScheduler(QrtzMSchedule qMSchedule, String jobName, QrtzMJob qJobMaster) throws DetailException;

    ////////////////////////////// NEW//////////////////////////////////////////////////////////

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobSchedule
     *            type {@link QrtzMJobScheduleDto}
     * @return {@link QrtzMJobScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobScheduleDto create(QrtzMJobScheduleDto qrtzMJobSchedule) throws DetailException;

    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobSchedule
     *            type {@link QrtzMJobScheduleDto}
     * @return {@link QrtzMJobScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobScheduleDto update(QrtzMJobScheduleDto qrtzMJobSchedule) throws DetailException;

    /**
     * <p>
     * List.
     * </p>
     *
     * @author khadm
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<QrtzMJobScheduleDto>}
     * @throws DetailException
     *             the detail exception
     */
    public List<QrtzMJobScheduleDto> list(QrtzMJobScheduleSearchDto searchDto, Pageable pageable) throws DetailException;

    /**
     * <p>
     * Count.
     * </p>
     *
     * @author khadm
     * @return {@link int}
     * @throws DetailException
     *             the detail exception
     */
    public int count(QrtzMJobScheduleSearchDto searchDto ) throws DetailException;
    
    public QrtzMJobScheduleDto detail(Long id) throws DetailException;

}
