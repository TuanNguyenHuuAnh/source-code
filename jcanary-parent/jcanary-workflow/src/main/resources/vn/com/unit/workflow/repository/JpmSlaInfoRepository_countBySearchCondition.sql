SELECT 
	COUNT(sla_info.ID)
FROM 
	JPM_SLA_INFO sla_info
LEFT JOIN 
	JPM_BUSINESS bus 
	ON sla_info.BUSINESS_ID = bus.ID AND bus.DELETED_ID = 0
LEFT JOIN 
	JPM_PROCESS_DEPLOY pdeloy 
	ON sla_info.PROCESS_DEPLOY_ID = pdeloy.ID AND pdeloy.DELETED_ID = 0
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