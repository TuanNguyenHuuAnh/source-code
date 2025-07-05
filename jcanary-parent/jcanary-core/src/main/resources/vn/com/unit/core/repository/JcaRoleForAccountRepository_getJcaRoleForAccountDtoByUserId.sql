SELECT
	role_acc.ID							AS ROLE_FOR_ACCOUNT_ID
	,role_acc.ACCOUNT_ID				AS USER_ID
	,role_acc.ROLE_ID					AS ROLE_ID
	,role_acc.START_DATE				AS START_DATE
	,role_acc.END_DATE					AS END_DATE		
FROM 
	JCA_ROLE_FOR_ACCOUNT role_acc
WHERE 
	DELETED_ID = 0
	AND role_acc.ACCOUNT_ID	 = /*userId*/
	