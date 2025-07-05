/*******************************************************************************
 * Class        :JcaMenuPathService
 * Created date :2020/12/10
 * Lasted date  :2020/12/10
 * Author       :SonND
 * Change log   :2020/12/10 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaMenuPathDto;
import vn.com.unit.core.entity.JcaMenuPath;

/**
 * JcaMenuPathService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaMenuPathService {

    /**
     * <p>
     * Save entity with create, update
     * </p>
     * .
     *
     * @param jcaMenuPath
     *            type {@link JcaMenuPath}
     * @return JcaMenuPath
     * @author SonND
     */
    public JcaMenuPath saveJcaMenuPath(JcaMenuPath jcaMenuPath);

    /**
     * <p>
     * Save jcaMenuPath with jcaMenuPathDto
     * </p>
     * .
     *
     * @param jcaMenuPathDto
     *            type {@link JcaMenuPathDto}
     * @return JcaMenuPath
     * @author SonND
     */
    public JcaMenuPath saveJcaMenuPathDto(JcaMenuPathDto jcaMenuPathDto);

    /**
     * <p>
     * Get jcaMenuPath by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaMenuPath
     * @author SonND
     */
    public JcaMenuPath getJcaMenuPathById(Long id);

    /**
     * <p>
     * Get jcaMenuPath by descendant id
     * </p>
     * .
     *
     * @param descendantId
     *            type {@link Long}
     * @return JcaMenuPath
     * @author SonND
     */
    public JcaMenuPath getJcaMenuPathByDescendantId(Long descendantId);

    
    /**
     * <p>
     * Delete menu path by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @author sonnd
     */
    public void deleteMenuPathByDescendantId(Long descendantId);
    
    /**
     * <p>
     * Get jca menu path dto list default.
     * </p>
     *
     * @author TrieuVD
     * @return {@link List<JcaMenuPathDto>}
     */
    public List<JcaMenuPathDto> getJcaMenuPathDtoListDefault();

}
