package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.admin.repository.SamAdminRepository;
import vn.com.unit.ep2p.admin.service.BackupDataService;
import vn.com.unit.ep2p.core.utils.SoapApiUtils;
import vn.com.unit.ep2p.dto.SamAttachmentInfoDto;
import vn.com.unit.ep2p.dto.SamAttachmentInfoParamDto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BackupDataServiceImpl implements BackupDataService {

	@Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;
	
	@Autowired
	private SamAdminRepository samRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BackupDataServiceImpl.class);

	@Override
	public void backupData() {
		try {
			sqlManagerService.call("SP_BACKUP_DATA");
		} catch (Exception ex) {
			logger.error("##Error##" + ex);
		}
	}
	
	@Override
	public boolean createWorkFlow() {
		SamAttachmentInfoParamDto common = new SamAttachmentInfoParamDto();
		sqlManagerService.call("SP_GET_SAM_ACTIVITY_NO_WF", common);
		List<SamAttachmentInfoDto> lsActivityNoWf = common.data;
		for (SamAttachmentInfoDto actInfo : lsActivityNoWf) {
			List<String> lstPathFile = new ArrayList<String>();
			String keyin = "";
			String keyinValue = "";
			if ("1".equals(actInfo.getActivityType())) {
				keyin = String.format("#AgentCode@%s#ActivityCode@%s#Notes@Plan", actInfo.getAgentCode(), actInfo.getActivityCode());
				keyinValue = String.format("%s$%s$Plan$", actInfo.getAgentCode(), actInfo.getActivityCode());
			} else {
				keyin = String.format("#AgentCode@%s#ActivityCode@%s#Notes@Report", actInfo.getAgentCode(), actInfo.getActivityCode());
				keyinValue = String.format("%s$%s$Report$", actInfo.getAgentCode(), actInfo.getActivityCode());
			}
			
			try {
				SoapApiUtils.getWorkflowId(lstPathFile, keyin, keyinValue);
				samRepository.updateWfCreated(actInfo.getActivityId(), actInfo.getActivityType());
			} catch (Exception e) {
				logger.error("iBPS has exception : " + e);
				return false;
		    }
		}
		
		return true;
	}
}
