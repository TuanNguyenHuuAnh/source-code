package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AgentGroupResponse {	
    public String sourceSystem;
    public String group;
    public String policyNo;
    public String proposalNo;
    public String deviceId;
    public String clientId;

}
