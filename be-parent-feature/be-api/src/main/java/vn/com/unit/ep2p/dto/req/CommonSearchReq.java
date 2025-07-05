package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonSearchReq {

    @ApiModelProperty(notes = "languge", example = "vi", required = true, position = 0)
    private String languageCode;

    @ApiModelProperty(notes = "Page size", example = "10", required = false, position = 0)
    private Integer pageSize;

    @ApiModelProperty(notes = "page", example = "1", required = false, position = 0)
    private Integer page;
}
