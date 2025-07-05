/*******************************************************************************
 * Class        ：EfoComponentRepository
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentDto;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;

/**
 * EfoComponentRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface EfoComponentRepository extends DbRepository<EfoComponent, Long> {

    /**
     * <p>
     * Count efo component dto by condition.
     * </p>
     *
     * @param efoComponentSearchDto
     *            type {@link EfoComponentSearchDto}
     * @return {@link int}
     * @author taitt
     */
    int countEfoComponentDtoByCondition(@Param("efoComponentSearchDto") EfoComponentSearchDto efoComponentSearchDto);
    
    /**
     * <p>
     * Get efo component dto by condition.
     * </p>
     *
     * @param efoComponentSearchDto
     *            type {@link EfoComponentSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<EfoComponentDto>}
     * @author taitt
     */
    Page<EfoComponentDto> getEfoComponentDtoByCondition(@Param("efoComponentSearchDto") EfoComponentSearchDto efoComponentSearchDto,            
            Pageable pageable);
    
    /**
     * <p>
     * Get efo component dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link EfoComponentDto}
     * @author taitt
     */
    EfoComponentDto getEfoComponentDtoById(@Param("id") Long id);
}
