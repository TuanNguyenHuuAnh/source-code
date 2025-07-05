/*******************************************************************************
 * Class        RoleForAccountRepository
 * Created date 2017/03/07
 * Lasted date  2017/03/07
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/03/0701-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.entity.JcaRoleForAccount;
import vn.com.unit.core.repository.JcaRoleForAccountRepository;

public interface RoleForAccountRepository extends JcaRoleForAccountRepository {
    List<JcaRoleForAccountDto> findByAccountId(@Param("accountId") long accountId);
    
    @Modifying
    void deleteRoleForAccountListByUsername(@Param("username") String username);
    
    // RoleForAccountRepository_update
    @Modifying
    void updateRole(@Param("roleForAccountUpdate") JcaRoleForAccount roleForAccountUpdate);

    /**
     * @param accountId
     * @return
     */
    List<JcaRoleForAccountDto> findViewDetailByAccountId(@Param("accountId") Long accountId);

	/*-----------------------BEGIN_ORACLE----------------------*/

	// RoleForAccountRepository_findViewDetailByAccountIdOracle
	List<JcaRoleForAccountDto> findViewDetailByAccountIdOracle(@Param("accountId") Long accountId);

	// RoleForAccountRepository_findByAccountIdOracle
	List<JcaRoleForAccountDto> findByAccountIdOracle(@Param("accountId") long accountId);

	// RoleForAccountRepository_deleteRoleForAccountListByUsernameOracle
	@Modifying
	void deleteRoleForAccountListByUsernameOracle(@Param("username") String username);

	// RoleForAccountRepository_updateOracle
	@Modifying
	void updateOracle(@Param("roleForAccountUpdate") JcaRoleForAccount roleForAccountUpdate);

	/*-----------------------END_ORACLE----------------------*/
	
	public JcaRoleForAccountDto findRoleIdAndAccountId(@Param("roleId") long roleId, @Param("accountId") long accountId);

	@Modifying
	public void deleteRoleIdAndAccountId(@Param("roleId") long roleId, @Param("accountId") long accountId);
	
	@Modifying
    public void deleteAccountId(@Param("accountId") long accountId);

}
