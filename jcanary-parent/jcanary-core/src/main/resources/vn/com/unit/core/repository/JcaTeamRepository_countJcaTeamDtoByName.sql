SELECT 
	COUNT(1)
FROM 
	JCA_TEAM team
WHERE 
	team.DELETED_ID = 0
	AND team.NAME = /*name*/
	
	/*IF teamId != null */
	AND team.ID  != /*teamId*/
	/*END*/	
