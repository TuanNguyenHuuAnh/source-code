package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class AgentDisciplinarySearchDto extends CommonSearchWithPagingDto{
	private String agentCode;
	private Object agentName;
	private Object disciplineType;
	private Object effectiveDate;
	private Object expirationDate;
	private Object userSuggestViolation;
	private Object decisionNumber;
	private String agentGroup;
}
