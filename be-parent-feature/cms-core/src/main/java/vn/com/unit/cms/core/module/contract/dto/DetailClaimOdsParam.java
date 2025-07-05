package vn.com.unit.cms.core.module.contract.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class DetailClaimOdsParam {
	@In
	public String claimNo;
	@ResultSet
	public List<ClaimOdsDetailDto> data;
}
