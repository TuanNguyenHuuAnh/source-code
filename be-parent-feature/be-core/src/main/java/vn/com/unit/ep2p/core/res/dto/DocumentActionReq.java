package vn.com.unit.ep2p.core.res.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentActionReq extends DocumentSaveReq {

    @ApiModelProperty(notes = "Button id of document to action submit", example = "1", required = true, position = 0)
    private Long buttonId;

    @ApiModelProperty()
    private String buttonText;

    @ApiModelProperty(notes = "Task id runtime of document to action submit", example = "123", required = false, position = 1)
    private String coreTaskId;

    @ApiModelProperty(notes = "Comment note action", example = "submit", required = true, position = 1)
    private String note;
    
    @ApiModelProperty(notes = "get json value param", example = "[{}]", required = false, position = 1)
    private String docInputJson ="{skip: 0}";
    
    private Boolean hasEdit;
    
    private Boolean hasUpdateData;
    
    private boolean isCloneData;
    
    private String url;
    
    private Long dataId;
}
