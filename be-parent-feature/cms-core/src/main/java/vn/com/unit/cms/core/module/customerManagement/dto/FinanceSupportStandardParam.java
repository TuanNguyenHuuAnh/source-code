package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
public class FinanceSupportStandardParam {
    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String agentGroup;
    @In
    public Integer page;
    @In
    public Integer size;
    @In
    public String sort;
    @In
    public String search;
    @Out
    public Integer total;
    @ResultSet
    public List<FinanceSupportStandardDto> data;
}
