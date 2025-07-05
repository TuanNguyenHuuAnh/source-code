SELECT
	  max(sort)
FROM m_news_category
WHERE
	delete_date is null
	AND m_news_type_id = /*typeId*/
