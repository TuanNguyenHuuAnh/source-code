package vn.com.unit.cms.core.module.notify.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotifyInfoAgent {
	private int no;
	
	private String notifyName;
	
	private String agentCode;
	
	private String agentName;
	
	private String positionName;
}
