package vn.com.unit.cms.core.module.contract.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class ContractClaimInforPending {
	@In
	public String agentCode;
	@In
	public String orgId;
	@In
	public String agentGroup;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<ContractClaimSearchResultDto> data;
	@Out
	public Integer total;
}
