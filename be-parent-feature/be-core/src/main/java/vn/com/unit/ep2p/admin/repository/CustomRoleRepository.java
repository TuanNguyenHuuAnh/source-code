/*******************************************************************************
 * Class        CustomRoleRepository
 * Created date 2018/01/31
 * Lasted date  2018/01/31
 * Author       Phucdq
 * Change log   2018/01/3101-00 Phucdq create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.RoleCustomizableDto;
import vn.com.unit.ep2p.admin.entity.JcaForRoleLangdingPage;

/**
 * CustomRoleRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author Phucdq
 */
public interface CustomRoleRepository extends DbRepository<JcaForRoleLangdingPage, Long> {

    /**
     * getListMenuByRoleID
     *
     * @param roleId
     * @return
     * @author Phucdq
     */
	List<MenuDto> getListMenuByRoleID(@Param("roleId") Long roleId, @Param("companyId") Long companyId, @Param("languageCode") String languageCode);

    /**
     * getAllRole
     *
     * @return
     * @author Phucdq
     */
    List<RoleCustomizableDto> getAllRole();
    
    /**
     * findOneByRoleId
     *
     * @param roleId
     * @return
     * @author Phucdq
     */
    RoleCustomizableDto findOneByRoleId(@Param("roleId") Long roleId);
    
    /** 
     * findMenuByAccountId
     *
     * @param accountId
     * @return
     * @author HUNGHT
     */
    MenuDto findMenuByAccountId(@Param("accountId") Long accountId);

    /**
     * getListRole
     * @param companyId
     * @return
     * @author Khoakld
     */
    List<RoleCustomizableDto> getListRole(@Param("companyId") Long companyId);
    
    JcaForRoleLangdingPage findJcaForRoleLangdingPageByRoleId(@Param("roleId") Long roleId);
}
