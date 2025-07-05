/*******************************************************************************
 * Class        ：JpmSlaInfoRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;
import vn.com.unit.workflow.dto.JpmSlaInfoSearchDto;
import vn.com.unit.workflow.entity.JpmSlaInfo;

/**
 * <p>
 * JpmSlaInfoRepository
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmSlaInfoRepository extends DbRepository<JpmSlaInfo, Long>{
    
    /**
     * <p>
     * Get jpm sla Info dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link JpmSlaInfoDto}
     */
    public JpmSlaInfoDto getJpmSlaInfoDtoById(@Param("id") Long id);
    
    /**
     * <p>
     * Get jpm sla Info dto list by search dto.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaInfoSearchDto}
     * @return {@link List<JpmSlaInfoDto>}
     */
    public List<JpmSlaInfoDto> getJpmSlaInfoDtoListBySearchDto(@Param("searchDto") JpmSlaInfoSearchDto searchDto);
    
    /**
     * <p>
     * Count by search condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaInfoSearchDto}
     * @return {@link int}
     */
    public int countBySearchCondition(@Param("searchDto") JpmSlaInfoSearchDto searchDto);
    
    /**
     * <p>
     * Get jpm sla Info dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaInfoSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JpmSlaInfoDto>}
     */
    public List<JpmSlaInfoDto> getJpmSlaInfoDtoListByCondition(@Param("searchDto") JpmSlaInfoSearchDto searchDto, Pageable pageable);
    
    /**
     * <p>
     * Get jpm sla info dto by process deploy id.
     * </p>
     *
     * @author TrieuVD
     * @param processDeployId
     *            type {@link Long}
     * @return {@link JpmSlaInfoDto}
     */
    public JpmSlaInfoDto getJpmSlaInfoDtoByProcessDeployId(@Param("processDeployId") Long processDeployId);
}
