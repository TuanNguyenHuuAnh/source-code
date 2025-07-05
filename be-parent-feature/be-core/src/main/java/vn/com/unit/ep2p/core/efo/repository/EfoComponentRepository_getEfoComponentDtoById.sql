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
	AND component.ID = /*id*/