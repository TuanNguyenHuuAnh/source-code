UPDATE m_faqs_category
SET sort = /*cond.sortValue*/
WHERE id = /*cond.objectId*/
	AND delete_date IS NULL