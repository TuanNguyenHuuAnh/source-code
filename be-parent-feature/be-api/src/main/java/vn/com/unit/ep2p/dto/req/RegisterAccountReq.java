package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.annotation.Pattern;

/**
 * RegisterAccountReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class RegisterAccountReq {
    @ApiModelProperty(notes = "Full name", example = "Nguyễn Văn A", required = true, position = 0)
    private String fullname;

    @ApiModelProperty(notes = "Email of account on system", example = "anv@gmail.com", required = true, position = 0)
//    @Pattern(regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String email;

    @ApiModelProperty(notes = "Notification via email", example = "true", required = false, position = 0)
    private boolean passwordToEmail;

    @ApiModelProperty(notes = "Phone", example = "0123456789", required = true, position = 0)
//    @Pattern(regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
    private String phone;

    @ApiModelProperty(notes = "Notification via phone", example = "false", required = false, position = 0)
    private boolean passWordToPhone;

    private boolean hasRead;
    private boolean confirmApplyCandidate;
    @ApiModelProperty(notes = "Current province", example = "HCM", required = false, position = 0)
    private String province;
    @ApiModelProperty(notes = "Applying province/city", example = "HCM", required = false, position = 0)
    private String provinceCity;
    @ApiModelProperty(notes = "Applying office", example = "HCM", required = false, position = 0)
    private String office;
    private String officeName;
    @ApiModelProperty(notes = "name of token recapcha", example = "...", required = true, position = 0)
    private String valueToken;
}
