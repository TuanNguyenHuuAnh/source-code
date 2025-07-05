/*******************************************************************************
 * Class        ：SlaConfigDetailEnterpriseService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：TrieuVD
 * Change log   ：2021/01/25：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.SlaConfigDetailAddReq;
import vn.com.unit.ep2p.dto.req.SlaConfigDetailUpdateReq;
import vn.com.unit.sla.dto.SlaConfigDetailDto;

/**
 * <p>
 * SlaConfigDetailEnterpriseService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaConfigDetailEnterpriseService extends BaseRestService<ObjectDataRes<SlaConfigDetailDto>, SlaConfigDetailDto> {

    /** The Constant TABLE_SLA_CONFIG_DETAIL. */
    public static final String TABLE_ALIAS_SLA_CONFIG_DETAIL = "detail";
    public static final String SEARCH_SLA_CONFIG_ID = "slaConfigId";
    public static final String SEARCH_ALERT_TYPE = "alertType";

    /**
     * <p>
     * Get sla config detail dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link SlaConfigDetailDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfigDetailDto getSlaConfigDetailDtoById(Long id) throws DetailException;

    /**
     * <p>
     * Creates the sla config detail.
     * </p>
     *
     * @param slaConfigDetailReq
     *            type {@link SlaConfigDetailAddReq}
     * @return {@link SlaConfigDetailDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfigDetailDto createSlaConfigDetail(SlaConfigDetailAddReq slaConfigDetailReq) throws DetailException;

    /**
     * <p>
     * Update sla config detail.
     * </p>
     *
     * @param slaConfigDetailReq
     *            type {@link SlaConfigDetailUpdateReq}
     * @return {@link SlaConfigDetailDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfigDetailDto updateSlaConfigDetail(SlaConfigDetailUpdateReq slaConfigDetailReq) throws DetailException;

}
