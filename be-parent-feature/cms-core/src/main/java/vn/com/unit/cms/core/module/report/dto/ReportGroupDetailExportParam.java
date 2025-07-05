package vn.com.unit.cms.core.module.report.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ReportGroupDetailExportParam {
	@In
	public String agentCode;
	@In
	public String agentGroup;
	@In
	public String orgCode;
	@In
	public String yyyyMM;
	@ResultSet
	public List<ReportGroupDetailExportDto> data;

}
