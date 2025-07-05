/*******************************************************************************
 * Class        ：QrtzJobLogService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.QrtzMJobLogReq;
import vn.com.unit.quartz.job.dto.QrtzMJobLogDto;

/**
 * <p>
 * QrtzJobLogService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzJobLogService extends BaseRestService<ObjectDataRes<QrtzMJobLogDto>, QrtzMJobLogDto>{
    
    /** The Constant TABLE_QRTZ_JOB_LOG. */
    public static final String TABLE_QRTZ_JOB_LOG = "job_log";
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobLogReq
     *            type {@link QrtzMJobLogReq}
     * @return {@link QrtzMJobLogDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobLogDto create(QrtzMJobLogReq qrtzMJobLogReq) throws DetailException;
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobLogReq
     *            type {@link QrtzMJobLogReq}
     * @return {@link QrtzMJobLogDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobLogDto update(QrtzMJobLogReq qrtzMJobLogReq) throws DetailException;
}
