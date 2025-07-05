package vn.com.unit.ep2p.admin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.repository.ProductAdminRepository;
import vn.com.unit.ep2p.admin.service.NotifyOrderService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotifyOrderServiceImpl implements NotifyOrderService {

	@Autowired
	public ProductAdminRepository productAdminRepository;
	
	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;
	
	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	@Override
	public String getNotifyContent() {
		return productAdminRepository.getNotifyContent();
	}
	
	@Override
	public void insertNotify(List<ADPDeviceTokenDto> dataInsert, String content) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.tbNotification"
				+ " (sAgentId, sDeviceToken, sMessage, nType, project, dtSubmitDate, nActive, dtCreatedDate, IsSend)"
				+ " VALUES (?,?,?,'20','eApp_AD',GETDATE(),0,GETDATE(),1)";
		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
		String value = jcaSystemConfigService.getValueByKey("BATCH_SIZE", 1L);
		int batchSize = Integer.parseInt(value);
		for(ADPDeviceTokenDto data : dataInsert){
			pst.setString(1, data.getUserId());
			pst.setString(2, data.getDeviceToken());
			pst.setString(3, content);
			pst.addBatch();
			startRow ++;
			if (startRow % batchSize == 0) {
				pst.executeBatch();
				pst.clearBatch();
				startRow = 0;
			}
		}
		pst.executeBatch();
		pst.clearBatch();
		connection.commit();
	}
}
