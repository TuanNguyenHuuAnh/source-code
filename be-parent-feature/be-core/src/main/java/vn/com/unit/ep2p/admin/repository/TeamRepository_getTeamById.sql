select 
	team.*,
	team.id AS team_id,
	co.name  AS company_name
from jca_team team
LEFT JOIN
	jca_company co ON team.company_id = co.id and co.DELETED_ID = 0
where team.id = /*id*/
