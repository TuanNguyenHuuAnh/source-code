SELECT *
FROM m_news_category newsCate
WHERE
	newsCate.delete_date is NULL
	AND newsCate.m_customer_type_id = /*customerId*/
	AND newsCate.id != /*categoryId*/
ORDER BY newsCate.sort