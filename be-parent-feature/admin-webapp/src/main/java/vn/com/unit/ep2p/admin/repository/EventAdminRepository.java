package vn.com.unit.ep2p.admin.repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.EventsAdmin;
import vn.com.unit.ep2p.dto.NotifyEventsDto;

import java.util.Date;
import java.util.List;

public interface EventAdminRepository extends DbRepository<EventsAdmin, Long> {

    @Modifying
    void updateCheckSave(Long id);

    List<Db2AgentDto> findEventSaveDetail(@Param("date")Date date);
    
    @Modifying
    void updateProcessing(Long id);
    
    int isUserLDAP(@Param("agentCode") String agentCode);
    
    String getMaxEventCode(@Param("prefix") String prefixCodeNot);
    
    @Modifying
    void addAutoEvent(@Param("event") EventsAdmin event);
    
    List<NotifyEventsDto> getListNotifyEvents(@Param("fromDate")Date fromDate,
    		@Param("endDate")Date endDate);
}
