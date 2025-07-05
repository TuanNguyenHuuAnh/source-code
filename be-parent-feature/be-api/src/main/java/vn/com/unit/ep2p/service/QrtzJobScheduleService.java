/*******************************************************************************
 * Class        ：QrtzJobScheduleService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.QrtzMJobScheduleReq;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;

/**
 * <p>
 * QrtzJobScheduleService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzJobScheduleService extends BaseRestService<ObjectDataRes<QrtzMJobScheduleDto>, QrtzMJobScheduleDto>{
    
    /** The Constant TABLE_QRTZ_JOB_SCHEDULE. */
    public static final String TABLE_QRTZ_JOB_SCHEDULE = "job_schedule";
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobScheduleReq
     *            type {@link QrtzMJobScheduleReq}
     * @return {@link QrtzMJobScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobScheduleDto create(QrtzMJobScheduleReq qrtzMJobScheduleReq)throws DetailException;
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobScheduleReq
     *            type {@link QrtzMJobScheduleReq}
     * @return {@link QrtzMJobScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobScheduleDto update(QrtzMJobScheduleReq qrtzMJobScheduleReq)throws DetailException;
    
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
}
