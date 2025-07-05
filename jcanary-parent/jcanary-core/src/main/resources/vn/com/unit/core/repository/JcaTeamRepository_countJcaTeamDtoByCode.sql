SELECT 
	COUNT(1)
FROM 
	JCA_TEAM team
WHERE 
	team.DELETED_ID = 0
	AND team.CODE = /*code*/
