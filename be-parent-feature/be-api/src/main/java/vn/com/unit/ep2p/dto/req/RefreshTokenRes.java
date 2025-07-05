package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TaiTM
 **/
@Getter
@Setter
public class RefreshTokenRes {
    @ApiModelProperty(notes = "Refresh Token", example = "aZn4850-akn45BG4", required = true, position = 0)
    public String refreshToken;
}
