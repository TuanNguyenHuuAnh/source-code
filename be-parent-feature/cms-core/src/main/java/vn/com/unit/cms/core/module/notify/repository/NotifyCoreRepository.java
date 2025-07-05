package vn.com.unit.cms.core.module.notify.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.notify.dto.NotifyDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyReadDto;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.db.repository.DbRepository;

public interface NotifyCoreRepository extends DbRepository<Notify, Long>{
    public List<NotifyDto> getListNotifyByType(@Param("offset") int offset, @Param("size") int size
            , @Param("agentCode")Long agentCode, @Param("notifyType")Integer notifyType,@Param("modeView")Integer modeView,@Param("isReadAlready")Integer isReadAlready,@Param("searchValues")String searchValues,@Param("isLike")Integer isLike);
    public int countNotifyByType(@Param("agentCode")Long agentCode, @Param("notifyType")Integer notifyType, @Param("modeView")Integer modeView,@Param("isReadAlready")Integer isReadAlready,@Param("searchValues")String searchValues,@Param("isLike")Integer isLike);
    
    public NotifyReadDto getNotifyByRead(@Param("agentCode")Long agentCode);
    
    public Notify getNotifyByAutoCode(@Param("notifyCode")String notifyCode);
}
