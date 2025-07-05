package vn.com.unit.ep2p.admin.repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.dto.NotifyAdminEditDto;

import java.util.Date;
import java.util.List;

public interface NotifyAdminRepository extends DbRepository<NotifyAdmin, Long> {

    public List<NotifyAdminEditDto> getDateExit(@Param("sendDate") String sendDate);
    
    public List<Db2AgentDto> getLsNotifyDetailById(@Param("id")Long id);
    @Modifying
    void updateIsSend(@Param("id") Long id);

    NotifyAdminEditDto findNotifyIdByCode(@Param("notifyCode") String notifyCode);

    List<Db2AgentDto> findNotifySaveDetail(@Param("date")Date date);

    @Modifying
    void updateCheckSave(Long id);

	public List<String> checkSendDate(String stringData, Long notifyId);

	String getMaxNotifyCode(@Param("prefix") String prefixCodeNot);
	
	@Modifying
    void insertDetailFromEvent(@Param("notifyId") Long notifyId, @Param("eventId") Long eventId);
	
	public List<String> getLsAgentFromEventId(@Param("eventId") Long eventId);
}
