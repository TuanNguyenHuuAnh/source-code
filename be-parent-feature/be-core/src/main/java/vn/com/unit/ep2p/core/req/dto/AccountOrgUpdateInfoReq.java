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
public class AccountOrgUpdateInfoReq extends AccountOrgInfoReq {
    @ApiModelProperty(notes = "Id of account organization", example = "1", required = true, position = 0)
    private Long orgForAccountId;
}
