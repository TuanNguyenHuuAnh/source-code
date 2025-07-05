/*******************************************************************************
 * Class        ：JpmSlaInfoRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmSlaConfigSearchDto;
import vn.com.unit.workflow.entity.JpmSlaConfig;

/**
 * <p>
 * JpmSlaConfigRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmSlaConfigRepository extends DbRepository<JpmSlaConfig, Long> {
    
    /**
     * <p>
     * Get jpm sla config dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link JpmSlaConfigDto}
     */
    public JpmSlaConfigDto getJpmSlaConfigDtoById(@Param("id") Long id, @Param("lang") String lang);
    
    /**
     * <p>
     * Get jpm sla config dto list by search dto.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaConfigSearchDto}
     * @return {@link List<JpmSlaConfigDto>}
     */
    public List<JpmSlaConfigDto> getJpmSlaConfigDtoListBySearchDto(@Param("searchDto") JpmSlaConfigSearchDto searchDto);
    
    /**
     * <p>
     * Count by search condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaConfigSearchDto}
     * @return {@link int}
     */
    public int countBySearchCondition(@Param("searchDto") JpmSlaConfigSearchDto searchDto);
    
    /**
     * <p>
     * Get jpm sla config dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaConfigSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JpmSlaConfigDto>}
     */
    public Page<JpmSlaConfigDto> getJpmSlaConfigDtoListByCondition(@Param("searchDto") JpmSlaConfigSearchDto searchDto, Pageable pageable);
    
    
    /**
     * <p>
     * Get jpm sla config dto by process deploy id and step deploy id.
     * </p>
     *
     * @author TrieuVD
     * @param processDepoyId
     *            type {@link Long}
     * @param stepDeployId
     *            type {@link Long}
     * @return {@link JpmSlaConfigDto}
     */
    public JpmSlaConfigDto getJpmSlaConfigDtoByProcessDeployIdAndStepDeployId(@Param("processDeployId") Long processDepoyId, @Param("stepDeployId") Long stepDeployId);
    
    /**
     * <p>
     * Get jpm sla config dto list by jpm sla info id.
     * </p>
     *
     * @author TrieuVD
     * @param jpmSlaInfoId
     *            type {@link Long}
     * @return {@link List<JpmSlaConfigDto>}
     */
    public List<JpmSlaConfigDto> getJpmSlaConfigDtoListByJpmSlaInfoId(@Param("jpmSlaInfoId") Long jpmSlaInfoId);
}
