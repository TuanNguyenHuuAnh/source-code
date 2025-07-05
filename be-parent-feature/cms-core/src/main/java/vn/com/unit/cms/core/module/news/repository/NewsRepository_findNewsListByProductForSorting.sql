SELECT 
    news.ID									AS	id
    , news.CODE								AS	code
    , newsLang.TITLE 						AS	title
  	, news.homepage							AS	homepage
  	, news.event							AS	event
  	, news.hot								AS	hot
  	, news.POSTED_DATE						AS	POSTED_DATE
FROM M_NEWS news
JOIN M_NEWS_LANGUAGE newsLang
ON (newsLang.delete_date is NULL AND newsLang.M_news_ID = news.ID)
LEFT JOIN m_news_type_language newsType ON (newsType.m_news_type_id = news.m_news_type_id 
											AND newsType.m_language_code = newsLang.m_language_code
											AND newsType.delete_date is null
											)
WHERE 
  news.delete_date is NULL
  AND news.ENABLED = '1'
  AND news.CUSTOMER_TYPE_ID = /*searchDto.custTypeId*/
  AND news.M_NEWS_TYPE_ID = /*searchDto.newsTypeId*/
  /*IF searchDto.categoryId != null*/
  AND news.M_NEWS_CATEGORY_ID = /*searchDto.categoryId*/
  /*END*/
ORDER BY news.update_date DESC, news.sort ASC, newsType.label ASC, news.ENABLED DESC