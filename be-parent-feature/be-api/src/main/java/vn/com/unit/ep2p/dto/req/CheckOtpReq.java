package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.annotation.Pattern;

/**
 * ForgotPasswordReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class CheckOtpReq {
    @ApiModelProperty(notes = "Agent code", example = "100001", required = true, position = 0)
    private String agentCode;
    
    @ApiModelProperty(notes = "OTP", example = "123132", required = true, position = 0)
    private String otp;
}
