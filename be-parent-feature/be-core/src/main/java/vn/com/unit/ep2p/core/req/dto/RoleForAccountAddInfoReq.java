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
public class RoleForAccountAddInfoReq {
	@ApiModelProperty(notes = "Id of role", example = "1", required = true, position = 2)
    private Long roleId;
	@ApiModelProperty(notes = "Start date", example = "20210122",  position = 3)
    private String startDate;
	@ApiModelProperty(notes = "End date", example = "20210122",  position = 4)
    private String endDate;
}
