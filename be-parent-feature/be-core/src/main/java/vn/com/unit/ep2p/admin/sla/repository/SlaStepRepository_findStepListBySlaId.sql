SELECT * 
FROM SLA_STEP step
WHERE
	step.DELETED_BY is null 
	AND step.SLA_INFO_ID = /*slaId*/0
	ORDER BY step.ID ASC