select TOP 4 
	  news.id					AS	id
  	, news.code					AS	code
	, news.M_NEWS_CATEGORY_ID	AS NEWS_CATEGORY_ID
  	, news.sub_title			AS	sub_title
	, lang.LINK_ALIAS			AS LINK_ALIAS
  	, lang.IMG_URL			AS	image_url
	, lang.PHYSICAL_IMG_URL	AS PHYSICAL_IMG_URL
  	, lang.key_word				AS	key_word
  	, news.enabled				AS	enabled
  	, lang.title				AS	title
  	, lang.short_content		AS	short_content
  	, lang.CONTENT 			AS CONTENT
  	, news.create_date			AS  create_date
  	, news.create_by			AS  create_by
	, news.POSTED_DATE			as POSTED_DATE
	, news.EXPIRATION_DATE		as EXPIRATION_DATE
	, news.HOT					as HOT

from M_NEWS_LANGUAGE lang
INNER JOIN M_NEWS NEWS
ON(NEWS.ID = lang.M_NEWS_ID)
INNER JOIN M_NEWS_CATEGORY CATE
ON(NEWS.M_NEWS_CATEGORY_ID = CATE.ID)
INNER JOIN M_NEWS_TYPE TYPE_
ON(CATE.M_NEWS_TYPE_ID = TYPE_.ID)
WHERE TYPE_.CODE =/*typeCode*/'CAREER'
AND CATE.CATEGORY_TYPE = 'CAT21.000001'
AND	NEWS.delete_date is null
	AND NEWS.POSTED_DATE <= GETDATE()
	AND ISNULL(news.EXPIRATION_DATE, DATEADD(day, 1, GETDATE())) >= GETDATE()
AND lang.M_LANGUAGE_CODE = UPPER(/*language*/'VI')
/*IF modeView == 0*/
	AND NEWS.ENABLED = 1
/*END*/
AND NEWS.HOMEPAGE = 1
ORDER BY news.POSTED_DATE DESC, news.update_date DESC, news.sort ASC, news.ENABLED DESC