package vn.com.unit.ep2p.adp.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.Date;
import java.util.List;

public class TrackingReportParamDto {
    @In
    public String agentCode;
    @In
    public Date fromDate;
    @In
    public Date toDate;
	@In
	public String reportType;

    @ResultSet
    public List<TrackingReportDto> lstData;
}
