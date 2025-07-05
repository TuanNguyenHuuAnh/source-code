package vn.com.unit.cms.core.module.autoter.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.report.dto.ReportGetDetailDto;

public class AutoterSumaryParam {
	@In
	public String agentCode;
	@In
	public String orgCode;
	@In
	public String agentGroup;
	@In
	public String dateKey;
	@In
	public String search;
	
	@ResultSet
	public List<AutoterSumaryDto> data;
	@Out
	public Integer totalData;
}
