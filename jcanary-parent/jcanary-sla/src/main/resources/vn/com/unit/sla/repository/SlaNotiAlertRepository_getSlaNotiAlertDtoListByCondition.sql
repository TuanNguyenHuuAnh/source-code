SELECT  
	alert.ID                  	AS ID
	, alert.ALERT_TYPE			AS ALERT_TYPE
	, alert.ALERT_DATE			AS ALERT_DATE
	, alert.NOTI_TEMPLATE_ID	AS NOTI_TEMPLATE_ID
	, alert.NOTI_JSON_DATA		AS NOTI_JSON_DATA
	, alert.STATUS				AS STATUS
FROM 
	SLA_NOTI_ALERT alert
WHERE
	alert.ALERT_DATE BETWEEN /*fromDate*/ AND /*toDate*/; 
	/*IF status != null && status != ""*/
	AND alert.STATUS = /*status*/
	/*END*/