package vn.com.unit.ep2p.admin.schedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TransferMovenmentPositionNotifyJob extends NotifyBaseJob {

	@Override
	protected String getProcedureName() {		
		return "RPT_ODS.DS_SP_GET_NOTICE_TRAN_MOV_POS";
	}

	@Override
	protected String getFunctionCode() { // to write log
		return "NOTICE_TRAN_MOV_POS";
	}

}
