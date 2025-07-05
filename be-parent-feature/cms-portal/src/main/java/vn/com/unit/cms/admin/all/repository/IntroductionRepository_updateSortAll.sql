UPDATE m_introduction
SET sort = /*cond.sortValue*/
WHERE id = /*cond.objectId*/
	AND delete_date IS NULL