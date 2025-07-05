package vn.com.unit.cms.core.module.contract.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractBusinessSearchDto extends CommonSearchWithPagingDto {
	
	private String agentCode;
	private String search;
	private String policyNo;
	private String processtypecode;
	private Long requestDateMilisec;
	private Object insuranceBuyer;
	private Object policyNoSearch;
	private Object requestDate;
	private String agentGroup;
	private String agentName;
	private String agentType;

}
