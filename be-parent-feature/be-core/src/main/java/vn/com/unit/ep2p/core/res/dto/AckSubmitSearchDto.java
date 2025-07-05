package vn.com.unit.ep2p.core.res.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AckSubmitSearchDto {
     private String Project;
     private String DeviceID;
     private String APIToken;
     private String AgentID;
     private String ProposalID;
     private String PotentialID;
     private String NumberOfPage;
     private String Image;//8
}
