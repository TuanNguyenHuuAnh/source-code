package vn.com.unit.cms.core.module.income.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomePersonalYearlySearchDto {
	private String agentCode;
	private String agentType;
	private String agentName;
	private String gaCode;
	private String gaName;
	private String gaSegment;
	private String bankAccountNumber;
	private String bankAccountName;
	private String idPersonalIncomeTax;
	private String year; 
}
