WITH accTeam AS (
	  SELECT 
	    team.id
	    ,ROW_NUMBER() OVER (PARTITION BY accTeam.TEAM_ID ORDER BY accTeam.CREATED_DATE DESC) RN
	  FROM
	    JCA_TEAM team
	  INNER JOIN 
	    JCA_ACCOUNT_TEAM accTeam
	  ON 
	    team.ID = accTeam.TEAM_ID
	  WHERE
	    accTeam.DELETED_ID = 0
)


SELECT
	team.ID 				AS TEAM_ID,
	team.CODE 				AS CODE,
	team.NAME 				AS NAME,
	team.NAME_ABV 			AS NAME_ABV,
	team.DESCRIPTION 		AS DESCRIPTION,
	team.CREATED_DATE 		AS CREATED_DATE,
	co.ID 			  	 	AS COMPANY_ID,
	co.NAME 				AS COMPANY_NAME
FROM 
  JCA_TEAM team
/*IF jcaAccountTeamSearchDto.nonData != null && jcaAccountTeamSearchDto.nonData*/
INNER JOIN
	accTeam accTeam
ON
  accTeam.ID  = team.ID AND accTeam.RN = 1
/*END*/
LEFT JOIN
  JCA_COMPANY co
ON
  co.ID = team.COMPANY_ID AND co.DELETED_ID = 0
WHERE
	team.DELETED_ID = 0
	/*BEGIN*/
	AND(
		/*IF jcaAccountTeamSearchDto.teamCode != null && jcaAccountTeamSearchDto.teamCode != ''*/
		OR UPPER(team.code) LIKE concat('%', concat(UPPER(/*jcaAccountTeamSearchDto.teamCode*/) ,'%'))
		/*END*/
		/*IF jcaAccountTeamSearchDto.teamName != null && jcaAccountTeamSearchDto.teamName != ''*/
		OR UPPER(team.NAME) LIKE concat('%', concat(UPPER(/*jcaAccountTeamSearchDto.teamName*/) ,'%'))
		OR UPPER(team.name_abv) LIKE concat('%', concat(UPPER(/*jcaAccountTeamSearchDto.teamName*/) ,'%'))
		/*END*/
	)
	/*END*/
	/*IF jcaAccountTeamSearchDto.companyId != null*/
	AND team.COMPANY_ID = /*jcaAccountTeamSearchDto.companyId*/
	/*END*/	