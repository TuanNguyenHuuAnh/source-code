package vn.com.unit.cms.core.module.notify.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

@Getter
@Setter
public class NotifySearchResultDto extends CmsCommonSearchResultFilterDto {
	
	private String notifyCode;

	private String notifyTitle;

	private boolean isActive;
	
	private boolean isSend;
	
	private String agentCode;
	
	private String agentName;
	
	private String position;
}
