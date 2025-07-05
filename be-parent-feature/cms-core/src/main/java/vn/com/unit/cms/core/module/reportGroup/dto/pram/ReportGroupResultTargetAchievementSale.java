package vn.com.unit.cms.core.module.reportGroup.dto.pram;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultManPowerDto;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultTargetAchievementDto;

import java.util.List;

public class ReportGroupResultTargetAchievementSale {
    @In
    public String agentCode;
    @In
    public String dataType;
    @In
    public String agentGroup;
    @In
    public String territory;
    @In
    public String region;
    @In
    public String area;
    @In
    public String office;
    @In
    public String gad;
    @In
    public String yyyyMM;
    @In
    public Integer page;
    @In
    public Integer pageSize;
    @ResultSet
    public List<ReportGroupResultTargetAchievementDto> data;
    @Out
    public Integer totalData;
}
