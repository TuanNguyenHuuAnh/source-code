SELECT  ID                  		AS ID
		,SLA_CONFIG_ID				AS SLA_CONFIG_ID
		,ALERT_TYPE					AS ALERT_TYPE
		,ALERT_TIME					AS ALERT_TIME
		,ALERT_UNIT_TIME			AS ALERT_UNIT_TIME
		,TEMPLATE_ID				AS TEMPLATE_ID
		,SEND_MAIL_FLAG				AS SEND_MAIL_FLAG
		,SEND_NOTI_FLAG				AS SEND_NOTI_FLAG
		,ACTIVE_FLAG				AS ACTIVE_FLAG
FROM 
	SLA_CONFIG_DETAIL
WHERE DELETED_DATE IS NULL
	/*IF searchDto.slaConfigId != null*/
	AND SLA_CONFIG_ID = /*searchDto.slaConfigId*/ 
	/*END*/
	/*IF searchDto.alertType != null && searchDto.alertType != ''*/
	AND ALERT_TYPE = /*searchDto.alertType*/ 
	/*END*/
/*IF orders != null*/
ORDER BY /*$orders*/ID
-- ELSE ORDER BY ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/