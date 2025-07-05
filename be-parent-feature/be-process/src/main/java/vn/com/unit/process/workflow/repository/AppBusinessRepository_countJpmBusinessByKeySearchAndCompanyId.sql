SELECT COUNT(*)
FROM
	JPM_BUSINESS bu
WHERE 
	bu.DELETED_ID = 0
	/*IF !companyAdmin*/
	AND (bu.COMPANY_ID = /*companyId*/1
	 OR bu.COMPANY_ID IS NULL )
	 /*END*/
	AND bu.IS_ACTIVE  = 1
	AND 
		bu.PROCESS_TYPE NOT IN /*processTypeIgnores*/()
