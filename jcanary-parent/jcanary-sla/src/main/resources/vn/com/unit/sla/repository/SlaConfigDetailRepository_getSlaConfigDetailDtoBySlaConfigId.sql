SELECT  
	cond.ID                 	AS ID
	, cond.SLA_CONFIG_ID				AS SLA_CONFIG_ID
    , cond.ALERT_TYPE					AS ALERT_TYPE
    , cond.ALERT_TIME                	AS ALERT_TIME
    , cond.EMAIL_TEMPLATE_ID         	AS EMAIL_TEMPLATE_ID
    , cond.EMAIL_SEND_FLAG           	AS EMAIL_SEND_FLAG
    , cond.NOTI_TEMPLATE_ID      		AS NOTI_TEMPLATE_ID
    , cond.NOTI_SEND_FLAG           	AS NOTI_SEND_FLAG
    , cond.ALERT_UNIT_TIME 				AS ALERT_UNIT_TIME
    , cond.ACTIVED						AS ACTIVED
    , con.SLA_DUE_TIME					AS SLA_DUE_TIME
    , con.SLA_TIME_TYPE					AS SLA_TIME_TYPE
    , con.CALENDAR_TYPE_ID				AS CALENDAR_TYPE_ID
FROM SLA_CONFIG_DETAIL cond
LEFT JOIN SLA_CONFIG con on con.ID = cond.SLA_CONFIG_ID 
    AND con.DELETED_ID = 0
WHERE
	cond.DELETED_ID = 0
	/*IF slaConfigId != null */
	AND cond.SLA_CONFIG_ID = /*slaConfigId*/
	/*END*/
ORDER BY cond.UPDATED_DATE
