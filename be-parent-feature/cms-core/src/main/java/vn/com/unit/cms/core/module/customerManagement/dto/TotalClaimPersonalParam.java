package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class TotalClaimPersonalParam {
    @In
    public String agentCode;
    @ResultSet
    public List<TotalClaimDto> data;

}
