package vn.com.unit.cms.core.module.contract.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractClaimSearchDto extends CommonSearchWithPagingDto {
	private String search; //So hop dong hoac ten chu hop dong
	private String agentCode;
	private String claimNoDetail;
	private String agentGroup;
	private Object totalContract;
	private Object claimNo;
	private Object insuranceBuyer;
	private Object insuredPerson;
	private Object scanDate;
	private String policyNo;
	private String agentName;
	private String agentType;
	private String policyType;
	private Object claimType;
	private Object statusVn;
}
