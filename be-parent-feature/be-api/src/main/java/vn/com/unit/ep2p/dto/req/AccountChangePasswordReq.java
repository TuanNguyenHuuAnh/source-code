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

/**
 * AccountAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class AccountChangePasswordReq {
    @ApiModelProperty(notes = "Id of account", example = "1")
    private Long userId;
    
    @ApiModelProperty(notes = "Password old of account", example = "123")
    private String passwordOld;
    
	@ApiModelProperty(notes = "Password new of account", example = "123")
    private String passwordNew;
	
	@ApiModelProperty(notes = "Confirm password new", example = "123")
    private String confirmPasswordNew;

    @ApiModelProperty(notes = "Agent code", example = "130747")
    private String agentCode;
}
