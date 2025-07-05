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
  count(1)
FROM 
  JCA_TEAM team
/*IF jcaAccountTeamSearchDto.nonData != null && jcaAccountTeamSearchDto.nonData*/
INNER JOIN
	accTeam accTeam
ON
  accTeam.ID  = team.ID AND accTeam.RN = 1
/*END*/
WHERE
	team.DELETED_ID = 0
	/*BEGIN*/
	AND(
		/*IF jcaAccountTeamSearchDto.teamCode != null && jcaAccountTeamSearchDto.teamCode != ''*/
		OR UPPER(team.code) LIKE concat('%', concat(UPPER(/*jcaAccountTeamSearchDto.teamCode*/) ,'%'))
		/*END*/
		/*IF jcaAccountTeamSearchDto.teamName != null && jcaAccountTeamSearchDto.teamName != ''*/
		OR UPPER(team.name_abv) LIKE concat('%', concat(UPPER(/*jcaAccountTeamSearchDto.teamName*/) ,'%'))
		OR UPPER(team.name) LIKE concat('%', concat(UPPER(/*jcaAccountTeamSearchDto.teamName*/) ,'%'))
		/*END*/
	)
	/*END*/
	/*IF jcaAccountTeamSearchDto.companyId != null*/
	AND team.COMPANY_ID = /*jcaAccountTeamSearchDto.companyId*/
	/*END*/	