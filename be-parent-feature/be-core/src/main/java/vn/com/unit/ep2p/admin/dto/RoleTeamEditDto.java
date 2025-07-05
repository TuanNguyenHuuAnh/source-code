/*******************************************************************************
 * Class        RoleTeamEditDto
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phunghn
 * Change log   2017/09/1201-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaRoleForTeamDto;

/**
 * RoleTeamEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Getter
@Setter
public class RoleTeamEditDto {

	List<JcaRoleForTeamDto> data;
	Long teamId;

}
