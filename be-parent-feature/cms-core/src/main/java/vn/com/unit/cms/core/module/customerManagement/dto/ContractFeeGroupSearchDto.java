package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractFeeGroupSearchDto extends CommonSearchWithPagingDto{
    private String agentCodeSearch;
    private String orgId;
    private String agentGroup;
    private String paidType;
    private Object agentCode;
    private Object managerAgentCode;
    private Object policyNo;
    private String agentName;
    private String orgName;
    private String agentType;
}
