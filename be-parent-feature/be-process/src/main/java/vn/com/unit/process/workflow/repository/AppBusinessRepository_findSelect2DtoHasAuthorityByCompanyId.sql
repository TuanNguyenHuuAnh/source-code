SELECT 
	bus.ID 					AS ID
	, bus.BUSINESS_NAME 	AS TEXT
	, bus.BUSINESS_NAME 	AS NAME
FROM 
	JPM_BUSINESS bus
WHERE
	bus.DELETED_ID = 0 
	AND (bus.PROCESS_TYPE = 3 OR bus.AUTHORITY = 1) 
	/*IF companyId != null*/
	AND (COMPANY_ID = /*companyId*/1
		  OR COMPANY_ID IS NULL ) 
	/*END*/
	/*IF companyId == null*/
	AND COMPANY_ID IS NULL 
	/*END*/
ORDER BY
	bus.BUSINESS_NAME 
	
