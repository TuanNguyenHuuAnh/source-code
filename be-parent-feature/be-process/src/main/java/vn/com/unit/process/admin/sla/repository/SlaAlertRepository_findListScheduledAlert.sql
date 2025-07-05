select *
from SLA_ALERT
WHERE 
	DELETED_BY is null 
	AND STATUS IN (1,3) 
	/*IF companyId != null*/
	AND COMPANY_ID = /*companyId*/0 
	/*END*/ 
	AND ALERT_DATE between /*startDate*/SYSDATE AND /*endDate*/SYSDATE
	AND COUNT_SENDING_ERROR <= /*count*/0 
	ORDER BY ID asc 
	