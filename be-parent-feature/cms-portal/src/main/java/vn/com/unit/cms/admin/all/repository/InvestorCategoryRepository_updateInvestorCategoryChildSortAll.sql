UPDATE M_INVESTOR_CATEGORY
SET sort = /*cond.sortValue*/
WHERE id = /*cond.objectId*/
	AND delete_date IS NULL