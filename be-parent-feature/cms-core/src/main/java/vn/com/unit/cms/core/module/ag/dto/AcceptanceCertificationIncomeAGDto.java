package vn.com.unit.cms.core.module.ag.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AcceptanceCertificationIncomeAGDto {
	private Integer orderNo;
	private String detailName;
	private BigDecimal amount;
	private String mainName;
	
	public AcceptanceCertificationIncomeAGDto (
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