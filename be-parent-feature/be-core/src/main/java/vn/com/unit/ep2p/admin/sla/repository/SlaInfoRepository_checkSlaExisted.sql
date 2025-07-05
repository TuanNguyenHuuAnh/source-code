SELECT COUNT(*) FROM APP_SLA_INFO
WHERE
	DELETED_ID IS NULL
	/*IF companyId != null*/
	AND COMPANY_ID = /*companyId*/
	/*END*/
	/*IF companyId == null*/
	AND COMPANY_ID IS NULL 
	/*END*/
	/*IF name != null && name != ''*/
	AND SLA_NAME = /*name*/
	/*END*/
	