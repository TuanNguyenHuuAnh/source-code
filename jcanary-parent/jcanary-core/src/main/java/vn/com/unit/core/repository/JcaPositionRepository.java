/*******************************************************************************
 * Class        :PositionRepository
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :MinhNV
 * Change log   :2020/12/01:01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaPositionSearchDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.db.repository.DbRepository;

/**
 * PositionRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface JcaPositionRepository extends DbRepository<JcaPosition, Long> {

    /**
     * <p>
     * count all position by condition
     * </p>
     * .
     *
     * @param jcaPositionSearchDto
     *            type {@link JcaPositionSearchDto}
     * @return int
     * @author MinhNV
     */
    int countJcaPositionDtoByCondition(@Param("jcaPositionSearchDto") JcaPositionSearchDto jcaPositionSearchDto);

    /**
     * 
     * <p>
     * get list JcaPosition by condition
     * </p>
     * 
     * @param positionSearchDto
     * @param offset
     * @param sizeOfPage
     * @param isPaging
     * @return List of JcaPosition
     * @author MinhNV
     */
    List<JcaPositionDto> getJcaPositionDtoByCondition(@Param("jcaPositionSearchDto") JcaPositionSearchDto jcaPositionSearchDto, Pageable pageable);

    /**
     * 
     * <p>
     * get JcaPosition by id
     * </p>
     * 
     * @param id
     * @return JcaPosition
     * @author MinhNV
     */
    JcaPositionDto getJcaPositionDtoById(@Param("id") Long id);

    /**
     * <p>
     * get list JcaPosition
     * </p>
     * 
     * @return List<JcaPosition>
     * @author MinhNV
     */
    List<JcaPosition> getPositionDtoList();

    /**
     * <p>
     * get JcaPosition by code
     * </p>
     * 
     * @param code
     *            type String
     * @param id
     *            type Long
     * @return JcaPosition
     * @author MinhNV
     */
    JcaPosition getPositionByCode(@Param("code") String code, @Param("id") Long id);
    
    List<JcaPositionDto> getJcaPositionDtoToBuildTree();
}
