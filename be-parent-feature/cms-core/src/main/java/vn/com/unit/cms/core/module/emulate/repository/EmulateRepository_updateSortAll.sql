UPDATE M_CONTEST_SUMMARY
SET sort = /*cond.sortValue*/
WHERE 
	id = /*cond.objectId*/
	AND deleted_date IS NULL