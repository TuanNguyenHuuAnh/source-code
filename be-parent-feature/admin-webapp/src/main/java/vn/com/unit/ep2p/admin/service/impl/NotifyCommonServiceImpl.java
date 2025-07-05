package vn.com.unit.ep2p.admin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.admin.repository.NotifyAdminRepository;
import vn.com.unit.ep2p.admin.repository.NotifyDetailAdminRepository;
import vn.com.unit.ep2p.admin.service.NotifyCommonService;
import vn.com.unit.ep2p.dto.NotifyDetailAdminEditDto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotifyCommonServiceImpl implements NotifyCommonService {
	
	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;
	
	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;
	
	@Autowired
	private NotifyAdminRepository notifyRepository;
	
	@Autowired
	private NotifyDetailAdminRepository notifyDetailRepository;

	public static final String PREFIX_CODE_NOT = "NOT";
	
	@Override
	public void insertNotify(List<ADPDeviceTokenDto> dataInsert, String content, String linkNotify) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.tbNotification"
				+ " (sAgentId, sDeviceToken, sMessage, nType, project, dtSubmitDate, nActive, dtCreatedDate, IsSend, LinkNotify)"
				+ " VALUES (?,?,?,'20','eApp_AD',GETDATE(),0,GETDATE(),1,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
		String value = jcaSystemConfigService.getValueByKey("BATCH_SIZE", 1L);
		int batchSize = Integer.parseInt(value);
		for(ADPDeviceTokenDto data : dataInsert){
			pst.setString(1, data.getUserId());
			pst.setString(2, data.getDeviceToken());
			pst.setString(3, content);
			pst.setString(4, linkNotify);
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
	
	@Override
	public Long pushNotifyForWeb(String agentCode, String title, String contents, String link) {
    	//save notify
		NotifyAdmin entity = new NotifyAdmin();
		entity.setNotifyCode(getNotifyCode());
		entity.setNotifyTitle(title);
		entity.setNotifyType(2);
		entity.setContents(contents);
		entity.setLinkNotify(link);
		entity.setSendImmediately(true);
		entity.setActive(true);
		entity.setApplicableObject("ALL");
		entity.setCreateBy("system");
		entity.setSendDate(new Date());
		entity.setSend(true);
		entity.setFc(false);
		entity.setCreateDate(new Date());
		entity.setNotifyType(1);
		entity.setSaveDetail(false);
		notifyRepository.save(entity);
		
		//save notify detail
		NotifyDetailAdminEditDto entityDetail = new NotifyDetailAdminEditDto();
		entityDetail.setNotifyId(entity.getId());
		entityDetail.setAgentCode(new Long(agentCode));
		entityDetail.setReadAlready(false);
		notifyDetailRepository.save(entityDetail);
		
		return entity.getId();
	}
	
	private String getNotifyCode() {
		SimpleDateFormat format = new SimpleDateFormat("yy");
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		return CommonUtil.getNextBannerCode(PREFIX_CODE_NOT,
				notifyRepository.getMaxNotifyCode(PREFIX_CODE_NOT
						+ format.format(new Date()) + formatMM.format(new Date())));
	}

}
