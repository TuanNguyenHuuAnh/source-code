package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class OfficeInsuranceSearchDto extends CommonSearchWithPagingDto{

	private String agentName;
	private String agentCode;
	private String agentType;
	private String orgId;
	private String orgName;
	private String agentGroup;
}
