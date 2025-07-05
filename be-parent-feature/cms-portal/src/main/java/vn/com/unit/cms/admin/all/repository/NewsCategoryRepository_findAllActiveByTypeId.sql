SELECT
	  *
FROM m_news_category
WHERE
	delete_date is null
	and delete_by is null
	and enabled = 1
	AND m_news_type_id = /*typeId*/
