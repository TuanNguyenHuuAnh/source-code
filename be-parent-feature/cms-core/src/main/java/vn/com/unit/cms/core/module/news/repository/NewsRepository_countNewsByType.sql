
SET NOCOUNT ON;
DROP TABLE IF EXISTS #NEWS_TYPE_LANGUAGE;
SELECT COUNT(news.id) AS TOTAL
INTO #NEWS_TYPE_LANGUAGE
FROM M_NEWS_TYPE_LANGUAGE lang_type
INNER JOIN M_NEWS_TYPE type_
ON (type_.ID = lang_type.M_NEWS_TYPE_ID)
INNER JOIN M_NEWS_CATEGORY cate
ON (cate.M_NEWS_TYPE_ID = lang_type.M_NEWS_TYPE_ID 
	AND lang_type.M_LANGUAGE_CODE = UPPER(/*language*/'VI'))

INNER JOIN M_NEWS news
ON (cate.ID = news.M_NEWS_CATEGORY_ID 
/*IF modeView == 0*/
	AND news.ENABLED = 1
/*END*/
)
INNER JOIN M_NEWS_LANGUAGE news_lang
ON (news_lang.M_NEWS_ID = news.ID)
WHERE 1 = 1
AND	news.DELETE_BY is null
AND type_.CODE IN ('NEWS','ACTIVITY')
AND CONVERT(DATE, news.POSTED_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(news.EXPIRATION_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())
AND news_lang.M_LANGUAGE_CODE = UPPER(/*language*/'VI')
/*IF searchDto.key != null && searchDto.key != ''*/
AND (news_lang.TITLE LIKE CONCAT( '%', CONCAT(/*searchDto.key*/'dai', '%' ))
	OR news_lang.SHORT_CONTENT LIKE CONCAT( '%', CONCAT(/*searchDto.key*/'dai', '%' ))
)
/*END*/
/*IF searchDto.type != null */
AND lang_type.M_NEWS_TYPE_ID = /*searchDto.type*/'100002'
/*END*/

SELECT NEWS.TOTAL AS TOTAL
FROM #NEWS_TYPE_LANGUAGE NEWS