package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyMaturedSearchDto extends CommonSearchWithPagingDto {
	private String agentCodeSearch;
    private String orgId;
    private String agentGroup;
    private Object agentCode;
    private String agentName;
    private Object managerAgentCode;
    private String orgName;
    private String agentType;
}