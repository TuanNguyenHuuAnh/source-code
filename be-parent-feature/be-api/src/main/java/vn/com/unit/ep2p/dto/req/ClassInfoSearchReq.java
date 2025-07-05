package vn.com.unit.ep2p.dto.req;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfoSearchReq {
    
    @ApiModelProperty(notes = "Channel of class", example = "Agency", required = false, position = 0)
    private String channel;
    
    @ApiModelProperty(notes = "Province of class", example = "TPHCM", required = false, position = 0)
    private String province;
    
    @ApiModelProperty(notes = "Type of class", example = "classType1", required = false, position = 0)
    private String classType;
    
    @ApiModelProperty(notes = "Code of class", example = "classCode1", required = false, position = 0)
    private String classCode;
    
    private Date startDateFrom;
    
    private Date startDateTo;
}
