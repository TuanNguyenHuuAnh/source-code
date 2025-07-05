package vn.com.unit.cms.core.module.information.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebtInformationDto {
	private String no;
	private String type;
	private Date arisingDate;
	private Integer debtMoney;
	private String explain;
	private Integer total;
	private BigDecimal debtMoneyDto;
	
	private String allowancetype;
}
