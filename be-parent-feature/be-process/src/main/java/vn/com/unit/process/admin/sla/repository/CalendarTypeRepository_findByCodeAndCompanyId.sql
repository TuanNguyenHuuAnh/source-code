SELECT
	*
FROM SLA_CALENDAR_TYPE
WHERE DELETED_ID = 0
AND code = /*code*/'' 
/*IF companyId != null*/
AND company_id = /*companyId*/0
/*END*/