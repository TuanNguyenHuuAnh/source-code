package vn.com.unit.ep2p.admin.schedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TaxCommitmentNotifyJob extends NotifyBaseJob {

	@Override
	protected String getProcedureName() {		
		return "RPT_ODS.DS_SP_GET_INFO_NOTIFICATION_TAX_COMMITMENT";
	}

	@Override
	protected String getFunctionCode() { // to write log
		return "NOTIFICATION_TAX_COMMITMENT";
	}

}