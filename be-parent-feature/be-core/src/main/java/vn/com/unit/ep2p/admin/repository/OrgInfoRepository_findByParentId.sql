SELECT
	org.*
	,main.ANCESTOR_ID			as PARENT_ORG_ID 
FROM JCA_ORGANIZATION_PATH       main
    LEFT JOIN JCA_ORGANIZATION            org 
        ON main.descendant_id = org.id
WHERE DELETED_ID = 0
	AND ANCESTOR_ID = /*parentId*/''
	AND DEPTH = 1
