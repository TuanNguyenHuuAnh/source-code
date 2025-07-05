UPDATE JCA_MENU
SET display_order = /*cond.sortValue*/
WHERE id = /*cond.objectId*/
	AND deleted_id = 0