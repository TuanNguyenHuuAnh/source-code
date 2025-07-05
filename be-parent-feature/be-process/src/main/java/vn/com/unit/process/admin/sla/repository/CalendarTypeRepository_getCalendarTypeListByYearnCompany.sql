SELECT ID AS id, NAME AS name, NAME AS text
FROM SLA_CALENDAR_TYPE
WHERE DELETED_ID = 0
	/*IF companyId != null*/
	AND COMPANY_ID = /*companyId*/
	/*END*/
	/*IF companyId == null*/
	AND COMPANY_ID IS NULL
	/*END*/