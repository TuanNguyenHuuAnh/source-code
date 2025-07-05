package vn.com.unit.ep2p.core.res.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentAPISearchDto {
    private String Action;
    private String Project;
    private String DeviceID;
    private String APIToken;
    private String AgentID;
    private String ProposalID;
    private String DocTypeID;
}
