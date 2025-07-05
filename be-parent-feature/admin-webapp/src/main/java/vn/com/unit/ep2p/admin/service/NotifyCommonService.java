package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;

import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;

public interface NotifyCommonService {
    
    public void insertNotify(List<ADPDeviceTokenDto> dataInsert, String content, String linkNotify) throws SQLException;
    
    public Long pushNotifyForWeb(String agentCode, String title, String contents, String link);
}
