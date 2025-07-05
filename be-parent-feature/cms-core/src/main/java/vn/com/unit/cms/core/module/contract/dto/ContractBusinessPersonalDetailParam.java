package vn.com.unit.cms.core.module.contract.dto;

import java.util.Date;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContractBusinessPersonalDetailParam {
	@In
	public String agentCode;
	@In
	public String policyNo;
	@In
	public String processtypecode;
	@In
	public String requestDate; //yyyymmdd
	@ResultSet
	public List<ContractBusinessSearchResultDto> data;
}
