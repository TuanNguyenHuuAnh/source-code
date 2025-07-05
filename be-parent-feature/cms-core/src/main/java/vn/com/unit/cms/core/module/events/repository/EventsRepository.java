package vn.com.unit.cms.core.module.events.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.account.dto.RegisterAccountDto;
import vn.com.unit.cms.core.module.events.dto.EventsDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsDto;
import vn.com.unit.cms.core.module.events.dto.EventsSearchDto;
import vn.com.unit.cms.core.module.events.entity.Events;
import vn.com.unit.db.repository.DbRepository;

import java.util.Date;
import java.util.List;

public interface EventsRepository extends DbRepository<Events, Long>{

    List<EventsDto> getListEventByDate(@Param("eventDate") Date eventDate, @Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("agentCode") String user
    , @Param("search") CommonSearchWithPagingDto search);

    int countByCondition(@Param("eventDate") Date eventDate, @Param("agentCode") String user);

    String getMaxEventCode(@Param("prefix") String prefixCodeNot);

    List<EventsDto> getAllEventByDateInMonth(@Param("eventDate") Date eventDate, @Param("agentCode") String user);

    RegisterAccountDto getAccountCandidateByEmail(@Param("email") String email, @Param("fullname") String fullname, @Param("phone") String phone);
    
    int countEventsByCondition(@Param("search") EventsSearchDto search);
    
    List<EventsDto> getListEventsByCondition(@Param("search") EventsSearchDto search);
    
    EventsDto getDetailEvent(@Param("eventId") String eventId, @Param("createBy") String createBy);
    
    EventsDto getEventByCode(@Param("eventCode") String eventId);
    
    @Modifying
    void updateEvent(@Param("event") EventsDto event);
    
    int getEventProcessing(@Param("id") Long id);
    
    EventsDto getParticipantInfo(@Param("eventCode") String eventCode);
}
