SELECT 
	 team.ID					AS TEAM_ID
	,team.CODE					AS CODE
	,team.NAME					AS NAME
	,team.NAME_ABV				AS NAME_ABV
	,team.DESCRIPTION			AS DESCRIPTION
    ,team.CREATED_ID			AS CREATED_ID
	,team.CREATED_DATE			AS CREATED_DATE
	,team.UPDATED_ID			AS UPDATED_ID
	,team.UPDATED_DATE			AS UPDATED_DATE
	,team.DELETED_ID			AS DELETED_ID
	,team.DELETED_DATE			AS DELETED_DATE

FROM JCA_TEAM team

WHERE  (team.DELETED_ID = 0 OR team.DELETED_ID is null)
	/*IF jcaTeamSearchDto.companyId != null && jcaTeamSearchDto.companyId != 0*/
	AND team.COMPANY_ID = /*jcaTeamSearchDto.companyId*/
	/*END*/
	/*IF jcaTeamSearchDto.companyId == null*/
	AND team.COMPANY_ID IS NULL
	/*END*/
	/*IF jcaTeamSearchDto.companyId == 0 && !jcaTeamSearchDto.companyAdmin*/
	AND (team.COMPANY_ID  IN /*jcaTeamSearchDto.companyIdList*/()
	OR team.COMPANY_ID IS NULL)
	/*END*/
/*BEGIN*/
	AND(
		/*IF jcaTeamSearchDto.code != null && jcaTeamSearchDto.code != ''*/
		OR UPPER(team.CODE) LIKE concat('%', concat(UPPER(/*jcaTeamSearchDto.code*/) ,'%'))
		/*END*/
		/*IF jcaTeamSearchDto.name != null && jcaTeamSearchDto.name != ''*/
		OR UPPER(team.NAME) LIKE concat('%', concat(UPPER(/*jcaTeamSearchDto.name*/) ,'%'))
		/*END*/
	)
/*END*/


/*IF orders != null*/
ORDER BY /*$orders*/NAME
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
