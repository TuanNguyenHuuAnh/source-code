select
	id as id
	,name as text
	,'open' as state
from JCA_ORGANIZATION_PATH       main
    LEFT JOIN JCA_ORGANIZATION            org 
        ON main.descendant_id = org.id
where org.DELETED_ID = 0
	/*IF !isAdmin && orgId != 0*/
	AND (COMPANY_ID IN /*companyIds*/()
		OR COMPANY_ID IS NULL)
	/*END*/
	AND main.ANCESTOR_ID = /*orgId*/
	AND depth = 1
ORDER BY 
	ANCESTOR_ID, DISPLAY_ORDER;