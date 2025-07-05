package vn.com.unit.cms.core.module.ga.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeConfirmPaymentParamGa;

@Getter
@Setter
public class AcceptanceCertificationReportDto {
	private IncomeConfirmPaymentParamGa incomesParams;
	private String year;
	private String month;
	private String office;
	private String bankAccountNumber;
	private String bankAccountName;
}