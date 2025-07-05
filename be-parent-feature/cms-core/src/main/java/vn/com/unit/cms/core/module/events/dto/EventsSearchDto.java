package vn.com.unit.cms.core.module.events.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class EventsSearchDto extends CommonSearchWithPagingDto {
	private String agentName;			//	ten TVTC
	private String agentCode;			//	agentCode
	private String agentType;			//	chuc vu
	private String eventCode;
	private String eventTitle;
	private String eventLocation;
	private Object eventDate;
	private String eventStatus;
	private String createBy;
}
