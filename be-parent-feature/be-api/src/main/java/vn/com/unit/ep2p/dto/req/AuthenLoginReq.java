/*******************************************************************************
 * Class        ：AuthenLoginReq
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.AuthenInfoReq;

/**
 * AuthenLoginReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@ApiModel(description = "Class login properties to authen to system mbal.")
public class AuthenLoginReq extends AuthenInfoReq {

    @ApiModelProperty(notes = "user name register from system", example = "admin", required = false, position = 0)
    private String username;

    @ApiModelProperty(notes = "password of user name register from system", example = "unit", required = false, position = 0)
    private String password;

    @ApiModelProperty(notes = "system login of user name register from system", example = "UNIT_CORP", required = true, position = 0)
    private String systemCode;

    @ApiModelProperty(notes = "remember login", example = "true", required = false, position = 0)
    private Boolean rememberMe;

    @ApiModelProperty(notes = "user name password login from system", example = "crypto.AES.encrypt", required = false, position = 0)
    private String authentication;

    @ApiModelProperty(notes = "firebase idToken", example = "eyJh....", required = false, position = 0)
    private String tokenFirebase;
    
    @ApiModelProperty(notes = "device token", example = "cLuOa4Zt-i8D5-jVOIOw_h:APA91bH...", required = false, position = 0)
    private String notiToken;

    @ApiModelProperty(notes = "name of device", example = "iphone", required = false, position = 0)
    private String deviceId;
    @ApiModelProperty(notes = "name of token recapcha", example = "...", required = true, position = 0)
    private String valueToken;
}
