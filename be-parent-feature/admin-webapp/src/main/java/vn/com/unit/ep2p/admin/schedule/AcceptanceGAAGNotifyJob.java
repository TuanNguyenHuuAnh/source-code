package vn.com.unit.ep2p.admin.schedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class AcceptanceGAAGNotifyJob extends NotifyBaseJob {

	@Override
	protected String getProcedureName() {		
		return "RPT_ODS.DS_SP_GET_INFO_NOTIFICATION_ACCEPTANCE_GAAG";
	}

	@Override
	protected String getFunctionCode() { // to write log
		return "NOTICE_ACCEPTANCE_GAAG";
	}

}
