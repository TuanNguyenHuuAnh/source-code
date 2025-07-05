package vn.com.unit.ep2p.service;

import vn.com.unit.cms.core.module.agent.dto.ActivityAgentDto;
import vn.com.unit.cms.core.module.agent.dto.ActivityGroupDto;

public interface ApiActivityAgentService{
	ActivityAgentDto getActivityAgent(String agentCode);
	
	ActivityGroupDto getActivityGroup(String agentCode, String agentGroup);
}
