SELECT 
    news.ID,
    news.CODE,
    newsLang.TITLE AS name
    , STUFF((
		    SELECT ', ' + proLang.title
		    FROM m_product_language proLang 
		    WHERE (CHARINDEX(news.M_PRODUCT_ID,proLang.M_PRODUCT_ID, 1) > 0
				AND proLang.DELETE_DATE is NULL
				AND proLang.m_language_code = newsLang.m_language_code)
		    FOR XML PATH(''),TYPE).value('(./text())[1]','VARCHAR(MAX)')
	  		,1,2,''
	  	) as product_name

		, STUFF((
		    SELECT ', ' + proCateLang.title
		    FROM m_product_category_language proCateLang
		    WHERE (CHARINDEX(news.M_PRODUCT_CATEGORY_ID, proCateLang.M_PRODUCT_CATEGORY_ID, 1) > 0
				 AND proCateLang.DELETE_DATE is NULL
				 AND proCateLang.m_language_code = newsLang.m_language_code)
		    FOR XML PATH(''),TYPE).value('(./text())[1]','VARCHAR(MAX)')
	  		,1,2,''
	  	) as product_category_name

		, STUFF((
		    SELECT ', ' + proCateSubLang.title
		    FROM m_product_category_sub_language proCateSubLang
		    WHERE (CHARINDEX(news.M_PRODUCT_CATEGORY_SUB_ID, proCateSubLang.M_PRODUCT_CATEGORY_SUB_ID, 1) > 0
				AND proCateSubLang.DELETE_DATE is NULL
				AND proCateSubLang.m_language_code = newsLang.m_language_code)
		    FOR XML PATH(''),TYPE).value('(./text())[1]','VARCHAR(MAX)')
	  		,1,2,''
	  	) as product_category_sub_name
FROM M_NEWS news
JOIN M_NEWS_LANGUAGE newsLang
ON (newsLang.delete_date is NULL AND newsLang.M_news_ID = news.ID)
LEFT JOIN m_news_type_language newsType ON (newsType.m_news_type_id = news.m_news_type_id 
											AND newsType.m_language_code = newsLang.m_language_code
											AND newsType.delete_date is null
											)
WHERE 
  news.delete_date is NULL
  AND news.ENABLED = 1
  AND newsLang.M_LANGUAGE_CODE = UPPER(/*lang*/)
  AND news.CUSTOMER_TYPE_ID = /*customerId*/
  /*IF status != null*/
  AND news.STATUS != /*status*/
  /*END*/
 
ORDER BY
		  product_category_name 		ASC
		, product_category_sub_name 	ASC
		, product_name 					ASC
		, news.sort 					ASC
		, newsType.label 				ASC
		, news.create_date 				DESC
		, news.ENABLED 					DESC