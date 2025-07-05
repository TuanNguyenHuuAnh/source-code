package vn.com.unit.cms.core.module.contract.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class ClaimAdditionalInformationOdsParam {
	@In
	public String claimid;
	@ResultSet
	public List<ClaimAdditionalInformationDto> data;
}
