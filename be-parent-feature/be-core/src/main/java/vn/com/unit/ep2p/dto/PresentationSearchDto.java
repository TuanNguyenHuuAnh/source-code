package vn.com.unit.ep2p.dto;

import vn.com.unit.core.dto.ConditionSearchCommonDto;

public class PresentationSearchDto extends ConditionSearchCommonDto{

	private String channel;
	
	private String status;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
