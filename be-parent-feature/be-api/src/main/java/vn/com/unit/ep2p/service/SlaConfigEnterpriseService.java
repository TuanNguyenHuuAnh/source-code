/*******************************************************************************
 * Class        ：SlaConfigEnterpriseService
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：TrieuVD
 * Change log   ：2020/12/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.SlaConfigAddReq;
import vn.com.unit.ep2p.dto.req.SlaConfigUpdateReq;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;

public interface SlaConfigEnterpriseService extends BaseRestService<ObjectDataRes<JpmSlaConfigDto>, JpmSlaConfigDto> {

    static final String TABLE_SLA_CONFIG = "sla";

    /**
     * <p>
     * Get jpm sla config dto by config id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link JpmSlaConfigDto}
     */
    public JpmSlaConfigDto getJpmSlaConfigDtoByConfigId(Long id);
    
    /**
     * <p>
     * Creates the jpm sla config dto.
     * </p>
     *
     * @author TrieuVD
     * @param slaConfigReq
     *            type {@link SlaConfigAddReq}
     * @return {@link JpmSlaConfigDto}
     * @throws DetailException
     *             the detail exception
     */
    public JpmSlaConfigDto createJpmSlaConfigDto(SlaConfigAddReq slaConfigReq) throws DetailException;

    /**
     * <p>
     * Update jpm sla config dto.
     * </p>
     *
     * @author TrieuVD
     * @param slaConfigReq
     *            type {@link SlaConfigUpdateReq}
     * @return {@link JpmSlaConfigDto}
     * @throws DetailException
     *             the detail exception
     */
    public JpmSlaConfigDto updateJpmSlaConfigDto(SlaConfigUpdateReq slaConfigReq) throws DetailException;

}
