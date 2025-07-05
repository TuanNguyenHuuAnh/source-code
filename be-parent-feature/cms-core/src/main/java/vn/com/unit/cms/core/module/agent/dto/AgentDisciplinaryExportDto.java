package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDisciplinaryExportDto extends AgentExportDto{
	private String appointDate;
	private String phoneNumber;
	private String address;
	private String hdNumber;
	private Integer monthInactiveNumber;
}
