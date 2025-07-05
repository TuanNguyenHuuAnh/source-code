select 
	team.id as team_id,
	team.code as code,
	team.name as name,
	team.name_abv as name_abv,
	team.description as description,
	team.CREATED_DATE as CREATED_DATE,
	co.id 			  	 	AS company_id,
	co.name 				AS company_name,
	case when co.name is null then N'ZZZ' else co.name end AS order_name
from jca_team team
LEFT JOIN
	jca_company co ON team.company_id = co.id and co.DELETED_ID = 0
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
ORDER BY 
	ISNULL(team.UPDATED_DATE, team.CREATED_DATE) desc
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY
