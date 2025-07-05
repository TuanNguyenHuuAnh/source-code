/*******************************************************************************
 * Class        ：MenuService
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：SonND
 * Change log   ：2020/12/09：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.MenuAddReq;
import vn.com.unit.ep2p.dto.req.MenuUpdateReq;
import vn.com.unit.ep2p.dto.res.MenuInfoRes;

/**
 * MenuService
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
/**
 * @author sonnd
 *
 */
public interface MenuService {

    /**
     * <p>
     * List menu
     * </p>
     * 
     * @return List<TreeObject<JcaMenuDto>>
     * @throws Exception
     * @author SonND
     */
    List<TreeObject<JcaMenuDto>> getListMenu(Long companyId) throws DetailException;

    /**
     * <p>
     * Create menu
     * </p>
     * 
     * @param menuAddReq
     * @return MenuInfoRes
     * @throws Exception
     * @author SonND
     */
    MenuInfoRes create(MenuAddReq menuAddReq) throws DetailException;

    /**
     * <p>
     * Save menu
     * </p>
     * 
     * @param JcaMenuDto
     * @return JcaMenu
     * @throws Exception
     * @author SonND
     */
    public JcaMenuDto save(JcaMenuDto jcaMenuDto);

    /**
     * <p>
     * Get menu information by id
     * </p>
     * 
     * @param menuId
     * @return MenuInfoRes
     * @throws Exception
     * @author SonND
     */
    public MenuInfoRes getMenuInfoResById(Long menuId) throws DetailException;

    /**
     * <p>
     * Get detail information by id
     * </p>
     * 
     * @param menuId
     * @return JcaMenuDto
     * @throws Exception
     * @author SonND
     */
    public JcaMenuDto getDetailDto(Long menuId) throws DetailException;

    /**
     * 
     * <p>
     * Update information menu by id
     * <p>
     * 
     * @param menuUpdateReq
     * @return
     * @throws Exception
     * @author SonND
     */
    void update(MenuUpdateReq menuUpdateReq) throws DetailException;

    /**
     * 
     * <p>
     * Delete menu by id
     * <p>
     * 
     * @param menuId
     * @return
     * @throws Exception
     * @author SonND
     */
    void delete(Long menuId) throws DetailException;

	/**
	 * @author vunt
	 * @param companyId
	 * @return
	 * @throws DetailException
	 */
	List<TreeObject<JcaMenuDto>> getTreeMenuByUser(Long companyId, String langCode, Long userId) throws DetailException;

	/**
	 * @param companyId
	 * @param langCode
	 * @param userId
	 * @return
	 */
	List<JcaMenuDto> getListMenuByUser(Long companyId, String langCode, Long userId);
}
