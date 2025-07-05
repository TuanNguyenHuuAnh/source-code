

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
	, news.POSTED_DATE			as POSTED_DATE
	, news.EXPIRATION_DATE		as EXPIRATION_DATE
	, newsLan.KEY_WORD			as KEY_WORD
FROM m_news news
LEFT JOIN m_news_language newsLan ON (newsLan.m_news_id = news.id AND newsLan.delete_date is null)
LEFT JOIN m_news_category_language category ON (category.m_news_category_id = news.m_news_category_id 
												AND category.m_language_code = newsLan.m_language_code
												AND category.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = news.customer_type_id 
											AND customerType.m_language_code = newsLan.m_language_code
											AND customerType.delete_date is null)
INNER JOIN M_NEWS_CATEGORY cate
ON (news.M_NEWS_CATEGORY_ID = cate.ID)
INNER JOIN M_NEWS_TYPE TYPE_
ON(CATE.M_NEWS_TYPE_ID = TYPE_.ID)
WHERE
	news.delete_date is null
	AND UPPER(newsLan.m_language_code) = UPPER(/*language*/'vi')	
	AND newsLan.LINK_ALIAS = /*link*/'test-1'
	AND TYPE_.CODE = /*type*/'NEWS'
	AND news.M_NEWS_CATEGORY_ID = /*categoryId*/
	AND CONVERT(DATE, news.POSTED_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(news.EXPIRATION_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())