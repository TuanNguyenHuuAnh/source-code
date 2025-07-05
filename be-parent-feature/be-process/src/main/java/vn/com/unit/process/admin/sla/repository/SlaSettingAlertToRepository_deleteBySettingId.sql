UPDATE SLA_SETTING_ALERT_TO
SET DELETED_BY = /*deletedBy*/,
	DELETED_DATE = /*deletedDate*/
WHERE
	/*IF id != null*/
		SLA_SETTING_ID = /*id*/1
	/*END*/