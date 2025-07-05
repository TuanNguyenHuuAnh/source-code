package vn.com.unit.cms.core.module.events.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.events.dto.EventsGuestDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestSearchDto;
import vn.com.unit.cms.core.module.events.entity.EventsApplicableDetail;
import vn.com.unit.db.repository.DbRepository;

public interface EventsApplicableDetailRepository extends DbRepository<EventsApplicableDetail, Long>{

	int countEventsGuest(@Param("search") EventsGuestSearchDto search);
	
	List<EventsGuestDetailDto> getListEventsGuest(@Param("search") EventsGuestSearchDto search);
	
	@Modifying
	int updateGuest(@Param("guest") EventsGuestDetailDto guest);
	
	@Modifying
	void insertGuest(@Param("guest") EventsGuestDetailDto guest);
	
	@Modifying
	void deleteByEventId(@Param("eventId") Long eventId, @Param("agentCodes") List<String> agentCodes, @Param("idNumbers") List<String> idNumbers);
	
	List<EventsApplicableDetail> getListRegistered(@Param("eventId") Long eventId);
}
