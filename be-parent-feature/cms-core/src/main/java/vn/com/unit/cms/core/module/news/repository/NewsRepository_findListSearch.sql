WITH TBL_DATA AS (
	SELECT
		  news.id								AS	id
	  	, news.code								AS	code
	  	, mType.LABEL							AS	NEWS_TYPE
	  	, category.label						AS  NEWS_CATEGORY
		, news.M_NEWS_CATEGORY_ID				AS NEWS_CATEGORY_ID
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
	  	, news.M_NEWS_TYPE_ID
	  	, news.sort								AS	sort
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
	WHERE
		news.delete_date is null	
		AND news.customer_type_id = /*searchDto.customerTypeId*/9
)
SELECT *
FROM TBL_DATA TBL
WHERE
	1 = 1
	
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/
	)
	/*END*/
	
/*IF orders != null*/
ORDER BY /*$orders*/TBL.ID
-- ELSE ORDER BY TBL.ENABLED DESC, TBL.POSTED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/