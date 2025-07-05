package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSwitchAppDto {
	@ApiModelProperty(notes = "facebook flag", example = "0", required = false, position = 0)
	private Integer facebookFlag;
	@ApiModelProperty(notes = "google flag", example = "1", required = false, position = 0)
	private Integer googleFlag;
	@ApiModelProperty(notes = "apple flag", example = "0", required = false, position = 0)
	private Integer appleFlag;
	@ApiModelProperty(notes = "uid", example = "text", required = false, position = 0)
	private String uid;
}
