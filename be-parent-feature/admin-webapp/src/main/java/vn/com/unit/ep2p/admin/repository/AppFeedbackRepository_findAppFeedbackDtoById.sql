SELECT feedback.*
	,account.FULLNAME	AS USER_NAME
FROM APP_FEEDBACK feedback 
LEFT JOIN 
	JCA_ACCOUNT account
ON
	account.ID = feedback.USER_ID
WHERE
	feedback.DELETED_ID = 0
	/*IF id !=null*/
	AND
		feedback.ID = /*id*/
	/*END*/