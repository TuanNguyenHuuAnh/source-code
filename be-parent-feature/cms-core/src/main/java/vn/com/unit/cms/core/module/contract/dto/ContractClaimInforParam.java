package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContractClaimInforParam {
	@In
	public String claimid;
	@ResultSet
	public List<ContractClaimResultDto> data;
}
