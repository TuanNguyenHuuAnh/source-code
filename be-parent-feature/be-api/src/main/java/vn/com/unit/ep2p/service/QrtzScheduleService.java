/*******************************************************************************
 * Class        ：QrtzScheduleService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.QrtzMScheduleReq;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;

/**
 * <p>
 * QrtzScheduleService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzScheduleService extends BaseRestService<ObjectDataRes<QrtzMScheduleDto>, QrtzMScheduleDto>{
    
    /** The Constant TABLE_QRTZ_SCHEDULE. */
    public static final String TABLE_QRTZ_SCHEDULE = "schedule";
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMScheduleReq
     *            type {@link QrtzMScheduleReq}
     * @return {@link QrtzMScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMScheduleDto create(QrtzMScheduleReq qrtzMScheduleReq)throws DetailException;
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMScheduleReq
     *            type {@link QrtzMScheduleReq}
     * @return {@link QrtzMScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMScheduleDto update(QrtzMScheduleReq qrtzMScheduleReq)throws DetailException;
}
