package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotifeeDto {

	private Integer type;
	private String title;
	private String contents;
	private String agentCode;
	private String agentName;
	private String channel;
}
