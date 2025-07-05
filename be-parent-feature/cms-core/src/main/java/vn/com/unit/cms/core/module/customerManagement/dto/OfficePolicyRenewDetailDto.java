package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OfficePolicyRenewDetailDto {
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private Integer sumOfPolRenewed;
	private BigDecimal paymentAmount;
	private Integer sumOfPolWillRenew;
	private BigDecimal paymentAmountEstimate;
	private String no;
}
