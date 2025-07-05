SELECT * 
FROM SLA_SETTING setting
WHERE
	setting.DELETED_BY is null 
	AND setting.SLA_STEP_ID = /*stepId*/0
	ORDER BY setting.ID ASC