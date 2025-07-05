/*******************************************************************************
 * Class        ：RoleForTeamInfoListReq
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
 * RoleForTeamInfoListReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class RoleForTeamInfoListReq {
    @ApiModelProperty(notes = "List role for team",  required = true, position = 1)
    private List<RoleForTeamInfoReq> listRoleForTeamInfoReq;
}
