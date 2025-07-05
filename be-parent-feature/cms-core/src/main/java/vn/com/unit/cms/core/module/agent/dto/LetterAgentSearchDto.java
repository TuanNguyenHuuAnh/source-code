package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Setter
@Getter
public class LetterAgentSearchDto extends CommonSearchWithPagingDto {
	private String agentCode;
	private String letterCategoryName;
	private String createdDate;
	private String oldAgentType;
	private String newAgentType;
}
