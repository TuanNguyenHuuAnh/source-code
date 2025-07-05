/*******************************************************************************
 * Class        ：SlaConfigDetailService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.List;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaConfigAlertToDto;
import vn.com.unit.sla.entity.SlaConfigAlertTo;

/**
 * SlaConfigDetailService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaConfigAlertToService extends DbRepositoryService<SlaConfigAlertTo, Long> {

    /**
     * <p>
     * Get sla config alert to dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link SlaConfigAlertToDto}
     * @author TrieuVD
     */
    public SlaConfigAlertToDto getSlaConfigAlertToDtoById(Long id);

    /**
     * <p>
     * Get list by config detail id.
     * </p>
     *
     * @param slaConfigDetailId
     *            type {@link Long}
     * @return {@link List<SlaConfigAlertToDto>}
     * @author TrieuVD
     */
    public List<SlaConfigAlertToDto> getListByConfigDetailId(Long slaConfigDetailId);

    /**
     * <p>
     * Delete by config detail id.
     * </p>
     *
     * @param slaConfigDetailId
     *            type {@link Long}
     * @author TrieuVD
     */
    public void deleteByConfigDetailId(Long slaConfigDetailId);
    
    /**
     * <p>
     * Save sla config alert to.
     * </p>
     *
     * @param slaConfigAlertTo
     *            type {@link SlaConfigAlertTo}
     * @return {@link SlaConfigAlertTo}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfigAlertTo saveSlaConfigAlertTo(SlaConfigAlertTo slaConfigAlertTo) throws DetailException;
    
    /**
     * <p>
     * Save sla config alert to dto.
     * </p>
     *
     * @param slaConfigAlertToDto
     *            type {@link SlaConfigAlertToDto}
     * @return {@link SlaConfigAlertTo}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfigAlertTo saveSlaConfigAlertToDto(SlaConfigAlertToDto slaConfigAlertToDto) throws DetailException;

    /**
     * <p>
     * Clone sla config alert tos by sla config detail id.
     * </p>
     *
     * @param oldSlaConfigDetailId
     *            type {@link Long}
     * @param newSlaConfigDetailId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    public void cloneSlaConfigAlertTosBySlaConfigDetailId(Long oldSlaConfigDetailId, Long newSlaConfigDetailId) throws DetailException;
}
