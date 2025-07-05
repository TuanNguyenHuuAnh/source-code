select count(1) c 
from jca_position_path       main
    LEFT JOIN jca_position            pos 
        ON main.descendant_id = pos.id
where pos.DELETED_ID = 0
	AND ANCESTOR_ID = /*parentId*/''
	AND depth > 0