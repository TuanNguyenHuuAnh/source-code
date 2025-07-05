package vn.com.unit.process.admin.sla.service.impl;
//package vn.com.unit.mbal.admin.sla.service.impl;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import vn.com.unit.jcanary.dto.AccountDto;
//import vn.com.unit.mbal.admin.sla.service.SlaAlertAsyncService;
//import vn.com.unit.mbal.admin.sla.service.SlaAlertService;
//import vn.com.unit.ppl.dto.OZDocDto;
//
//@Async
//@Service
//@Lazy
//@Transactional
//public class SlaAlertAsyncServiceImpl implements SlaAlertAsyncService {
//	
//	@Autowired
//	private SlaAlertService alertService;
//	
//	@Override
//	public void saveAlertSLAForTask(SecurityContext securityContext, OZDocDto ozDocDto, Date submitDate, Long taskId, String actTaskId, Long stepId, Long processId, Long referenceId, String reference, List<AccountDto> emailTo, List<AccountDto> emailCC, 
//			Long submitById, String submitBy, String link, Map<String,String> map, String lang) throws Exception {		
//		alertService.saveAlertSLA(securityContext, ozDocDto, submitDate, taskId, actTaskId, stepId, processId, referenceId, reference, emailTo, emailCC, submitById, submitBy, link, map, lang);	
//	}
//	
//}
