package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContractExpiresPagingParam {
	@In
	public String agentCode;
	@In
	public String agentGroup;
	@In
	public String type;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<ContractExpiresSearchResultDto> data;
	@Out
	public Integer total;
}
