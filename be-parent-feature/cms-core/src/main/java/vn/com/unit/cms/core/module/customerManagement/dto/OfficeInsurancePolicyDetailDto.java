package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OfficeInsurancePolicyDetailDto {
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String policyNo;
	private String no;
	private String insuranceBuyer;
	private String insuredPerson;
	private Date effectiveDate;
	private Date canceledDate;
	private String status;
	private BigDecimal commissionShare;
	private String productCode;
	private String productName;
	private BigDecimal price;
	private Date paymentDate;
	private BigDecimal periodAmount;
	private BigDecimal yearAmount;
}
