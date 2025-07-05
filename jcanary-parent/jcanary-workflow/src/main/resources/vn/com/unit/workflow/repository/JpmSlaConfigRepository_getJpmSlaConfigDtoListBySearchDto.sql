SELECT 
	sla.ID							AS ID
	,jsla.ID 						AS JPM_SLA_CONFIG_ID
	,jsla.BUSINESS_ID				AS BUSINESS_ID
	,jsla.PROCESS_DEPLOY_ID			AS PROCESS_DEPLOY_ID
	,jsla.STEP_DEPLOY_ID			AS STEP_DEPLOY_ID
	,jsla.SLA_CALENDAR_TYPE_ID		AS SLA_CALENDAR_TYPE_ID
	,jsla.COMPANY_ID				AS COMPANY_ID
	,bus.BUSINESS_NAME 				AS BUSINESS_NAME
	,pdeloy.PROCESS_NAME 			AS PROCESS_DEPLOY_NAME
	,step_lang.LANG_CODE			AS LANG_CODE
	,step_lang.STEP_NAME			AS STEP_NAME
	,sla.SLA_DUE_TIME				AS SLA_DUE_TIME
	,sla.SLA_TIME_TYPE				AS SLA_TIME_TYPE
	,sla.ACTIVED					AS ACTIVED
FROM 
	JPM_SLA_CONFIG jsla
LEFT JOIN 
	SLA_CONFIG sla 
	ON jsla.SLA_CONFIG_ID = sla.ID AND sla.DELETED_ID = 0
LEFT JOIN 
	JPM_BUSINESS bus 
	ON jsla.BUSINESS_ID = bus.ID AND bus.DELETED_ID = 0
LEFT JOIN 
	JPM_PROCESS_DEPLOY pdeloy 
	ON jsla.PROCESS_DEPLOY_ID = pdeloy.ID AND pdeloy.DELETED_ID = 0
LEFT JOIN 
	JPM_STEP_LANG_DEPLOY step_lang 
	ON jsla.STEP_DEPLOY_ID = step_lang.STEP_DEPLOY_ID
WHERE 
	jsla.DELETED_ID = 0
	/*IF searchDto.jpmSlaInfoId != null*/
	AND jsla.JPM_SLA_INFO_ID = /*searchDto.jpmSlaInfoId*/
	/*END*/
	/*IF searchDto.langCode != null && searchDto.langCode != ''*/
	AND UPPER(replace(step_lang.LANG_CODE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.langCode*/), '%' ))
	/*END*/