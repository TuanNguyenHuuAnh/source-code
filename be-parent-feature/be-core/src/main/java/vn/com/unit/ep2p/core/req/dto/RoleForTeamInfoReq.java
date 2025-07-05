/*******************************************************************************
 * Class        ：ReqAccountDto
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：SonND
 * Change log   ：2020/12/02：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ReqAuthenDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class RoleForTeamInfoReq {
	@ApiModelProperty(notes = "Id of team", example = "1", required = true, position = 1)
    private Long teamId;
	@ApiModelProperty(notes = "Id of role", example = "[\"1\",\"2\",\"3\",\"4\"]", required = true, position = 2)
    private List<Long> listRoleId;
	@ApiModelProperty(notes = "Id of company", example = "1",  position = 3)
    private Long companyId;
}
