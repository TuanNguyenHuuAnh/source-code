SELECT  
	sla_config.ID                 				AS ID
	,sla_config.SLA_NAME						AS SLA_NAME
	,sla_config.CALENDAR_TYPE_ID				AS CALENDAR_TYPE_ID
	,sla_config.SLA_DUE_TIME					AS SLA_DUE_TIME
	,sla_config.SLA_TIME_TYPE					AS SLA_TIME_TYPE
	,sla_config.DISPLAY_ORDER					AS DISPLAY_ORDER
	,sla_config.ACTIVED							AS ACTIVED
FROM 
	SLA_CONFIG sla_config
WHERE 
	sla_config.DELETED_ID = 0
	AND sla_config.ID = /*id*/

