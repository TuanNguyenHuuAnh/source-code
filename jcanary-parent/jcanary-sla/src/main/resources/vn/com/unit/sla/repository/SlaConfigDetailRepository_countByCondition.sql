SELECT  count(ID) 
FROM SLA_CONFIG_DETAIL
WHERE DELETED_DATE IS NULL
	/*IF searchDto.slaConfigId != null*/
	AND SLA_CONFIG_ID = /*searchDto.slaConfigId*/ 
	/*END*/
	/*IF searchDto.alertType != null && searchDto.alertType != ''*/
	AND ALERT_TYPE = /*searchDto.alertType*/ 
	/*END*/