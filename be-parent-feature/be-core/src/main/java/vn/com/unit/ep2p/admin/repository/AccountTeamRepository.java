/*******************************************************************************
 * Class        AccountAssignRepository
 * Created date 2017/07/18
 * Lasted date  2017/07/18
 * Author       phunghn
 * Change log   2017/07/1801-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAccountTeamDto;
import vn.com.unit.core.entity.JcaAccountTeam;
import vn.com.unit.core.repository.JcaAccountTeamRepository;

/**
 * AccountAssignRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface AccountTeamRepository extends JcaAccountTeamRepository {

	/**
	 * findByAccountId
	 * @param accountId
	 * @return
	 * @author trieuvd
	 */
	List<JcaAccountTeamDto> findByAccountId(@Param("accountId") Long accountId);
	
	/**
	 * deleteAccountTeam
	 * @param teamId
	 * @param accountId
	 * @author trieuvd
	 */
	@Modifying
	void deleteAccountTeam(@Param("accountTeam") JcaAccountTeamDto accountTeam, @Param("user") String user, @Param("date") Date date);
	
	@Modifying
    public void deleteAccountForTeam(@Param("userId") Long userId, @Param("teamId") Long teamId);
	
	/**
	 * findByAccountIdsAndTeamId
	 * @param accountId
	 * @param teamId
	 * @return List<AccountTeam>
	 * @author KhuongTH
	 */
	List<JcaAccountTeam> findByAccountIdsAndTeamId(@Param("accountIds") List<Long> accountId, @Param("teamId") Long teamId);
	
	/**
	 * @param codes
	 * @return
	 */
	List<Long> findGroupIdsByCodes(@Param("codes") List<String> codes, @Param("companyId") Long companyId);
	
	/**
	 * findGroupsByAccountId
	 * @param accountId
	 * @return
	 */
	List<Long> findGroupsByAccountId(@Param("accountId") Long accountId);
	
	@Modifying
	public void deleteByAccountId(@Param("accountId") Long accountId);

	/**
	 * @author vunt
	 * {@code} delete all account team
	 */
	@Modifying
	public void deleteAll();
	
}
