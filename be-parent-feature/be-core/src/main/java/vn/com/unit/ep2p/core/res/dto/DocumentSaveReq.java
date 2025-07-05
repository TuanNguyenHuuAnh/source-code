package vn.com.unit.ep2p.core.res.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentSaveReq extends DocumentInfoReq {

    @ApiModelProperty(notes = "Type of process", example = "3", required = true, position = 0)
    private int processType;

//    @ApiModelProperty(notes = "List main file", example = "[base64]", required = true, position = 0)
//    private List<String> fileStream;

}
