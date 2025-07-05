package vn.com.unit.ep2p.admin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.repository.SamAdminRepository;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.SamAdminService;
import vn.com.unit.ep2p.dto.SamWaitingApprovalDto;

/**
 * @version 01-00
 * @since 01-00
 * @author nt.tinh
 * @Last updated: 15/05/2024	nt.tinh SR16451 - Enhance SAM mADP/ADPortal
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SamAdminServiceImpl implements SamAdminService {

	@Autowired
	public SamAdminRepository samAdminRepository;

	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private Db2ApiService db2ApiService;
	
	private static final Logger logger = LoggerFactory.getLogger(NotifyPolicyMaturedServiceImpl.class);
	private static final String CONTENT = "Bạn có %s kế hoạch đang chờ duyệt";
	
	@Override
	public void pushNotificationForZD() {
		Connection connection = connectionProvider.getConnection();
		try {
			connection.setAutoCommit(false);
			String query = "INSERT INTO dbo.tbNotification"
					+ " (sAgentId, sDeviceToken, sMessage, nType, project, dtSubmitDate, nActive, dtCreatedDate, IsSend, LinkNotify)"
					+ " VALUES (?,?,?,'20','eApp_AD',GETDATE(),0,GETDATE(),1,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			
			List<SamWaitingApprovalDto> listWaitingApproval = samAdminRepository.getWaitingApproval();
			for (SamWaitingApprovalDto dto : listWaitingApproval) {
				List<String> zdCodes = db2ApiService.getZdCodeByZoneCode(dto.getZoneCode());
				if (zdCodes == null || zdCodes.size() == 0) {
					continue;
				}
				List<ADPDeviceTokenDto> deviceToken = db2ApiService.getDeviceTokenInfo(zdCodes);
				for (ADPDeviceTokenDto data : deviceToken) {
					pst.setString(1, data.getUserId());
	    			pst.setString(2, data.getDeviceToken());
	    			pst.setString(3, String.format(CONTENT, dto.getNumberWaitingApproval()));
	    			pst.setString(4, "");
	    			
	    			pst.addBatch();
	    			pst.executeBatch();
	    			pst.clearBatch();
				}
			}
			
			connection.commit();
		} catch (SQLException ex) {
			logger.error(ex.toString());
		}
	}
}
