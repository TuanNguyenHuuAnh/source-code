package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class OfficePolicyExpiredDetailDto {
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String policyNo;
	private String insuranceBuyer;
	private String insuredPerson;
	private Date paymentPeriod;
	private BigDecimal paymentPeriodAmount;
	private Date effectiveDate;
	private Date expiredDate;
	private BigDecimal paymentWaitingApproved;
	private String no;
	private String maturedType;
	private Integer totalContract; //tong so hop dong
	private Integer totalContractsWillExpired;//so hd se dao han
	private BigDecimal totalAmountPaid; //tong so tien chi tra
	private BigDecimal totalAmountPaidEstimate; //tong so tien uoc tinh chi tra
	private String agentAll;
	private String parentAll;
	
}
