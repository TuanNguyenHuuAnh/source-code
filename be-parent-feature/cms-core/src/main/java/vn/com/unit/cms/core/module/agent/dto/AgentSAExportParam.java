package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.ResultSet;

public class AgentSAExportParam {
	@ResultSet
	public List<AgentSAExportDto> data;
}
