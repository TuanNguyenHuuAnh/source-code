SELECT * FROM JCA_M_SYSTEM_SETTING_SYNC 
WHERE 
	DELETED_ID = 0 
	AND DELETED_DATE IS NULL 
	/*IF isSync != null*/
	AND IS_SYNC = /*isSync*/1 
	/*END*/ 
	/*IF isSync != null*/
	AND CREATED_DATE <= /*maxDate*/'' 
	/*END*/ 
	/*IF companyId != null*/
	AND COMPANY_ID = /*companyId*/
	/*END*/ 
	ORDER BY ID ASC