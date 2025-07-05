/*******************************************************************************
 * Class        CustomRoleService
 * Created date 2018/01/31
 * Lasted date  2018/01/31
 * Author       Phucdq
 * Change log   2018/01/3101-00 Phucdq create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.RoleCustomizableDto;
//import vn.com.unit.ep2p.dto.JcaMenuDto;

/**
 * CustomRoleService
 * 
 * @version 01-00
 * @since 01-00
 * @author Phucdq
 */
public interface CustomRoleService {

    /**
     * getListMenuByRoleID
     *
     * @param roleID
     * @return
     * @author Phucdq
     */
    List<MenuDto> getListMenuByRoleID(Long roleID, Long companyId, String languageCode);

    /**
     * find RoleCustom By RoleId
     *
     * @param roleId
     * @return
     * @author Phucdq
     */
	RoleCustomizableDto findRoleCustomByRoleId(Long roleId);

    /**
     * updateRole
     *
     * @param roleDto
     * @return
     * @author Phucdq
     */
    boolean updateRole(List<RoleCustomizableDto> roles);

    /**
     * getListRole
     *
     * @return
     * @author Phucdq
     */
    List<RoleCustomizableDto> getListRole();
    
    /**
     * findMenuByAccountId
     *
     * @param accountId
     * @return
     * @author HUNGHT
     */
    MenuDto findMenuByAccountId(Long accountId);
    /**
     * getListByRole
     *
     * @return
     * @author 
     */
    List<RoleCustomizableDto> getListByRole(Long companyId);
    
}
