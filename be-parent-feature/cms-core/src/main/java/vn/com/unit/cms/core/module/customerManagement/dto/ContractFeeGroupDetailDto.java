package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractFeeGroupDetailDto {//ko cần
    private String no;
    private String policyNo;
    private String managerAgentCode;
    private String managerAgentType;
    private String managerAgentName;
    private String agentCode;
    private String agentName;
    private String agentType;
    private String insuranceContract;
    private String customerName;
    private String feeMustReceive; //-> update: fee phi can thu
    private String expectedFee;//phi mong doi
    private String parentGroup;
    private String childGroup;
    private String orgParentId;
    private String orgParentName;
    private String insuranceBuyer; //ben mua BH wating confirm
}
