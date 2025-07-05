package vn.com.unit.cms.core.module.report.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class ReportActivelContractParam {
	@In
	public String agentCode;
	@In
	public Integer yyyyMM;
	@In
	public Integer type;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<ReportActiveContractMonthDto> data;
	@Out
	public Integer totalData;
}
