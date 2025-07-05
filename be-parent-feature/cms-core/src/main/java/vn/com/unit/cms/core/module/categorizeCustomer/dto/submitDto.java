package vn.com.unit.cms.core.module.categorizeCustomer.dto;


import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class submitDto {
	private String proposalNo;
	private String clientCode;
	
	private String businessCode;
	private String agentCode;
	
	private String createdBy;
	
	private Date createdDate;
	private String clientOther;
	private String businessOther;
	

}
