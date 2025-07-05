SET NOCOUNT ON;

WITH LIST_NEWS_CATEGORY AS(
SELECT
	  news.id					AS	id
  	, news.code					AS	code
	, news.M_NEWS_CATEGORY_ID	AS NEWS_CATEGORY_ID
  	, news.sub_title			AS	sub_title
	, news_lang.LINK_ALIAS		AS LINK_ALIAS
	, type_.LINK_ALIAS			AS PARENT_LINK_ALIAS
  	, news_lang.IMG_URL			AS	image_url
  	, news_lang.key_word			AS	key_word
  	, news.enabled				AS	enabled
  	, news_lang.title				AS	title
  	, news_lang.short_content		AS	short_content
  	, news_lang.CONTENT 			AS CONTENT
  	, news.create_date			AS  create_date
  	, news.create_by			AS  create_by
	, news.SORT					AS SORT
 	
FROM M_NEWS_TYPE_LANGUAGE lang_type
INNER JOIN M_NEWS_TYPE type_
ON (type_.ID = lang_type.M_NEWS_TYPE_ID)
INNER JOIN M_NEWS_CATEGORY cate
ON (cate.M_NEWS_TYPE_ID = lang_type.M_NEWS_TYPE_ID 
	AND lang_type.M_LANGUAGE_CODE = /*language*/'VI')

INNER JOIN M_NEWS news
ON (cate.ID = news.M_NEWS_CATEGORY_ID 
AND	news.DELETE_BY is null
AND type_.CODE IN ('NEWS','ACTIVITY')
/*IF modeView == 0*/
	AND news.ENABLED = 1
/*END*/
/*IF searchDto.type != null */
AND lang_type.M_NEWS_TYPE_ID = /*searchDto.type*/'100002'
/*END*/
)
INNER JOIN M_NEWS_LANGUAGE news_lang
ON (news_lang.M_NEWS_ID = news.ID)
WHERE 1 = 1

	AND (dbo.fn_dms_date2str(  news.POSTED_DATE ) <= dbo.fn_dms_date2str( news.EXPIRATION_DATE  )
    AND dbo.fn_dms_date2str(  news.EXPIRATION_DATE )> dbo.fn_dms_date2str(  GETDATE()  )
    OR news.EXPIRATION_DATE is null)
	AND news_lang.M_LANGUAGE_CODE = /*language*/'VI'
	/*IF searchDto.key != null && searchDto.key != ''*/
		AND( CHARINDEX(/*searchDto.key*/'tin',news_lang.TITLE )>0
		or CHARINDEX(/*searchDto.key*/'tin',news_lang.SHORT_CONTENT)>0
		)
	/*END*/
)
SELECT CATE.id					AS	id
  	, CATE.code					AS	code
	, CATE.NEWS_CATEGORY_ID		AS NEWS_CATEGORY_ID
  	, CATE.sub_title			AS	sub_title
	, CATE.LINK_ALIAS			AS LINK_ALIAS
	, CATE.PARENT_LINK_ALIAS			AS PARENT_LINK_ALIAS
  	, CATE.image_url			AS	image_url
  	, CATE.key_word				AS	key_word
  	, CATE.enabled				AS	enabled
  	, CATE.title				AS	title
  	, CATE.short_content		AS	short_content
  	, CATE.CONTENT 				AS CONTENT
	, NULL 						AS	CONTENT_STRING
  	, CATE.create_date			AS  create_date
  	, CATE.create_by			AS  create_by
	, CATE.SORT					AS SORT
FROM LIST_NEWS_CATEGORY CATE
ORDER BY
	sort 					DESC
OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/5 ROWS ONLY
	