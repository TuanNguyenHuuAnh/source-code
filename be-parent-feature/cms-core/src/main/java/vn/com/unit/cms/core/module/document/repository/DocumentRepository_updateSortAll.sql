UPDATE m_document SET 
	sort = /*cond.sortValue*/
WHERE 
	id = /*cond.objectId*/
	AND delete_date IS NULL