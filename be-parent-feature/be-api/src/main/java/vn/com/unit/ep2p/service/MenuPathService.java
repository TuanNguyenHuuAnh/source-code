/*******************************************************************************
 * Class        ：MenuPathService
 * Created date ：2020/12/10
 * Lasted date  ：2020/12/10
 * Author       ：SonND
 * Change log   ：2020/12/10：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.dto.JcaMenuPathDto;
import vn.com.unit.core.entity.JcaMenuPath;

/**
 * MenuPathService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
/**
 * @author sonnd
 *
 */
public interface MenuPathService {
    
	
	
	/**
     * <p>
     * Creates the.
     * </p>
     *
     * @param jcaMenuPathDto
     *            type {@link JcaMenuPathDto}
     * @return {@link JcaMenuPath}
     * @author SonND
     */
	public JcaMenuPath create(JcaMenuPathDto jcaMenuPathDto);
   
	 /**
     * <p>
     * Save menu path
     * </p>
     * .
     *
     * @param JcaMenuPath
     *            type {@link jcaMenuPath}
     * @return JcaMenuPath
     * @author SonND
     */
	public JcaMenuPath save(JcaMenuPath jcaMenuPath);
	
	
	/**
     * <p>
     * Get menu path by id
     * </p>
     * .
     *
     * @param menuPathId
     *            type {@link Long}
     * @return JcaMenuPath
     * @author SonND
     */
	public JcaMenuPath getMenuPathById(Long menuPathId);
	
	/**
     * <p>
     * Get menu path by descendant id
     * </p>
     * .
     *
     * @param descendantId
     *            type {@link Long}
     * @return JcaMenuPath
     * @author SonND
     */
	public JcaMenuPath getMenuPathByDescendantId(Long descendantId);
	
	public void deleteMenuPathByDescendantId(Long descendantId);
}
