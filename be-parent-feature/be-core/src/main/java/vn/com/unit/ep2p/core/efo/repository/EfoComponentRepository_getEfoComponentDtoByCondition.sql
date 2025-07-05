SELECT 
	 component.ID				AS COMPONENT_ID
	,component.FORM_ID			AS FORM_ID
	,component.COMPANY_ID		AS COMPANY_ID	
	,component.COMPANY_NAME		AS COMPANY_NAME
	,component.DISPLAY_ORDER	AS DISPLAY_ORDER
	,component.ACTIVED		AS ACTIVED
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
	
/*IF orders != null*/
ORDER BY /*$orders*/FORM_ID
-- ELSE ORDER BY cat.FORM_ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/