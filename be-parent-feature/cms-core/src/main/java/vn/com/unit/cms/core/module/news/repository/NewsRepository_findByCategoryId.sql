SELECT
	  *
FROM m_news
WHERE
	delete_date is null
	AND ENABLED = 1
	AND m_news_category_id =/*categoryId*/
