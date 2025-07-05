package vn.com.unit.ep2p.core.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.LoggingService;
import vn.com.unit.ep2p.log.entity.LogAPI;
import vn.com.unit.ep2p.log.entity.LogApiExternal;
import vn.com.unit.ep2p.log.entity.LogDB;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class LoggingServiceImpl extends AbstractCommonService implements LoggingService {
	@Autowired
    @Qualifier("dataSourceLog")
    private DataSource dataSourceLog;
	    
    @Override
    public void saveLogApi(LogAPI logApi) throws SQLException {
    	String query = "INSERT INTO dbo.M_LOG_API"
				+ " (EVENT_NAME, URL_PATH, REQ_PARAMS, LOG_LEVEL, DEVICE, USER_AGENT, USER_ACCOUNT, SOURCE_IP, OUTCOME, SYSTEM, TOTAL_ACTION_TIME, CREATED_DATE)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,getDate())";
    	
    	try (
    	        Connection connection = dataSourceLog.getConnection();
    	        PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    	    ) {
			connection.setAutoCommit(false);
			
			pst.setString(1, logApi.getEventName());
			pst.setString(2, logApi.getUrlPath());
			pst.setString(3, logApi.getReqParams());
			pst.setString(4, logApi.getLogLevel());
			pst.setString(5, logApi.getDevice());
			pst.setString(6, logApi.getUserAgent());
			pst.setString(7, logApi.getUserAccount());
			pst.setString(8, logApi.getSourceIp());
			pst.setString(9, logApi.getOutcome());
			pst.setString(10, logApi.getSystem());
			pst.setLong(11, logApi.getTats());
			pst.execute();
			ResultSet rs = pst.getGeneratedKeys();
	        if(rs.next())
	        {
	        	logApi.setId(rs.getLong(1));
	        }
			connection.commit();
    	} catch (SQLException e) {
            throw e;
        }
    }
    
    public void saveLogApiExternal(LogApiExternal logApi) throws SQLException {
    	String query = "INSERT INTO dbo.M_LOG_API_EXTERNAL"
				+ " (USER_ACCOUNT, URL_PATH, REQ_JSON, RES_JSON, STATUS, TOTAL_ACTION_TIME, CREATED_DATE)"
				+ " VALUES (?,?,?,?,?,?,getDate())";    	
    	try (
    	        Connection connection = dataSourceLog.getConnection();
    	        PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    	    ) {
			connection.setAutoCommit(false);
			
			pst.setString(1, logApi.getUsername());
			pst.setString(2, logApi.getUrl());
			pst.setString(3, logApi.getJsonInput());
			pst.setString(4, logApi.getResponseJson());
			pst.setString(5, logApi.getStatus());
			pst.setLong(6, logApi.getTats());
			
			pst.execute();
			connection.commit();
    	} catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void saveLogDb(LogDB logDb) throws SQLException {
    	String query = "INSERT INTO dbo.M_LOG_DB"
				+ " (API_ID, STORE_NAME, PARAM, TOTAL_ACTION_TIME, EXCEPTION, CREATED_DATE)"
				+ " VALUES (?,?,?,?,?,getDate())"; 	
    	try (
    	        Connection connection = dataSourceLog.getConnection();
    	        PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    	    ) {
			connection.setAutoCommit(false);
			
			pst.setLong(1, logDb.getApiID());
			pst.setString(2, logDb.getStoreName());
			pst.setString(3, logDb.getParam());
			pst.setLong(4, logDb.getTats());
			pst.setString(5, logDb.getException());
			
			pst.execute();
			connection.commit();
    	} catch (SQLException e) {
            throw e;
        }
    }
}
