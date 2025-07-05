package vn.com.unit.cms.core.module.contract.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class ContractBusinessPersonalExportParam {
	@In
	public String agentCode;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<ContractBusinessSearchResultDto> data;
}
