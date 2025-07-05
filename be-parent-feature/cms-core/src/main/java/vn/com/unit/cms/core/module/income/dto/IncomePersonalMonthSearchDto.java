package vn.com.unit.cms.core.module.income.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomePersonalMonthSearchDto{
	private String agentCode;
	private String paymentPeriod;	//MMyyyy
	private String idPersonalIncomeTax;
	private String bankAccountNumber;
	private String bankAccountName;
	private String agentType;
	private String agentName;
}
