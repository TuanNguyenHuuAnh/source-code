package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;

import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;

public interface NotifyOrderService {

    String getNotifyContent();
    
    public void insertNotify(List<ADPDeviceTokenDto> dataInsert, String content) throws SQLException;
}
