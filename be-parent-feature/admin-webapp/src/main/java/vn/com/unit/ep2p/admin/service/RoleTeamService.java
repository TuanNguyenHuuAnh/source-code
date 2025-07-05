/*******************************************************************************
 * Class        RoleTeamService
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phunghn
 * Change log   2017/09/1201-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;


import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.service.JcaRoleForTeamService;
import vn.com.unit.ep2p.admin.dto.RoleTeamEditDto;



/**
 * RoleTeamService
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface RoleTeamService extends JcaRoleForTeamService{

	List<JcaTeamDto> getListTeam(Long companyId);
	
	List<JcaRoleForTeamDto> getListRoleForTeam(Long teamId);
	
	int saveRoleForTeam(RoleTeamEditDto roleDto);
	
	/**
	 * getListRoleForTeamWithRoleType
	 * @param teamId
	 * @return
	 * @author HungHT
	 */
	List<JcaRoleForTeamDto> getListRoleForTeamWithRoleType(Long teamId);
	
	/**
	 * saveRoleForTeamWithRoleType
	 * @param roleDto
	 * @return
	 * @author HungHT
	 */
	int saveRoleForTeamWithRoleType(RoleTeamEditDto roleDto);
	
	/**
	 * findTeamByCompanyId
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	List<JcaTeamDto> findTeamByCompanyId(Long companyId);
}
