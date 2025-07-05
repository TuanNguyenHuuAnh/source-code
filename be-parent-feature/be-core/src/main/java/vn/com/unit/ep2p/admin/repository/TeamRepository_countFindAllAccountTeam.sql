select 
	count(*)
from jca_team team
where  (team.DELETED_ID = 0 or team.DELETED_ID is null)
	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND team.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.companyId == null*/
	AND team.COMPANY_ID IS NULL
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND (team.COMPANY_ID  IN /*searchDto.companyIdList*/()
	OR team.COMPANY_ID IS NULL)
	/*END*/
/*BEGIN*/
	AND(
		/*IF searchDto.code != null && searchDto.code != ''*/
		OR UPPER(team.code) LIKE concat('%', concat(UPPER(/*searchDto.code*/) ,'%'))
		/*END*/
		/*IF searchDto.nameAbv != null && searchDto.nameAbv != ''*/
		OR UPPER(team.name_abv) LIKE concat('%', concat(UPPER(/*searchDto.nameAbv*/) ,'%'))
		/*END*/
	)
/*END*/
