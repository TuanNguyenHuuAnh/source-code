package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class CmsAgentDetailPagingParam {
	@In
	public String territory;
	@In
	public String region;
	@In
	public String office;
	@In
	public String area;
	@In
	public String position;
	@In
	public String agentCode;
	@ResultSet
	public List<CmsAgentDetail> data;
}
