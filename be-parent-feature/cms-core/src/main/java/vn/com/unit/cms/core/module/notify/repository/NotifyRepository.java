package vn.com.unit.cms.core.module.notify.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchResultDto;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.db.repository.DbRepository;

public interface NotifyRepository extends DbRepository<Notify, Long> {
	public Page<NotifySearchResultDto> findAllNotify(@Param("searchDto") NotifySearchDto condition, Pageable pageable
			/*,@Param("lang") String lang*/);
	
    public Integer countByNotifySearchDto(@Param("searchDto") NotifySearchDto condition);

    String getMaxNotifyCode(@Param("prefix") String prefixCodeNot);
    
    Long getNotifyIdByCode(@Param("code") String code);


    public List<NotifySearchResultDto> getAgentByNotifyId(@Param("searchDto") NotifySearchDto condition);

    @Modifying
    void deleteAgentNotifyDetailByNotifyId(@Param("notifyId") Long id);
    
    @Modifying
    void updateIsSend(@Param("id") Long id);

    String getTemplateContentByCode(@Param("code") String code);
    
    @Modifying
    void insertDetailFromEvent(@Param("notifyId") Long notifyId, @Param("eventId") Long eventId);
    
    @Modifying
    void insertDetailFromEventByAgent(@Param("notifyId") Long notifyId, @Param("eventId") Long eventId, @Param("agentCodes") List<String> agentCodes);
    
    List<String> getAgentCodeDel(@Param("eventId") Long eventId, @Param("sessionKey") String sessionKey);
    
    List<String> getAgentCodeIns(@Param("eventId") Long eventId, @Param("sessionKey") String sessionKey);
    
    public List<String> getLsAgentFromEventId(@Param("eventId") Long eventId);
}
