package vn.com.unit.cms.core.module.reportGroup.dto.pram;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultManPowerDetailDto;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultManPower2Dto;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultPremiumDto;

import java.util.List;

public class ReportGroupResultManpowerPram {
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
    public List<ReportGroupResultManPower2Dto> data;
    @Out
    public Integer totalData;
    
}
