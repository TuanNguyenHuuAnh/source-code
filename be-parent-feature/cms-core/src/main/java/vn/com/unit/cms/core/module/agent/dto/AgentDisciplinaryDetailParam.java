package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class AgentDisciplinaryDetailParam {
	@In
	public String agentCode;
	@In
	public String agentName;
	@In
	public String teamType;
	@ResultSet
	public List<AgentDisciplinaryDetailDto> data;
}
