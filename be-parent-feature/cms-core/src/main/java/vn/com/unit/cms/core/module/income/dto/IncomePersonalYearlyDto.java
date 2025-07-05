package vn.com.unit.cms.core.module.income.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomePersonalYearlyDto {
	private String agentCode;
	private String agentName;
	private String year;					//YYYY
	private GrossYearDto gross;				//Thu nhập trước thuế 
	private TaxYearDto tax;					//Tổng số thuế đã nộp
	private DeductYearDto deduct;			//Khấu trừ 
	private RealIncomeYearDto realIncome;	//Thực lãnh
	private Integer lastYearBalance;		//Số dư năm trước
	private Integer totalGenerated;			//Tổng số phát sinh
	private Integer totalTaxPaid;			//Tổng số thuế đã nộp
	private Integer totalRecoveriesYear;	//Tổng số thu hồi trong năm
	private Integer yearEndBalance;			//Số dư cuối năm
}
