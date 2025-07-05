/*******************************************************************************
 * Class        ：QrtzJobClassService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.QrtzJobClassReq;
import vn.com.unit.quartz.job.dto.QrtzMJobClassDto;

/**
 * <p>
 * QrtzJobClassService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzJobClassService extends BaseRestService<ObjectDataRes<QrtzMJobClassDto>, QrtzMJobClassDto> {
    
    /** The Constant TABLE_QRTZ_JOB_CLASS. */
    public static final String TABLE_QRTZ_JOB_CLASS = "job_class";
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzJobClassReq
     *            type {@link QrtzJobClassReq}
     * @return {@link QrtzMJobClassDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobClassDto create(QrtzJobClassReq qrtzJobClassReq) throws DetailException;
    
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzJobClassReq
     *            type {@link QrtzJobClassReq}
     * @return {@link QrtzMJobClassDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobClassDto update(QrtzJobClassReq qrtzJobClassReq) throws DetailException;
    
    
}
