/*******************************************************************************
 * Class        ：SlaConfigService
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
import vn.com.unit.sla.dto.SlaConfigDto;
import vn.com.unit.sla.entity.SlaConfig;

/**
 * SlaConfigService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaConfigService extends DbRepositoryService<SlaConfig, Long> {
    
    /**
     * <p>
     * Get sla config dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link SlaConfigDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfigDto getSlaConfigDtoById(Long id) throws DetailException;
    
    /**
     * <p>
     * Save sla config.
     * </p>
     *
     * @param slaConfig
     *            type {@link SlaConfig}
     * @return {@link SlaConfig}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfig saveSlaConfig(SlaConfig slaConfig) throws DetailException;
    
    /**
     * <p>
     * Save sla config dto.
     * </p>
     *
     * @param slaConfigDto
     *            type {@link SlaConfigDto}
     * @return {@link SlaConfigDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaConfigDto saveSlaConfigDto(SlaConfigDto slaConfigDto) throws DetailException;
    
    /**
     * createSlaConfig.
     *
     * @param slaConfigDto
     *            type {@link SlaConfigDto}
     * @return {@link SlaConfigDto}
     * @throws DetailException
     *             the detail exception
     * @author khadm
     */
    public SlaConfigDto createSlaConfig(SlaConfigDto slaConfigDto) throws DetailException;
    
    /**
     * updateSlaConfig.
     *
     * @param slaConfigDto
     *            type {@link SlaConfigDto}
     * @return {@link SlaConfigDto}
     * @throws DetailException
     *             the detail exception
     * @author khadm
     */
    public SlaConfigDto updateSlaConfig(SlaConfigDto slaConfigDto) throws DetailException;

    /**
     * deleteById.
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
     * getListConfig.
     *
     * @param pageable
     *            type {@link Pageable}
     * @return the list config
     * @author khadm
     */
    public List<SlaConfigDto> getListConfig(Pageable pageable);
    
    /**
     * countListConfig.
     *
     * @return {@link int}
     * @author khadm
     */
    public int countListConfig();
    
    /**
     * <p>
     * Clone sla config dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link SlaConfigDto}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    public SlaConfigDto cloneSlaConfigDtoById(Long id) throws DetailException;
}
