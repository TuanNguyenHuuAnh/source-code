package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MicSearchReq {
    
    @ApiModelProperty(notes = "region of MIC", example = "", required = false, position = 0)
    private String region;
    
    @ApiModelProperty(notes = "Province of class", example = "TPHCM", required = false, position = 0)
    private String area;

    private Date createDateFrom;
    
    private Date createDateTo;

    private String size;
    private String page;
}
