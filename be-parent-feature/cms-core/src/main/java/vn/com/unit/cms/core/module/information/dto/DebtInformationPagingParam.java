package vn.com.unit.cms.core.module.information.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class DebtInformationPagingParam {
	@In
	public String agentCode;
	@In
	public String yyyyMm;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<DebtInformationDto> data;
	@Out
	public Integer totalData;
}
