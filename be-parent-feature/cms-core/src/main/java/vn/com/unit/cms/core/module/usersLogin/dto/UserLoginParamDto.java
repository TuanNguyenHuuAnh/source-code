package vn.com.unit.cms.core.module.usersLogin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginParamDto {
	@ApiModelProperty(notes = "Account ID", example = "172268", required = true, position = 0)
	private String userId;
	@ApiModelProperty(notes = "Expired Date", example = "", required = true, position = 1)
	private Long expiredDate;
}
