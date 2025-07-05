package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.ep2p.admin.dto.NotifeeDto;

public interface NotifyFeeService {
    
    public Long pushListNotifyForWeb(List<NotifeeDto> notifees);
}
