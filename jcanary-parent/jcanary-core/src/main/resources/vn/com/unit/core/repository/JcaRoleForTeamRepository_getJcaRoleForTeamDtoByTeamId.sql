SELECT 
  	role_team.ID     						AS ID
  	,role_team.TEAM_ID     					AS TEAM_ID
  	,role_team.ROLE_ID     					AS ROLE_ID
  	,role_team.COMPANY_ID     				AS COMPANY_ID
FROM
  	JCA_ROLE_FOR_TEAM role_team
WHERE
  	role_team.TEAM_ID = /*teamId*/