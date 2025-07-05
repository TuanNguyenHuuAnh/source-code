SELECT 
	count(1) 
FROM  
	EFO_COMPONENT component
WHERE
	component.DELETED_ID = 0
	/*IF efoComponentSearchDto.actived != null*/
	AND component.ACTIVED = /*efoComponentSearchDto.actived*/
	/*END*/	
		    
	/*IF efoComponentSearchDto.companyId != null*/
	AND component.COMPANY_ID = /*efoComponentSearchDto.companyId*/
	/*END*/
	
	/*IF efoComponentSearchDto.companyId != null*/
	AND component.FORM_ID = /*efoComponentSearchDto.companyId*/
	/*END*/