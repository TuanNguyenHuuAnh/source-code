package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentInfoSearchResultDto {
	private String no;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String agentStatus;
	private String partnerCode;
	private String operationalModel;	// mo hinh hoat dong
	private String unitCode;			// mã đơn vị
	private String unitName;			// tên đơn vị
}
