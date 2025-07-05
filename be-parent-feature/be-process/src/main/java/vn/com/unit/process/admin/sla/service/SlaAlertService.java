package vn.com.unit.process.admin.sla.service;
//package vn.com.unit.mbal.admin.sla.service;
//
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.security.core.context.SecurityContext;
//
//import vn.com.unit.jcanary.dto.AccountDto;
//import vn.com.unit.mbal.admin.sla.dto.SlaIntegrationDto;
//import vn.com.unit.ppl.dto.OZDocDto;
//
//public interface SlaAlertService {
//
//	/**
//	 * @param securityContext
//	 * @param submitDate
//	 * @param taskId
//	 * @param actTaskId
//	 * @param stepId
//	 * @param processId
//	 * @param referenceId
//	 * @param reference
//	 * @param emailTo
//	 * @param emailCC
//	 * @param submitById
//	 * @param submitBy
//	 * @param link
//	 * @param map
//	 * @return
//	 */
//	Date saveAlertSLA(SecurityContext securityContext, OZDocDto ozDocDto, Date submitDate, Long taskId, String actTaskId, Long stepId, Long processId, Long referenceId, String reference, List<AccountDto> emailTo, List<AccountDto> emailCC, 
//			Long submitById, String submitBy, String link, Map<String,String> map, String lang);
//	
//	/**
//	 * @param securityContext
//	 * @param companyId
//	 * @throws Exception
//	 */
//	void sendMailSlaAlertScheduled(SecurityContext securityContext, Long companyId) throws Exception;
//	
//	/**
//	 * @throws Exception
//	 */
//	void sendMailSlaAlertScheduled() throws Exception;
//	
//	/**
//	 * @param slaAlerts
//	 * @return
//	 * @throws Exception
//	 */
//	Object copyAlertToAlertHistory(List<Long> slaAlerts) throws Exception;
//	
//	/**
//	 * @param id
//	 * @throws Exception
//	 */
//	void reomoveRecordByTaskId(Long id) throws Exception;
//	
//	/**
//	 * @param fsonData
//	 * @throws Exception
//	 */
//	void saveSlaIntegrationInfo(SlaIntegrationDto object) throws Exception;
//	
//	/**
//	 * @param taskId
//	 * @param actTaskId
//	 * @param dueDate
//	 * @param actionId
//	 * @throws Exception
//	 */
//	void updateTask(Long taskId, String actTaskId, Date dueDate, Long actionId) throws Exception;
//	
//	/**
//	 * @param id
//	 * @throws SQLException
//	 */
//	void reomoveIntegrationRecordByTaskId(Long id) throws SQLException;
//	
//}