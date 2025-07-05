package vn.com.unit.cms.core.module.ga.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AcceptanceCertificationIncomeDto {
	private Integer orderNo;
	private String detailName;
	private BigDecimal amount;
	private String mainName;
	
	public AcceptanceCertificationIncomeDto (
		Integer orderNo,
		String detailName,
		BigDecimal amount,
		String mainName
	) {
		this.orderNo = orderNo;
		this.detailName = detailName;
		this.amount = amount;
		this.mainName = mainName;
	}
}