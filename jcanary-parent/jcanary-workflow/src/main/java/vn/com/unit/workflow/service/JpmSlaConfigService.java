/*******************************************************************************
 * Class        ：JpmSlaConfigService
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
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmSlaConfigSearchDto;
import vn.com.unit.workflow.entity.JpmSlaConfig;

/**
 * <p>
 * JpmSlaConfigService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmSlaConfigService extends DbRepositoryService<JpmSlaConfig, Long>{

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
    public JpmSlaConfigDto getJpmSlaConfigDtoById(Long id, String lang);

    /**
     * <p>
     * Save jpm sla config.
     * </p>
     *
     * @author TrieuVD
     * @param jpmSlaConfig
     *            type {@link JpmSlaConfig}
     * @return {@link JpmSlaConfig}
     */
    public JpmSlaConfig saveJpmSlaConfig(JpmSlaConfig jpmSlaConfig);
    
    /**
     * <p>
     * Save jpm sla config dto.
     * </p>
     *
     * @author TrieuVD
     * @param jpmSlaConfigDto
     *            type {@link JpmSlaConfigDto}
     * @return {@link JpmSlaConfigDto}
     */
    public JpmSlaConfigDto saveJpmSlaConfigDto(JpmSlaConfigDto jpmSlaConfigDto);
    
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
    public List<JpmSlaConfigDto> getJpmSlaConfigDtoListBySearchDto(JpmSlaConfigSearchDto searchDto);
    
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
    public int countBySearchCondition(JpmSlaConfigSearchDto searchDto);
    
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
    public List<JpmSlaConfigDto> getJpmSlaConfigDtoListByCondition(JpmSlaConfigSearchDto searchDto, Pageable pageable);
    
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
    public JpmSlaConfigDto getJpmSlaConfigDtoByProcessDeployIdAndStepDeployId(Long processDepoyId, Long stepDeployId);

    /**
     * <p>
     * Clone jpm sla config.
     * </p>
     *
     * @author TrieuVD
     * @param oldSlaInfoId
     *            type {@link Long}
     * @param slaInfoId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     */
    public void cloneJpmSlaConfig(Long oldSlaInfoId, Long slaInfoId) throws DetailException;
}
