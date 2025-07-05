SELECT
	  news.id					AS	id
  	, news.code					AS	code
	, news.M_NEWS_CATEGORY_ID	AS NEWS_CATEGORY_ID
  	, news.sub_title			AS	sub_title
	, newsLan.LINK_ALIAS			AS LINK_ALIAS
  	, newsLan.IMG_URL			AS	image_url
  	, newsLan.key_word				AS	key_word
  	, news.enabled				AS	enabled
  	, category.label			AS  category_name
  	, category.label 			AS  type_name
  	, newsLan.title				AS	title
  	, newsLan.short_content		AS	short_content
  	, newsLan.CONTENT 			AS CONTENT
  	, news.create_date			AS  create_date
  	, news.create_by			AS  create_by
	, newsLan.PHYSICAL_IMG_URL	AS PHYSICAL_IMG_URL
	, news.POSTED_DATE			as POSTED_DATE
	, news.EXPIRATION_DATE		as EXPIRATION_DATE
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
	AND UPPER(newsLan.m_language_code) = UPPER(/*language*/'vi')	
	/*IF id != null*/
	AND news.id = /*id*/1
	/*END*/
