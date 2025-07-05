/*******************************************************************************
 * Class        :JcaPositionPathRepository
 * Created date :2020/12/25
 * Lasted date  :2020/12/25
 * Author       :SonND
 * Change log   :2020/12/25:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaPositionPathDto;
import vn.com.unit.core.entity.JcaPositionPath;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaPositionPathRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaPositionPathRepository extends DbRepository<JcaPositionPath, Long> {

    /**
     * <p>
     * Gets the jca position path dto by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @return the jca position path dto by descendant id
     * @author sonnd
     */
    JcaPositionPathDto getJcaPositionPathDtoByDescendantId(@Param("descendantId") Long descendantId);

    /**
     * <p>
     * Gets the jca position path dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca position path dto by id
     * @author sonnd
     */
    JcaPositionPathDto getJcaPositionPathDtoById(@Param("id")Long id);

    /**
     * <p>
     * Delete position path by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deletePositionPathByDescendantId(@Param("descendantId")Long descendantId);
    
    JcaPositionPath findOne(@Param("ancestorId") Long ancestorId,@Param("descendantId") Long descendantId);

}