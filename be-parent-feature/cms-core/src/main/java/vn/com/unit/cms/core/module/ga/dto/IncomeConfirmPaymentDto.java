package vn.com.unit.cms.core.module.ga.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IncomeConfirmPaymentDto {
	private String mainCode;
	private String subCode;
	private String subName;
	private BigDecimal amount;
}