SELECT
	  ISNULL(max(newsType.sort) , 0)
FROM m_news_type newsType
JOIN m_news_type_language newsTypeLang
ON (newsTypeLang.delete_date is NULL
	AND newsTypeLang.M_NEWS_TYPE_ID = newsType.ID
)
WHERE
	newsType.delete_date is null
	AND newsType.m_customer_type_id = /*customerId*/
	AND newsTypeLang.M_LANGUAGE_CODE = UPPER(/*lang*/)