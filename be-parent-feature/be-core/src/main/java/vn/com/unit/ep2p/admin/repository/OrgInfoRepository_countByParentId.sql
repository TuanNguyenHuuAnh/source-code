select count(1) c 
from JCA_ORGANIZATION_PATH       main
    LEFT JOIN JCA_ORGANIZATION            org 
        ON main.descendant_id = org.id
where DELETED_ID = 0
	and ANCESTOR_ID = /*parentId*/''
	AND depth = 1