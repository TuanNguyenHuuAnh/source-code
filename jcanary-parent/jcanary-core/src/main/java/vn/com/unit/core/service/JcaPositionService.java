/*******************************************************************************
 * Class        JcaPositionService
 * Created date 2020/12/02
 * Lasted date  2020/12/02
 * Author       MinhNV
 * Change log   2020/12/02 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaPositionSearchDto;
import vn.com.unit.core.entity.JcaPosition;

/**
 * JcaPositionService.
 *
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface JcaPositionService {

    /** The Constant TABLE_ALIAS_JCA_POSITION. */
    public static final String TABLE_ALIAS_JCA_POSITION ="position";
    
    /**
     * <p>
     * total position by condition
     * </p>
     * .
     *
     * @param positionsearchdto
     *            type {@link JcaPositionSearchDto}
     * @return int
     * @author MinhNV
     */
    public int countJcaPositionDtoByCondition(JcaPositionSearchDto positionsearchdto);

    /**
     * <p>
     * get all position by condition
     * </p>
     * .
     *
     * @param positionsearchdto
     *            type {@link JcaPositionSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return List<JcaPositionDto>
     * @author MinhNV
     */
    public List<JcaPositionDto> getJcaPositionDtoByCondition(JcaPositionSearchDto positionsearchdto, Pageable pagable);

    /**
     * <p>
     * get position by id
     * </p>
     * .
     *
     * @param id
     *            id of position
     * @return JcaPosition
     * @author MinhNV
     */
    public JcaPosition getPositionById(Long id);

    /**
     * <p>
     * Save JcaPosition with create, update
     * </p>
     * .
     *
     * @param jcaPosition
     *            type {@link JcaPosition}
     * @return JcaPosition
     * @author MinhNV
     */
    public JcaPosition saveJcaPosition(JcaPosition jcaPosition);

    /**
     * <p>
     * Save JcaPosition with JcaPositionDto
     * </p>
     * .
     *
     * @param jcaPositionDto
     *            type {@link JcaPositionDto}
     * @return JcaPosition
     * @author MinhNV
     */
    public JcaPosition saveJcaPositionDto(JcaPositionDto jcaPositionDto);

    /**
     * <p>
     * Get position information detail by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaPositionDto
     * @author MinhNV
     */
    public JcaPositionDto getJcaPositionDtoById(Long id);
    
    /**
     * <p>
     * Delete jca position by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author sonnd
     */
    public void deleteJcaPositionById(Long id);
    
    /**
     * <p>
     * Gets the jca position dto to build tree.
     * </p>
     *
     * @return the jca position dto to build tree
     * @author sonnd
     */
    public List<JcaPositionDto> getJcaPositionDtoToBuildTree();
}
