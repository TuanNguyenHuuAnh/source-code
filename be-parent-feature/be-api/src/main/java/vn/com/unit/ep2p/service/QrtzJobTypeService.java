/*******************************************************************************
 * Class        ：QrtzJobTypeService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.QrtzMJobTypeReq;
import vn.com.unit.quartz.job.dto.QrtzMJobTypeDto;

/**
 * <p>
 * QrtzJobTypeService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzJobTypeService extends BaseRestService<ObjectDataRes<QrtzMJobTypeDto>, QrtzMJobTypeDto>{
    
    /** The Constant TABLE_QRTZ_JOB_TYPE. */
    public static final String TABLE_QRTZ_JOB_TYPE = "job_type";
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobTypeReq
     *            type {@link QrtzMJobTypeReq}
     * @return {@link QrtzMJobTypeDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobTypeDto create(QrtzMJobTypeReq qrtzMJobTypeReq)throws DetailException;
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobTypeReq
     *            type {@link QrtzMJobTypeReq}
     * @return {@link QrtzMJobTypeDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobTypeDto update(QrtzMJobTypeReq qrtzMJobTypeReq)throws DetailException;

}
