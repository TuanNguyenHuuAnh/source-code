package vn.com.unit.ep2p.adp.dto;

import java.util.Date;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class AggregateReportParamDto {
	@In
    public String agentCode;
    @In
    public String areaCode;
    @In
    public String regionCode;
    @In
    public String zoneCode;
    @In
    public Date fromDate;
    @In
    public Date toDate;

    @ResultSet
    public List<AggregateReportDto> lstData;
}
