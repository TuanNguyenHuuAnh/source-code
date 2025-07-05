package vn.com.unit.ep2p.core.res.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentMainFileUploadReq extends DocumentMainFileInfoReq {

    @ApiModelProperty(notes = "File base64 upload", example = "", required = true, position = 0)
    private String fileBase64;
    
    @ApiModelProperty(notes = "File name upload", example = "FileTest.pdf", required = true, position = 0)
    private String fileName;
    
    @ApiModelProperty(notes = "Id of main file", example = "1", required = false, position = 0)
    private Long mainFileId;

}
