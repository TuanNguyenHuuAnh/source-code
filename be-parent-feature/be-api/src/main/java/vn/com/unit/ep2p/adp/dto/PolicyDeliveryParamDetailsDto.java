package vn.com.unit.ep2p.adp.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class PolicyDeliveryParamDetailsDto {
    @In
    public String agentCode;
    @In
    public String policyNo;
    @ResultSet
    public List<PolicyDeliveryDetailsDto> datas;
}
