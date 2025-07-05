/*******************************************************************************
 * Class        ：QrtzJobStoreService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.QrtzMJobStoreReq;
import vn.com.unit.quartz.job.dto.QrtzMJobStoreDto;

/**
 * <p>
 * QrtzJobStoreService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzJobStoreService extends BaseRestService<ObjectDataRes<QrtzMJobStoreDto>, QrtzMJobStoreDto> {

    /** The Constant TABLE_QRTZ_JOB_STORE. */
    public static final String TABLE_QRTZ_JOB_STORE = "job_store";
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobStoreReq
     *            type {@link QrtzMJobStoreReq}
     * @return {@link QrtzMJobStoreDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobStoreDto create(QrtzMJobStoreReq qrtzMJobStoreReq)throws DetailException;
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobStoreReq
     *            type {@link QrtzMJobStoreReq}
     * @return {@link QrtzMJobStoreDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobStoreDto update(QrtzMJobStoreReq qrtzMJobStoreReq)throws DetailException;
}
