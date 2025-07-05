package vn.com.unit.ep2p.adp.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDueDateResultDto;

public class PolicyDueDateDetailParamDto {
    @In
    public String agentCode;
    @In
    public String policyNo;
    @ResultSet
    public List<ContractSearchDueDateResultDto> datas;
}
