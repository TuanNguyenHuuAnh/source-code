package vn.com.unit.cms.core.module.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionTable {
	private String agentGroup; // cua user login
	private String orgCode; // cua user login
	private String agentCode; // cua data 
	private String username;
	private Integer gadFlag;
}
