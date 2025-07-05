package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Setter
@Getter
public class TotalInsuranceContractGroupSearchDto extends CommonSearchWithPagingDto{
    private String agentCode;
    private String orgId;
    private String agentGroup;
}
