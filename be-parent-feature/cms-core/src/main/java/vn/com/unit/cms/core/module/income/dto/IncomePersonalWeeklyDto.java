package vn.com.unit.cms.core.module.income.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomePersonalWeeklyDto {
    private Integer no;
	private String agentCode;
	private String paymentPeriod;	//ky tt MM_YYYY
	private String incomeType;		//loai thu nhap
	private Date paymentDate;		//ngay tt
	private Integer paymentAmount;	//so tien tt
	private BigDecimal paymentAmountDto;
	private String incomeName;
	//private Integer total;			//tổng
}
