package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.SlaConfigDetailInfoReq;

@Getter
@Setter
public class SlaConfigDetailUpdateReq extends SlaConfigDetailInfoReq {
    
    @ApiModelProperty(notes = "Id of sla config detail on system", example = "1", required = true, position = 0)
    private Long id;
}
