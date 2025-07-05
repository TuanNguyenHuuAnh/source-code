package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyChangedDto {

	private String policyNo;
	private String title;
	private String contents;
	private String agentCode;
	private String channel;
	private String agentCodeDuo;
}
