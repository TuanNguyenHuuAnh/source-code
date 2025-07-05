SELECT 
	count(proDeploy.ID) 
FROM  
	JPM_PROCESS_DEPLOY proDeploy
WHERE
	proDeploy.DELETED_ID = 0
	/*IF searchDto.keySearch != null && searchDto.keySearch != ''*/
	/*BEGIN*/
	AND 
		(
			/*IF searchDto.processName != null && searchDto.processName != ''*/
			UPPER(proDeploy.PROCESS_NAME) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.processName*/), '%' ))
			/*END*/
			/*IF searchDto.processCode != null && searchDto.processCode != ''*/
			OR UPPER(proDeploy.PROCESS_CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.processCode*/), '%' ))
			/*END*/
		)
	/*END*/
	/*END*/
		    
	/*IF searchDto.companyId != null*/
	AND proDeploy.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
		    
	/*IF searchDto.businessId != null*/
	AND proDeploy.BUSINESS_ID = /*searchDto.businessId*/
	/*END*/
		    
	/*IF searchDto.processId != null*/
	AND proDeploy.PROCESS_ID = /*searchDto.processId*/
	/*END*/
		    
	/*IF searchDto.fromDate != null*/
	AND proDeploy.EFFECTIVE_DATE >= /*searchDto.fromDate*/
	/*END*/
		    
	/*IF searchDto.toDate != null*/
	AND proDeploy.EFFECTIVE_DATE <= /*searchDto.toDate*/
	/*END*/