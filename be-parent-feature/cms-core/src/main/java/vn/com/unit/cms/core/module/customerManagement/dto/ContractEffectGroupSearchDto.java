package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractEffectGroupSearchDto extends CommonSearchWithPagingDto{
    private String agentCodeSearch;
    private String agentGroup;
    private String policyType;
    private Object agentCode;
    private Object managerAgentCode;
    private String agentName;
    private String agentType;
    private String orgName;
    private String orgId;
    // Survey
 	private String surveyStatus;				// Trạng thái khảo sát: 0: Không đồng ý mua mới, 1: Đồng ý mua mới, 2: Phân vân
}
