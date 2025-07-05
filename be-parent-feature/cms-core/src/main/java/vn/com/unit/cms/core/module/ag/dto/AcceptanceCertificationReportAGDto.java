package vn.com.unit.cms.core.module.ag.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeMonthsParamGa;

@Getter
@Setter
public class AcceptanceCertificationReportAGDto {
	//private List<AcceptanceCertificationIncomeAGDto> incomes;
	private IncomeMonthsParamGa incomesParams;
	private String agentCode;
	private String note1;
	private String note2;
	private String note3;
	private String trained;
	private String taxCode;
	private String agentName;
	private String bankAccount;
	private String bankName;
}