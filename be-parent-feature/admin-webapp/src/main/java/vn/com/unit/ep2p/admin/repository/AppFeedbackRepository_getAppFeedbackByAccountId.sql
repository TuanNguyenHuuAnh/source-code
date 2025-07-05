SELECT feedb.* 
FROM
	APP_FEEDBACK feedb
WHERE
	feedb.DELETED_ID = 0
	AND feedb.ACCOUNT_ID = /*accountId*/