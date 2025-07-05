package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DynamicInputConfSearchReq {
    @ApiModelProperty(notes = "Channel", example = "Agency", required = false, position = 0)
    private String channel;
    
    @ApiModelProperty(notes = "Status", example = "Active", required = false, position = 0)
    private String statusInputBox;
}
