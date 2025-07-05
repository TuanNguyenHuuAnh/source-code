package vn.com.unit.ep2p.service;

import java.util.Locale;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDetailDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDetailSearchDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinarySearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.config.SystemConfig;

public interface AgentDisciplinaryService {
	public SystemConfig getSystemConfig();
	
	ObjectDataRes<AgentDisciplinaryDto> getListAgentDisciplinaryByCondition(AgentDisciplinarySearchDto searchDto);
	
	AgentDisciplinaryDetailDto getAgentDisciplinaryDettailByCondition(AgentDisciplinaryDetailSearchDto searchDto);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportAgentDisciplinary(AgentDisciplinarySearchDto searchDto, Locale locale);
}
