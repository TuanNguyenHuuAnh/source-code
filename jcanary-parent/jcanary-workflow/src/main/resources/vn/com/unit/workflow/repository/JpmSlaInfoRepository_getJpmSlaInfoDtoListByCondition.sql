SELECT 
	sla_info.ID						AS ID
	,sla_info.BUSINESS_ID			AS BUSINESS_ID
	,sla_info.PROCESS_DEPLOY_ID		AS PROCESS_DEPLOY_ID
	,sla_info.SLA_CALENDAR_TYPE_ID	AS SLA_CALENDAR_TYPE_ID
	,sla_info.NAME					AS NAME
	,sla_info.COMPANY_ID			AS COMPANY_ID
	,com.NAME						AS COMPANY_NAME
	,bus.BUSINESS_NAME 				AS BUSINESS_NAME
	,pdeloy.PROCESS_NAME 			AS PROCESS_DEPLOY_NAME
FROM 
	JPM_SLA_INFO sla_info
LEFT JOIN 
	JPM_BUSINESS bus 
	ON sla_info.BUSINESS_ID = bus.ID AND bus.DELETED_ID = 0
LEFT JOIN 
	JPM_PROCESS_DEPLOY pdeloy 
	ON sla_info.PROCESS_DEPLOY_ID = pdeloy.ID AND pdeloy.DELETED_ID = 0
LEFT JOIN 
	JCA_COMPANY com 
	ON sla_info.COMPANY_ID = com.ID AND com.DELETED_ID = 0
WHERE 
	sla_info.DELETED_ID = 0
	/*IF searchDto.companyId != null*/
	AND sla_info.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.name != null && searchDto.name != ''*/
	AND UPPER(replace(sla_info.NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.name*/), '%' ))
	/*END*/
	/*IF searchDto.businessId != null*/
	AND sla_info.BUSINESS_ID = /*searchDto.businessId*/
	/*END*/
	/*IF searchDto.processDeployId != null*/
	AND sla_info.PROCESS_DEPLOY_ID = /*searchDto.processDeployId*/
	/*END*/