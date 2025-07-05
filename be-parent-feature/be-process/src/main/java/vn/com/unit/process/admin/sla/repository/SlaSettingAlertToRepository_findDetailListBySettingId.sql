SELECT * 
FROM SLA_SETTING_ALERT_TO alert 
WHERE
	alert.DELETED_BY is null 
	AND alert.SLA_SETTING_ID = /*settingId*/0
	ORDER BY alert.ID ASC