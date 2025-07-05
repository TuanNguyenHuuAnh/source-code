package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContractDetailParam {
	@In
	public String agentCode;
	@In
	public String policyNo;
	@In
	public String policyType;
	@ResultSet
	public List<ContractSearchAllResultDto> data;
}
