package vn.com.unit.cms.core.module.agent.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;

public class CheckAccessByAgentLogin  {//extends CmsCommonPagingParamDto
	@In
	public String agentCode;
	@In
	public String orgCode;
	@In
	public String agentGroup;
	@In
	public String agentCodeChild;
	@Out
	public Integer total;
}
