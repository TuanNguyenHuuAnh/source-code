package vn.com.unit.cms.core.module.report.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class ReportResultSumaryParam {
    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String agentGroup;
    @ResultSet
    public List<ReportResultSumaryDto> datas;
}
