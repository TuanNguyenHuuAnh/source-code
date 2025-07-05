/*******************************************************************************
 * Class        ：QrtzJobService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.QrtzMJobReq;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;

/**
 * <p>
 * QrtzJobService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzJobService extends BaseRestService<ObjectDataRes<QrtzMJobDto>, QrtzMJobDto>{
    
    /** The Constant TABLE_QRTZ_JOB. */
    public static final String TABLE_QRTZ_JOB = "job";
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobReq
     *            type {@link QrtzMJobReq}
     * @return {@link QrtzMJobDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobDto create(QrtzMJobReq qrtzMJobReq)throws DetailException;
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobReq
     *            type {@link QrtzMJobReq}
     * @return {@link QrtzMJobDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobDto update(QrtzMJobReq qrtzMJobReq)throws DetailException;

}
