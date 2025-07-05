package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuePolicyCardPersonalDto {

	private Integer no;
	
	private String polStatus;

	private String policyOwner;

	private Date polPaidToDate;

	private String premiumStatus;

	private BigDecimal amount;
	
	private String docNo;
	
	private String policyNo;
	
	private String iconType;
}