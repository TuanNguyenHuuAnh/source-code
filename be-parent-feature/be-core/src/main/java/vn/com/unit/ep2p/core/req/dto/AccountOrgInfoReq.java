/*******************************************************************************
 * Class        ：ReqAccountDto
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：SonND
 * Change log   ：2020/12/02：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

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
public class AccountOrgInfoReq {
	@ApiModelProperty(notes = "Id of account", example = "1", required = true, position = 0)
    private Long userId;
	@ApiModelProperty(notes = "Id of organization", example = "1", required = true, position = 0)
    private Long orgId;
	@ApiModelProperty(notes = "Id of position", example = "1", required = true, position = 0)
    private Long positionId;
	@ApiModelProperty(notes = "Account organization actived", example = "true", required = true, position = 0)
    private Boolean actived;
	@ApiModelProperty(notes = "Account organization has organizaion main and position main", example = "false", required = true, position = 0)
    private Boolean mainFlag;
}
