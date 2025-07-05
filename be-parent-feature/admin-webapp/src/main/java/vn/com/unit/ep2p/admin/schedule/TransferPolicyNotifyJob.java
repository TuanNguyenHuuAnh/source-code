package vn.com.unit.ep2p.admin.schedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TransferPolicyNotifyJob extends NotifyBaseJob {

	@Override
	protected String getProcedureName() {		
		return "RPT_ODS.DS_SP_GET_NOTICE_POLICY";
	}

	@Override
	protected String getFunctionCode() { // to write log
		return "NOTICE_POLICY";
	}

}
