--
-- NewsRepository_findNewsListForSorting.sql

SELECT
	  news.id					AS	id
  	, news.code					AS	code
  	, category.label			AS  category_name
	, news.M_NEWS_CATEGORY_ID	AS NEWS_CATEGORY_ID
  	, newsType.label 			AS  type_name
  	, newsLan.title				AS	title
  	, news.create_date			AS  create_date
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
	AND news.m_news_type_id = /*cond.newsTypeId*/
	AND category.m_news_category_id = /*cond.categoryId*/
	
	/*IF cond.customerTypeId != null */
	AND news.customer_type_id = /*cond.customerTypeId*/
	/*END*/
ORDER BY news.sort ASC, ISNULL(news.update_date, news.create_date) DESC, news.ENABLED DESC