/*******************************************************************************
 * Class        ：SlaConfigDetailService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaConfigDetailDto;
import vn.com.unit.sla.dto.SlaConfigDetailSearchDto;
import vn.com.unit.sla.entity.SlaConfigDetail;

/**
 * SlaConfigDetailService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaConfigDetailService extends DbRepositoryService<SlaConfigDetail, Long> {

    /**
     * getSlaConfigDetailDtoById.
     *
     * @param id
     *            type Long
     * @return SlaConfigDetailDto
     * @author TrieuVD
     * @throws DetailException 
     */
    public SlaConfigDetailDto getSlaConfigDetailDtoById(Long id) throws DetailException;

    /**
     * <p>
     * Get sla config detail dto list by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link SlaConfigDetailSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<SlaConfigDetailDto>}
     * @author TrieuVD
     */
    public List<SlaConfigDetailDto> getSlaConfigDetailDtoListByCondition(SlaConfigDetailSearchDto searchDto, Pageable pageable);
    
    /**
     * <p>
     * Count by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link SlaConfigDetailSearchDto}
     * @return {@link int}
     * @author TrieuVD
     */
    public int countByCondition(SlaConfigDetailSearchDto searchDto);
    
    /**
     * delete.
     *
     * @param id
     *            type {@link Long}
     * @return true, if successful
     * @throws DetailException
     *             the detail exception
     * @author khadm
     */
    public boolean deleteById(Long id) throws DetailException;
    
   
    /**
     * <p>
     * Clone sla config details by sla config id.
     * </p>
     *
     * @param oldSlaConfigId
     *            type {@link Long}
     * @param newSlaConfigId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    public void cloneSlaConfigDetailsBySlaConfigId(Long oldSlaConfigId, Long newSlaConfigId) throws DetailException;

    /**
     * <p>
     * Gets the sla config detail dto by sla config id.
     * </p>
     *
     * @param slaConfigId
     *            type {@link Long}
     * @return the sla config detail dto by sla config id
     * @author sonnd
     */
    public List<SlaConfigDetailDto> getSlaConfigDetailDtoBySlaConfigId(Long slaConfigId);
    
    /**
     * <p>
     * Find all by config id.
     * </p>
     *
     * @param slaConfigId
     *            type {@link Long}
     * @return {@link List<SlaConfigDetailDto>}
     * @author TrieuVD
     */
    public List<SlaConfigDetailDto> findAllByConfigId(Long slaConfigId);
    
    /**
     * <p>
     * Save sla config detail.
     * </p>
     *
     * @param slaConfigDetail
     *            type {@link SlaConfigDetail}
     * @return {@link SlaConfigDetail}
     * @author TrieuVD
     * @throws DetailException 
     */
    public SlaConfigDetail saveSlaConfigDetail(SlaConfigDetail slaConfigDetail) throws DetailException;
    
    /**
     * <p>
     * Save sla config detail dto.
     * </p>
     *
     * @param slaConfigDetailDto
     *            type {@link SlaConfigDetailDto}
     * @return {@link SlaConfigDetailDto}
     * @author TrieuVD
     * @throws DetailException 
     */
    public SlaConfigDetailDto saveSlaConfigDetailDto(SlaConfigDetailDto slaConfigDetailDto) throws DetailException;
    
    /**
     * <p>
     * Delete by sla config id.
     * </p>
     *
     * @author TrieuVD
     * @param slaConfigId
     *            type {@link Long}
     */
    public void deleteBySlaConfigId(Long slaConfigId);
}
