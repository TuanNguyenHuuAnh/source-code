SELECT 
	count(1) 
FROM  
	EFO_CATEGORY cat
WHERE
	cat.DELETED_ID = 0
	
	/*IF efoCategorySearchDto.name != null && efoCategorySearchDto.name != ''*/
	OR UPPER(replace(cat.NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*efoCategorySearchDto.name*/), '%' ))
	/*END*/
	
	/*IF efoCategorySearchDto.description != null && efoCategorySearchDto.description != ''*/
	OR UPPER(replace(cat.DESCRIPTION,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*efoCategorySearchDto.description*/), '%' ))
	/*END*/
		
	/*IF efoCategorySearchDto.actived != null*/
	AND cat.ACTIVED = /*efoCategorySearchDto.actived*/
	/*END*/	
		    
	/*IF efoCategorySearchDto.companyId != null*/
	AND cat.COMPANY_ID = /*efoCategorySearchDto.companyId*/
	/*END*/