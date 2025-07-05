select 
	count(*)
from jca_team team
where  (team.DELETED_ID = 0 or team.DELETED_ID is null)
	/*IF !searchDto.companyAdmin*/
	AND (team.company_id is null or team.company_id = /*searchDto.companyId*/)
	/*END*/
/*BEGIN*/
	AND(
		/*IF searchDto.code != null && searchDto.code != ''*/
		OR team.code LIKE concat('%', concat(/*searchDto.code*/ ,'%'))
		/*END*/
		/*IF searchDto.nameAbv != null && searchDto.nameAbv != ''*/
		OR team.name_abv LIKE concat('%', concat(/*searchDto.nameAbv*/ ,'%'))
		/*END*/
	)
/*END*/