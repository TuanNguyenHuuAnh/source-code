package vn.com.unit.ep2p.admin.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeConfirmPaymentAGDto {
	private String mainCode;
	private String subCode;
	private String subName;
	private BigDecimal subAmount;
}
