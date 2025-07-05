package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class PolicyMaturedDetailParam {
	@In
	public String policyNo;
	@ResultSet
	public List<PolicyMaturedResultDto> data;
}
