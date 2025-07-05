SELECT  
	alert.ID                  	AS ID
	, alert.ALERT_TYPE			AS ALERT_TYPE
	, alert.ALERT_DATE			AS ALERT_DATE
	, alert.EMAIL_CONTENT		AS EMAIL_CONTENT
	, alert.EMAIL_SUBJECT		AS EMAIL_SUBJECT
	, alert.STATUS				AS STATUS
FROM 
	SLA_EMAIL_ALERT alert
WHERE alert.DELETED_ID = 0
	/*IF status != null && status != ""*/
	AND alert.STATUS = /*status*/
	/*END*/
	AND alert.ALERT_DATE BETWEEN /*fromDate*/ AND /*toDate*/;