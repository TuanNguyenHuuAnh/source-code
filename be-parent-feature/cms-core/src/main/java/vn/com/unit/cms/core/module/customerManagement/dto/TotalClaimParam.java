package vn.com.unit.cms.core.module.customerManagement.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class TotalClaimParam {
    @In
    public String agentCode;
    @In
    public String orgId;
    @In
    public String agentGroup;
    @In
    public Integer page;
    @In
    public Integer pageSize;
    @In
    public String sort;
    @In
    public String search;
    @Out
    public Integer totalRows;
    @ResultSet
    public List<TotalClaimDto> data;
}
