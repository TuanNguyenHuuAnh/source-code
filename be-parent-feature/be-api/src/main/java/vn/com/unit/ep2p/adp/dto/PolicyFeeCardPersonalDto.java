package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyFeeCardPersonalDto {

	private Integer no;

	private String polStatus;

	private String policyOwner;

	private BigDecimal tp;

	private BigDecimal ep;

	private Date issueDate;

	private BigDecimal rfyp; 

	private BigDecimal ryp; 
	
	private String docNo;
	
	private String policyNo;
	
}