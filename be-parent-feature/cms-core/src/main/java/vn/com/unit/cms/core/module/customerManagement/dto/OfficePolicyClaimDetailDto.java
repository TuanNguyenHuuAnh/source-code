package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OfficePolicyClaimDetailDto {
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String docNo;
	private String policyNo;
	private String totalContract;
	private String no;
	private String insuranceBuyer;
	private String insuredPerson;
	private Date docCreatedDate;
	private String docType;
	private String infoWaitingToAdd;
	private String status;
	private Date expiredDateToAdd;
	private Date actionDate;
	private String reasonReject;
	private BigDecimal compensationAmount;
	private String additionalDoc;
	private String resultDate;
	private String agentAll;
	private String parentAll;
}
