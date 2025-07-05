package vn.com.unit.cms.core.module.reportGroup.dto.pram;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultPremiumDto;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupTotalDto;

import java.util.List;

public class ReportGroupResultTotalPram {
    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String agentGroup;
    @In
    public String yyyyMM;
    @In
    public String dataType;

    @In
    public Integer page;
    @In
    public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
    @ResultSet
    public List<ReportGroupTotalDto> data;
    
    @Out
    public Integer totalData;
}
