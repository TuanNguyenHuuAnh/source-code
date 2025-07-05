/*******************************************************************************
 * Class        ：JpmSlaInfoService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;
import vn.com.unit.workflow.dto.JpmSlaInfoSearchDto;
import vn.com.unit.workflow.entity.JpmSlaInfo;

/**
 * <p>
 * JpmSlaInfoService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmSlaInfoService extends DbRepositoryService<JpmSlaInfo, Long> {
    
    /**
     * <p>
     * Get jpm sla info dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link JpmSlaInfoDto}
     */
    public JpmSlaInfoDto getJpmSlaInfoDtoById(Long id);

    /**
     * <p>
     * Save jpm sla info.
     * </p>
     *
     * @author TrieuVD
     * @param jpmSlaInfo
     *            type {@link JpmSlaInfo}
     * @return {@link JpmSlaInfo}
     */
    public JpmSlaInfo saveJpmSlaInfo(JpmSlaInfo jpmSlaInfo);
    
    /**
     * <p>
     * Save jpm sla info dto.
     * </p>
     *
     * @author TrieuVD
     * @param jpmSlaInfoDto
     *            type {@link JpmSlaInfoDto}
     * @return {@link JpmSlaInfoDto}
     */
    public JpmSlaInfoDto saveJpmSlaInfoDto(JpmSlaInfoDto jpmSlaInfoDto);
    
    /**
     * <p>
     * Get jpm sla info dto list by search dto.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaInfoSearchDto}
     * @return {@link List<JpmSlaInfoDto>}
     */
    public List<JpmSlaInfoDto> getJpmSlaInfoDtoListBySearchDto(JpmSlaInfoSearchDto searchDto);
    
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
    public int countBySearchCondition(JpmSlaInfoSearchDto searchDto);
    
    /**
     * <p>
     * Get jpm sla info dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JpmSlaInfoSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JpmSlaInfoDto>}
     */
    public List<JpmSlaInfoDto> getJpmSlaInfoDtoListByCondition(JpmSlaInfoSearchDto searchDto, Pageable pageable);
    
    /**
     * <p>
     * Clone jpm sla.
     * </p>
     *
     * @author TrieuVD
     * @param oldProcessDeployId
     *            type {@link Long}
     * @param processDeployId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     */
    public void cloneJpmSla(Long oldProcessDeployId, Long processDeployId) throws DetailException;
    
}
