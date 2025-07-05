package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountChangePasswordGadReq {

    @ApiModelProperty(notes = "Id of account", example = "1", required = true, position = 1)
    private Long userId;
    
    @ApiModelProperty(notes = "Password old of account", example = "123", required = true, position = 2)
    private String passwordOld;
    
    @ApiModelProperty(notes = "Password old GAD of account", example = "123", required = true, position = 2)
    private String passwordGadOld;
    
	@ApiModelProperty(notes = "Password new of account", example = "123", required = true, position = 3)
    private String passwordGadNew;
	
	@ApiModelProperty(notes = "Confirm password new", example = "123", required = true, position = 4)
    private String confirmPasswordGadNew;
}
