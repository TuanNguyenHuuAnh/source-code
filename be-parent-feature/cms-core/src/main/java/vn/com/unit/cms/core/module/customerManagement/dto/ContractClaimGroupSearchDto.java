package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractClaimGroupSearchDto extends CommonSearchWithPagingDto{
    private String agentCodeSearch;
    private String orgId;
    private String agentGroup;
    private String policyType;
    private Object agentCode;
    private Object managerAgentCode;
    // private Object agentName;
    // private Object orgName;
    private Object policyNo;
    private Object poName;
    private Object liName;
    private Object claimNo;
    private Object scanDate;
    private Object claimType;
    private Object statusVn;
}
