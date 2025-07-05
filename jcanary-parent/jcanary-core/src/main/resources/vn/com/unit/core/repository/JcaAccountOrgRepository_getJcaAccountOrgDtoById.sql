SELECT 
	acc_org.ID					AS ACCOUNT_ORG_ID
	,acc.ID						AS USER_ID
	,acc.USERNAME				AS USER_NAME
	,org.ID						AS ORG_ID
	,org.NAME					AS ORG_NAME
	,org.CODE					AS ORG_CODE
	,pos.ID						AS POSITION_ID
	,pos.NAME					AS POSITION_NAME
	,acc_org.ACTIVED			AS ACTIVED
	,acc_org.MAIN_FLAG			AS MAIN_FLAG
FROM  
	JCA_ACCOUNT_ORG acc_org
LEFT JOIN
	JCA_ACCOUNT acc
ON
	acc.ID = acc_org.ACCOUNT_ID 
LEFT JOIN
	JCA_POSITION pos
ON
	pos.ID = acc_org.POSITION_ID
LEFT JOIN
	JCA_ORGANIZATION org
ON 
	org.ID = acc_org.ORG_ID	
	
WHERE
	acc_org.DELETED_ID = 0
	AND acc_org.ID = /*id*/
	
	