package vn.com.unit.ep2p.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.core.module.notify.dto.NotifyDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyInputDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyReadDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchLike;

public interface ApiNotifyService {
    
    public int countNotifyByType(Long agentCode, Integer notifyType, Integer modeView,Integer isReadAlready,String searchValues,Integer isLike);
    public List<NotifyDto> getListNotifyByType(Integer page, Integer size, Long agentCode, Integer notifyType, Integer modeView,Integer isReadAlready,String searchValues,Integer isLike);
    public NotifyReadDto saveNotifyCheckRead(Long id,Long agentCode,Integer isReadAlready);
   
    public NotifyReadDto getTotalUnread(Long agentCode);
    
    public String addNotifyByType(List<NotifyInputDto> dto) throws Exception;
    
    public boolean likeMessage(NotifySearchLike searchDto);

    String getTemplatePopupMaintain(String code);
}
