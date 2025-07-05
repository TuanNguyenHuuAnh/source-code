SELECT 
	count(bus.ID) 
FROM  
	JPM_BUSINESS bus
WHERE
	bus.DELETED_ID = 0
	/*IF searchDto.keySearch != null && searchDto.keySearch != ''*/
	/*BEGIN*/
	AND 
		(	
			/*IF searchDto.businessName != null && searchDto.businessName != ''*/
			UPPER(bus.BUSINESS_NAME) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.businessName*/), '%' ))
			/*END*/
			/*IF searchDto.businessCode != null && searchDto.businessCode != ''*/
			OR UPPER(bus.BUSINESS_CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.businessCode*/), '%' ))
			/*END*/
		)
	/*END*/
	/*END*/
		    
	/*IF searchDto.companyId != null*/
	AND bus.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
		    
	/*IF searchDto.processType != null && searchDto.processType != '' */
	AND bus.PROCESS_TYPE = /*searchDto.processType*/
	/*END*/