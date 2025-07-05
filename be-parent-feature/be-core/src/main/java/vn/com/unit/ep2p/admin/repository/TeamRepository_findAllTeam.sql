select 
	team.*,
	co.name  AS company_name,
	case when co.name is null then N'ZZZ' else co.name end AS order_name
from jca_team team
LEFT JOIN
	jca_company co ON team.company_id = co.id and co.DELETED_ID = 0
where team.deleted_date is null
/*IF !companyAdmin*/
AND team.company_id = /*companyId*/1
/*END*/
order by 
	order_name,
	team.name
