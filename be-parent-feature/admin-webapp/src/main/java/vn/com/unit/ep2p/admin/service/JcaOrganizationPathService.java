/*******************************************************************************
 * Class        :JcaOrganizationPathService
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import vn.com.unit.core.dto.JcaOrganizationPathDto;
import vn.com.unit.core.entity.JcaOrganizationPath;

/**
 * JcaOrganizationPathService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaOrganizationPathService {

    /**
     * <p>
     * Save entity with create, update
     * </p>
     * .
     *
     * @param jcaOrganizationPath
     *            type {@link JcaOrganizationPath}
     * @return JcaOrganizationPath
     * @author SonND
     */
    public JcaOrganizationPath saveJcaOrganizationPath(JcaOrganizationPath jcaOrganizationPath);

    /**
     * <p>
     * Save jcaMenuPath with jcaMenuPathDto
     * </p>
     * .
     *
     * @param jcaOrganizationPathDto
     *            type {@link JcaOrganizationPathDto}
     * @return JcaMenuPath
     * @author SonND
     */
    public JcaOrganizationPath saveJcaOrganizationPathDto(JcaOrganizationPathDto jcaOrganizationPathDto);

    /**
     * <p>
     * Get JcaOrganizationPath by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaOrganizationPath
     * @author SonND
     */
    public JcaOrganizationPath getJcaOrganizationPathById(Long id);

    /**
     * <p>
     * Get JcaOrganizationPathDto by descendant id
     * </p>
     * .
     *
     * @param descendantId
     *            type {@link Long}
     * @return JcaOrganizationPathDto
     * @author SonND
     */
    public JcaOrganizationPathDto getJcaOrganizationPathDtoByDescendantId(Long descendantId);

    /**
     * <p>
     * Gets the jca organization path dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca organization path dto by id
     * @author sonnd
     */
    public JcaOrganizationPathDto  getJcaOrganizationPathDtoById(Long id);
    
    
    /**
     * <p>
     * Delete organization path by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @author sonnd
     */
    public void deleteOrganizationPathByDescendantId(Long descendantId);
}
