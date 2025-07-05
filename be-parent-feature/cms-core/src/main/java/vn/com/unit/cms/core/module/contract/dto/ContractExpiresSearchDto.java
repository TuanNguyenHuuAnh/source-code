package vn.com.unit.cms.core.module.contract.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractExpiresSearchDto extends CommonSearchWithPagingDto {
	private String agentCode;
	private String type;
	private String agentGroup;
	private String agentName;
	private Object policyKey;
	private Object poName;
	private Object polMaturedDt;
	private String agentType;

}
