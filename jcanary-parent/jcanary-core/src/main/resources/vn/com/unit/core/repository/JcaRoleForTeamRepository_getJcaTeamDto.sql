SELECT 
  	team.ID     			AS TEAM_ID
  	,team.CODE     			AS CODE
  	,team.NAME    	 		AS NAME
  	,team.NAME_ABV     		AS NAME_ABV
  	,team.COMPANY_ID     	AS COMPANY_ID
  	,team.ACTIVED     		AS ACTIVED
FROM
  	JCA_TEAM team
WHERE
  	team.DELETED_ID = 0
  	AND team.ACTIVED = 1
  	/*IF jcaRoleForTeamSearchDto.companyId != null*/
	AND team.COMPANY_ID  = /*jcaRoleForTeamSearchDto.companyId*/
	/*END*/