package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ActivityGroupParam{
	@In
	public String agentCode;
	@In
	public String agentGroup;
	@ResultSet
	public List<ActivityGroupDto> dataDetail;
}
