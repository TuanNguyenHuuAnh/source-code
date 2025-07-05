SELECT 
	COUNT(1)
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
