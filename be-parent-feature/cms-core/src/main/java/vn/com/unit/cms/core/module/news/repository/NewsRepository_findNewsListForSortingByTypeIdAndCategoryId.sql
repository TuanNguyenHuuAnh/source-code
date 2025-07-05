SELECT
	newsLan.TITLE			AS name
	, news.M_NEWS_CATEGORY_ID	AS NEWS_CATEGORY_ID
	, news.*
FROM m_news news
JOIN m_news_language newsLan ON (newsLan.m_news_id = news.id AND newsLan.delete_date is null)
LEFT JOIN m_news_category_language category ON (category.m_news_category_id = news.m_news_category_id 
											AND category.m_language_code = newsLan.m_language_code
											AND category.delete_date is null)
JOIN m_news_type_language newsType ON (newsType.m_news_type_id = news.m_news_type_id 
											AND newsType.m_language_code = newsLan.m_language_code
											AND newsType.delete_date is null
											)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = news.customer_type_id 
											AND customerType.m_language_code = newsLan.m_language_code
											AND customerType.delete_date is null)
WHERE
	news.delete_date is null
	AND news.ENABLED = 1
	AND UPPER(newsLan.m_language_code) = UPPER(/*lang*/)
	/*IF customerId != null */
	AND news.customer_type_id = /*customerId*/
	/*END*/
	/*IF id != null && id != -1 */
	AND news.id != /*id*/
	/*END*/
	/*IF typeId != null && typeId != -1*/
	AND news.m_news_type_id = /*typeId*/
	/*END*/
	/*IF categoryId != null && categoryId != -1*/
	AND category.m_news_category_id = /*categoryId*/
	/*END*/
ORDER BY news.sort ASC, newsLan.title ASC