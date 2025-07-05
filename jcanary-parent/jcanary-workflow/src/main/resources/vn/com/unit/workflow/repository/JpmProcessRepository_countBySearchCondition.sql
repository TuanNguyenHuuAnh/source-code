SELECT 
	count(pro.ID) 
FROM  
	JPM_PROCESS pro
WHERE
	pro.DELETED_ID = 0
	/*IF searchDto.keySearch != null && searchDto.keySearch != ''*/
	AND 
		(
			UPPER(replace(pro.PROCESS_NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.keySearch*/), '%' ))
			OR
			UPPER(replace(pro.PROCESS_CODE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.keySearch*/), '%' ))
		)
	/*END*/
		    
	/*IF searchDto.companyId != null*/
	AND pro.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
		    
	/*IF searchDto.businessId != null*/
	AND pro.BUSINESS_ID = /*searchDto.businessId*/
	/*END*/