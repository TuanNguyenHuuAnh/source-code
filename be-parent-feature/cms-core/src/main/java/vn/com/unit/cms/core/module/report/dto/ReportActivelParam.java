package vn.com.unit.cms.core.module.report.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class ReportActivelParam {
	@In
	public String agentCode;
	@In
	public String yyyyMM;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<ReportActiveDto> data;
	@Out
	public Integer totalData;
}
