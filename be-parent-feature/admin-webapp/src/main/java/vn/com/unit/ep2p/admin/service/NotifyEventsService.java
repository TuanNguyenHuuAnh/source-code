package vn.com.unit.ep2p.admin.service;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import vn.com.unit.ep2p.dto.NotifyEventsDto;

public interface NotifyEventsService {
	
	List<NotifyEventsDto> getListNotifyEvents() throws ParseException;
	
	Long addNewNotify(NotifyEventsDto dto, Date createDate);
	
	List<String> getLsAgentFromEventId(Long eventId);
}
