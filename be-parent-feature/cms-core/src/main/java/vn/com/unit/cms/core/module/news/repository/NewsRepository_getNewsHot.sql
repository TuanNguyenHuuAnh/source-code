SELECT top 1
		  news.id								AS	id
	  	, news.code								AS	code
	  	, mType.LABEL							AS	NEWS_TYPE
		, newType.CODE							AS  CODE_NEWS_TYPE
		, news.M_NEWS_CATEGORY_ID				AS NEWS_CATEGORY_ID
	  	, category.label						AS  NEWS_CATEGORY
	  	, newsLan.title							AS	title
	  	, news.enabled							AS	enabled
	  	, news.posted_date						AS	posted_date
	  	, news.expiration_date					AS	expiration_date
	  	, news.homepage							AS	homepage
	  	, news.event							AS	event
	  	, news.hot								AS 	hot
	  	, news.create_by						AS	create_by
	  	, news.create_date						AS  create_date
	  	, news.update_by						AS	update_by
	  	, news.update_date						AS  update_date
	  	, news.sort								AS	sort
	  	, newsLan.LINK_ALIAS					AS LINK_ALIAS
  		, newsLan.IMG_URL						AS	image_url
		, newsLan.PHYSICAL_IMG_URL				AS PHYSICAL_IMG_URL
  		, newsLan.key_word						AS	key_word
  		, newsLan.title							AS	title
  		, newsLan.short_content					AS	short_content
  		, newsLan.CONTENT 						AS CONTENT

	FROM m_news news

	LEFT JOIN m_news_language newsLan 
		ON newsLan.m_news_id = news.id 
		AND newsLan.delete_date is null

	INNER JOIN m_news_category_language category 
		ON category.m_news_category_id = news.m_news_category_id
		AND category.m_language_code = newsLan.m_language_code
		AND category.delete_date is null

	INNER JOIN M_NEWS_TYPE_LANGUAGE mType
		ON mType.M_NEWS_TYPE_ID = news.M_NEWS_TYPE_ID
		AND mType.m_language_code = newsLan.m_language_code
		AND mType.delete_date is null
	
	INNER JOIN M_NEWS_TYPE newType
		ON newType.ID = mType.M_NEWS_TYPE_ID
		AND newType.DELETE_DATE is null

	WHERE
		news.delete_date is null
		AND newsLan.M_LANGUAGE_CODE = UPPER(/*language*/'VI')
		
		AND news.ENABLED=1
		
		AND news.HOT=1
		
		AND news.POSTED_DATE <= GETDATE()
		AND ISNULL(news.EXPIRATION_DATE, DATEADD(day, 1, GETDATE())) >= GETDATE()
	    
	    /*IF mNewsCategoryId != null && mNewsCategoryId != ''*/
	    AND category.m_news_category_id=/*mNewsCategoryId*/
		/*END*/
	    
	ORDER BY news.SORT ASC