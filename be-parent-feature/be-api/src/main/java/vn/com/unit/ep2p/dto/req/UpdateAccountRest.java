package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountRest {
	@ApiModelProperty(notes = "url facebook", example = "facebook.com", required = false, position = 0)
	private String urlFacebook;
	@ApiModelProperty(notes = "url zalo", example = "zalo.com", required = false, position = 0)
	private String urlZalo;
}
