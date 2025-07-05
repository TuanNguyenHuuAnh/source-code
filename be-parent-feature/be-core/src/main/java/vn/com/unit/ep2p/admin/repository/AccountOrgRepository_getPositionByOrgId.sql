SELECT 
	org.POSITION_ID 	AS ID 
	, pos.CODE 			AS NAME
	, pos.NAME 			AS TEXT
FROM jca_account_org org
LEFT JOIN JCA_M_POSITION pos
	ON org.POSITION_ID = pos.ID
WHERE 
	org.ACCOUNT_ID = /*accountId*/1080
	AND org.ORG_ID = /*orgId*/1091
	AND org.DELETED_ID = 0
	AND org.ASSIGN_TYPE = 1
