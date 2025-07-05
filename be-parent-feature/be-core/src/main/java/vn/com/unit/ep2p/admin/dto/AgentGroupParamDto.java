package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AgentGroupParamDto {
	@In
    public String agentCode;
	
	@In
    public String proposalNo;
	
	@In
    public String policyKey;	
	
	
	@ResultSet
    public List<AgentInfoDb2> lstData;

}
