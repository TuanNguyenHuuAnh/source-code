package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContractClaimReportParam {
	@In
	public String agentCode;
	@In
	public String policyType;
	@In
	public String sort;
	@In
	public String search;
	
	@ResultSet
	public List<ContractClaimResultDto> data;
	
}
