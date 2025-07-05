package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRenewPasswordGadReq {

	@ApiModelProperty(notes = "Password new of account", example = "123", required = true, position = 0)
    private String password;
	
	@ApiModelProperty(notes = "Confirm password new", example = "123", required = true, position = 1)
    private String confirmPassword;
}
