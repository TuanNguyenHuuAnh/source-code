SELECT
	max(sort)
FROM m_news
WHERE
	delete_date is null
	AND CUSTOMER_TYPE_ID = /*customerId*/

