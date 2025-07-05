package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;

@Getter
@Setter
public class RecruitmentCandidateSaveReq extends DocumentSaveReq {
    @ApiModelProperty(notes = "Channel of data", example = "BANCAS", required = true, position = 0)
    private String channel;
}
