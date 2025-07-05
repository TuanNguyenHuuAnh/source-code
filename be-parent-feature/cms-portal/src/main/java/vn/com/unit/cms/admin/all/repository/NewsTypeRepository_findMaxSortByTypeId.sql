SELECT
	  max(sort)
FROM m_news_type
WHERE
	delete_date is null
	AND m_customer_type_id = /*typeId*/
