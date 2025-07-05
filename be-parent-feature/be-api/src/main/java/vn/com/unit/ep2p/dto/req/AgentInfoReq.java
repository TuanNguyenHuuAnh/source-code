package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ForgotPasswordReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class AgentInfoReq {
    @ApiModelProperty(notes = "username", example = "100001", required = true, position = 0)
    private String username;

    @ApiModelProperty(notes = "agent Code", example = "100001", required = true, position = 0)
    private String agentCode;

    @ApiModelProperty(notes = "Password agent", example = "100001", required = true, position = 0)
    private String passwordAgent;

    @ApiModelProperty(notes = "Password GAD", example = "100001", required = true, position = 0)
    private String passwordGad;
}
