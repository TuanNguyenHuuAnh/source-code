package vn.com.unit.cms.core.module.candidate.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;

public class CheckIsBDOHParam {
	@In
	public Integer agentType;
	@In
	public String username;
	@Out
	public Boolean isBdoh;
}
