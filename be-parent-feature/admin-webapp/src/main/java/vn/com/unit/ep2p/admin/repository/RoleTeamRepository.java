/*******************************************************************************
 * Class        RoleTeamRepository
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phunghn
 * Change log   2017/09/1201-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.repository.JcaRoleForTeamRepository;

/**
 * RoleTeamRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Repository
public interface RoleTeamRepository extends JcaRoleForTeamRepository  {

	public List<JcaRoleForTeamDto> getListRoleForTeam(@Param("teamId") Long teamId);
	
	@Modifying
	public void deleteAllRoleForTeam(@Param("teamId") Long teamId);
	
	@Modifying
	public void saveRoleForTeam(@Param("roleTeamDto") JcaRoleForTeamDto roleTeamDto, @Param("userId") Long userId);
	
	@Modifying
    public void saveRoleForTeamOracle(@Param("roleTeamDto") JcaRoleForTeamDto roleTeamDto, @Param("userId") Long userId);
}
