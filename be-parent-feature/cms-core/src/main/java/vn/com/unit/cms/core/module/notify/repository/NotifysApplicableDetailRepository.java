package vn.com.unit.cms.core.module.notify.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.notify.entity.NotifysApplicableDetail;
import vn.com.unit.db.repository.DbRepository;

public interface NotifysApplicableDetailRepository extends DbRepository<NotifysApplicableDetail, Long> {
    
    public NotifysApplicableDetail getDetailByIdAndAgentCode(@Param("id")Long id,@Param("agentCode")Long agentCode);
    
    public NotifysApplicableDetail getDetailByIsLike(@Param("agentCode")String agentCode,@Param("messageId")String messageId);
}
