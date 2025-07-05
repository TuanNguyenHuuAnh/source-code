package vn.com.unit.cms.core.module.customer.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class PolicyInforParam {

	@In
	public String policyNo;
	@ResultSet
	public List<PolicyInfoDto> data;

}
