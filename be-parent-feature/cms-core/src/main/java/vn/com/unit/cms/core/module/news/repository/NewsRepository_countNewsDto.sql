SELECT
	count(*)
FROM m_news news
LEFT JOIN m_news_language newsLan ON (newsLan.m_news_id = news.id AND newsLan.delete_date is null)
LEFT JOIN m_news_category_language category ON (category.m_news_category_id = news.m_news_category_id 
												AND category.m_language_code = newsLan.m_language_code
												AND category.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = news.customer_type_id 
											AND customerType.m_language_code = newsLan.m_language_code
											AND customerType.delete_date is null)
WHERE
	news.delete_date is null
	AND news.POSTED_DATE <= GETDATE()
	AND ISNULL(news.EXPIRATION_DATE, DATEADD(day, 1, GETDATE())) >= GETDATE()
	AND UPPER(newsLan.m_language_code) = UPPER(/*language*/'vi')
	/*IF searchCond.mNewsCategoryId != null*/
	AND category.M_NEWS_CATEGORY_ID = /*searchCond.mNewsCategoryId*/''
	/*END*/
	/*IF searchCond.ignoreNews != null*/
	AND news.ID NOT IN /*searchCond.ignoreNews*/()
	/*END*/
	/*IF searchCond.homepage != null*/
	AND news.HOMEPAGE = /*searchCond.homepage*/1
	/*END*/
	/*IF searchCond.idHotNews != null && searchCond.idHotNews != ''*/
	AND news.ID != /*searchCond.idHotNews*/
	/*END*/
	/*IF searchCond.key != null && searchCond.key != '' && searchCond.ignoreNews == null*/
	AND newsLan.TITLE LIKE concat('%', /*searchCond.key*/,'%')
	/*END*/
	/*IF modeView == 0*/
	AND news.ENABLED = 1
	/*END*/
	/*IF channel != null && channel != ''*/
    AND isnull(news.CHANNEL, 'AG') like UPPER('%' + /*channel*/ + '%')
    /*END*/
	