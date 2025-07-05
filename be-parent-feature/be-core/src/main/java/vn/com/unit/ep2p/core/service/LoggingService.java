package vn.com.unit.ep2p.core.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import vn.com.unit.ep2p.log.entity.LogAPI;
import vn.com.unit.ep2p.log.entity.LogApiExternal;
import vn.com.unit.ep2p.log.entity.LogDB;


@Service
public interface LoggingService {
                   
    public void saveLogApi(LogAPI logEntiry) throws SQLException;
    
    public void saveLogApiExternal(LogApiExternal logEntiry) throws SQLException;
    
    public void saveLogDb(LogDB logEntiry) throws SQLException;
    
}
