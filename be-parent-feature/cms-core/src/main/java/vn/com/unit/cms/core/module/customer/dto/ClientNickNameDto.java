package vn.com.unit.cms.core.module.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientNickNameDto {
	@ApiModelProperty(notes = "Agent code login", example = "243080", required = true, position = 0)
	private String agentCode;
	@ApiModelProperty(notes = "customer nickname", example = "nguyen van teo", required = false, position = 1)
	private String nickName;
	@ApiModelProperty(notes = "Customer id", example = "12345678", required = true, position = 2)
	private String customerNo; // ma khach hang
	@ApiModelProperty(notes = "tvtc note", example = "nguyen van teo", required = false, position = 1)
	private String notes;
}
