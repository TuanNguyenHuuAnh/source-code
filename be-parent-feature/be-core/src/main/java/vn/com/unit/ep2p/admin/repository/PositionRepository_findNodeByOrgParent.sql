select
	pos.id as id
	,pos.name as text
	,'open' as state
from jca_position_path       main
    LEFT JOIN jca_position            pos 
        ON main.descendant_id = pos.id
where pos.DELETED_ID = 0
	/*IF !isAdmin && id != 0*/
	AND (pos.COMPANY_ID IN /*companyIds*/()
		OR pos.COMPANY_ID IS NULL)
	/*END*/
	AND main.ANCESTOR_ID = /*id*/
	AND depth = 1
ORDER BY 
	depth, ANCESTOR_ID;