package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;

public class PresentationReqSearchDto {

	@ApiModelProperty(notes = "channel", example = "Agency", required = true, position = 0)
	private String channel;
	
	@ApiModelProperty(notes = "status", example = "1", required = true, position = 0)
	private String statusItem;
}
