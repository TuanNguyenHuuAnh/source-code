package vn.com.unit.cms.core.module.events.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class EventsGuestSearchDto extends CommonSearchWithPagingDto {
	private String eventId;
	private String name;
	private String idNumber;
	private String agentCodeGuest;
	private String attendanceStatus;
}
