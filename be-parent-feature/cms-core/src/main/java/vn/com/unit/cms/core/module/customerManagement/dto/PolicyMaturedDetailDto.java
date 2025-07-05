package vn.com.unit.cms.core.module.customerManagement.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyMaturedDetailDto {
	private String no;
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private Integer totalPolicyMatured;
	private BigDecimal totalMaturedAmount;
	private String parentAll;
	private String agentAll;
	
	// Survey
	private Integer newProposalNum;				// Count số lượng các HĐ có trạng thái "Có Nhu cầu mua mới"
}
