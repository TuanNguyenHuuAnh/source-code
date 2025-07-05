/*******************************************************************************
 * Class        ：AccountAddReq
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.AccountInfoReq;

/**
 * AccountAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class AccountAddReq extends AccountInfoReq {
    @ApiModelProperty(notes = "User name of account on system", example = "theanh", required = true, position = 0)
    private String username;
	@ApiModelProperty(notes = "Password of user on system", example = "123", required = true, position = 0)
    private String password;
	@ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
	
	@ApiModelProperty(notes = "Code account", example = "ADMIN",  position = 2)
    private String code;
	
	@ApiModelProperty(notes = "Current province", example = "HCM", required = false, position = 0)
    private String province;
    @ApiModelProperty(notes = "Applying province/city", example = "HCM", required = false, position = 0)
    private String provinceCity;
    @ApiModelProperty(notes = "Applying office", example = "HCM", required = false, position = 0)
    private String office;
    private String officeName;
    private boolean confirmApplyCandidate;
}
