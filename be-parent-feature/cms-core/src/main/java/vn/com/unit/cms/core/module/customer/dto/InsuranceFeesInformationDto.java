package vn.com.unit.cms.core.module.customer.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceFeesInformationDto {
	private Long id;
	private Integer No;
	private String policyNo;
	private Date effectiveDate;
	private Date paymentOfPeriodFees;//dinh ky dong phi
	private Date paymentPeriod;//ky dong phi
	private Double recurringBasicFee;//phi co ban dinh ky
	private Double estimatedFeeToPay;//phi du tinh dong
	private Double waitingFeeForFecognition;//phi cho ghi nhan
	private Double debts;//khoang no
	private Double unpaidBasicFee;//phi co ban chua đong
	private Double feeDueThisPeriod;//phi den han ky nay
	private String tollStatus;//tinh trang thu phi
}
