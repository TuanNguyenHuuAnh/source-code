package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.ep2p.admin.dto.NotifyBaseDto;

public interface NotifyBaseService {
    
    public Long pushListNotifyForWeb(List<NotifyBaseDto> notifications);
    public void pushListNotifyForWeb(List<NotifyBaseDto> notifications, boolean oneByOne);
}
