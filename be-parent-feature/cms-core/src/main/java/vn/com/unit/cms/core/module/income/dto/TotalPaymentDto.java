package vn.com.unit.cms.core.module.income.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalPaymentDto {
    private String mainName; // chỉ tiêu 
	private BigDecimal totalAmount; // tổng chi tiêu trong năm
	private String style;
}
