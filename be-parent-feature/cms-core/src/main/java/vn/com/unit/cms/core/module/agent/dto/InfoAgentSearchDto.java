package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;


@Getter
@Setter
public class InfoAgentSearchDto extends CommonSearchWithPagingDto {
	private String username;
	private String agentType;
	private String region;
	private Integer page;
	private Integer pageSize;
	private String view;
	private String mobile;
	private Object appointedDate;
	private Object terminatedDate;
	private Object managerAgentName;
	private Object agentStatus;
	private Object gender;
	private Object birthDay;
}
