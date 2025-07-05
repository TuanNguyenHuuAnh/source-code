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
public class ForgotPasswordReq {
    @ApiModelProperty(notes = "Agent code", example = "100001", required = true, position = 0)
    private String agentCode;
    
    @ApiModelProperty(notes = "OTP type", example = "email_dlvn", required = true, position = 0)
    private String optTo;
    
    @ApiModelProperty(notes = "Email of account on system", example = "anv@gmail.com", required = false, position = 0)
    @Pattern(regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String emailPersonal;
    
    @ApiModelProperty(notes = "Email of account on system", example = "anv@gmail.com", required = false, position = 0)
    @Pattern(regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String emailDlvn;

    @ApiModelProperty(notes = "Phone", example = "0123456789", required = false, position = 0)
    @Pattern(regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
    private String phoneNumber;

    @ApiModelProperty(notes = "name of token recapcha", example = "...", required = false, position = 0)
    private String valueToken;
}
