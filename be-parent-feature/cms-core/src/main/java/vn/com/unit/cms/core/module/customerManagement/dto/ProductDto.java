package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductDto {
	private String productCode;
	private String productName;
	private Date dataEntryDate;
	private BigDecimal insurancePremium;
	private BigDecimal price;
	private Date effectiveDate;
	private Date dueDate;
	private String insuredPerson;
	private Date insuredPersonBirthday;
}
