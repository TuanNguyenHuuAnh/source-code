package vn.com.unit.cms.core.module.customer.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class PolicyPotentialParam {

	@In
	public String agentCode;
	@In
	public String custNo;
	@In
	public String policyNo;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<PolicyInformationDto> data;
	@Out
	public Integer totalrows;
}
