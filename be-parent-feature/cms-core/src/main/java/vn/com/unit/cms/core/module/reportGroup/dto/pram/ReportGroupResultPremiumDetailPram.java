package vn.com.unit.cms.core.module.reportGroup.dto.pram;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultManPowerDetailDto;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultManPowerDto;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultPremiumDetailDto;

import java.util.List;

public class ReportGroupResultPremiumDetailPram {
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
    public String dataLevel;
    @In
    public Integer page;
    @In
    public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
    @ResultSet
    public List<ReportGroupResultPremiumDetailDto> data;
    @Out
    public Integer totalData;
}
