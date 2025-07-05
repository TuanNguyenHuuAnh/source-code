package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractExpiresPersonalDto {
	private Integer insuranceContractTotal;
	private Integer no;
	private Integer insuranceContract;
	private String customerName;
	private String mainInsurancePerson;
	private String address;
	private String phoneNumber;
	private String recurringFeePayment;//dinh ky dong phi
	private String periodicInsurancePremium;//phi bao hiem dinh ky
	private Date effectiveDate;
	private Date expirationDate;
	
}
