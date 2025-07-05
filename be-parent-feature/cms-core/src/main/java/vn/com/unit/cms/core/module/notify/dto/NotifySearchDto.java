package vn.com.unit.cms.core.module.notify.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

@Getter
@Setter
public class NotifySearchDto extends CmsCommonSearchFilterDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3232512860430790208L;
	
	private String notifyCode;
	
	private String notifyTitle;
	
	private String isActive;
	
	private String isSend;
	
}
