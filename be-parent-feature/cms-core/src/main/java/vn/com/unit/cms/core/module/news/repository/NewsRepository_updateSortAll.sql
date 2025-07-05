UPDATE m_news SET
	sort = /*cond.sortValue*/,
	UPDATE_DATE = /*cond.updateDate*/
WHERE
	id = /*cond.objectId*/
	AND delete_date IS NULL