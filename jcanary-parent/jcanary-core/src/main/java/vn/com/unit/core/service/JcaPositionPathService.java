/*******************************************************************************
 * Class        :JcaPositionPathService
 * Created date :2020/12/25
 * Lasted date  :2020/12/25
 * Author       :SonND
 * Change log   :2020/12/25 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaPositionPathDto;
import vn.com.unit.core.entity.JcaPositionPath;

/**
 * JcaPositionPathService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaPositionPathService {

    /**
     * <p>
     * Save jca position path.
     * </p>
     *
     * @param jcaPositionPath
     *            type {@link JcaPositionPath}
     * @return {@link JcaPositionPath}
     * @author sonnd
     */
    public JcaPositionPath saveJcaPositionPath(JcaPositionPath jcaPositionPath);

    /**
     * <p>
     * Save jca position path dto.
     * </p>
     *
     * @param jcaPositionPathDto
     *            type {@link JcaPositionPathDto}
     * @return {@link JcaPositionPath}
     * @author sonnd
     */
    public JcaPositionPath saveJcaPositionPathDto(JcaPositionPathDto jcaPositionPathDto);
   
    /**
     * <p>
     * Gets the jca position path by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca position path by id
     * @author sonnd
     */
    public JcaPositionPath getJcaPositionPathById(Long ancestorId, Long descendantId);

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
    public JcaPositionPathDto getJcaPositionPathDtoByDescendantId(Long descendantId);
    
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
    public JcaPositionPathDto  getJcaPositionPathDtoById(Long id);

    
    /**
     * <p>
     * Delete position path by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @author sonnd
     */
    public void deletePositionPathByDescendantId(Long descendantId);
    
    public void savePositionPath(JcaPositionDto jcaPositionDto);
}
