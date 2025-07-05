package vn.com.unit.ep2p.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.zxing.WriterException;

import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.entity.EventsAdmin;

public interface EventsAdminService {

    List<Db2AgentDto> getEventSaveDetail(Date date);

    void updateCheckSave(Long id);

    void insertDetailToDatabaseWithBatch(List<Db2AgentDto> dataInsert, Long id) throws SQLException;
    
    void updateProcessing(Long id);
    
    boolean isUserLDAP(String agentCode);
    
    HashMap<OfficeDto, EventsAdmin> addAutoEvent() throws WriterException, IOException;
    
    void sendMailQrCode(HashMap<OfficeDto, EventsAdmin> events);
}
