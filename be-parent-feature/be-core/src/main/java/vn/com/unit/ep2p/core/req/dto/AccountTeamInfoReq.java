/*******************************************************************************
 * Class        ：AccountTeamInfoReq
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AccountTeamInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AccountTeamInfoReq {
    @ApiModelProperty(notes = "Id of account", example = "1", required = true, position = 0)
    private Long accountId;
    @ApiModelProperty(notes = "Id of team", example = "1", required = true, position = 0)
    private Long teamId;
    @ApiModelProperty(notes = "Effected Date", example = "20200115", required = true, position = 0)
    private String effectedDate;
    @ApiModelProperty(notes = "Expired Date", example = "20250116", required = true, position = 0)
    private String expiredDate;
}
