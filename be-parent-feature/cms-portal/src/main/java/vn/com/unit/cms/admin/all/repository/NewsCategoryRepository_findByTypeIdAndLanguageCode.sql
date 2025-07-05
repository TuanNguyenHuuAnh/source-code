SELECT
	  categ.id			AS id
	, categ.code		AS code
	, categLang.label	AS label
FROM 
	m_news_category categ
INNER JOIN m_news_category_language categLang 
	ON categLang.m_news_category_id = categ.id 
	AND categLang.delete_date is null
WHERE 
	categ.delete_date is null
	AND categ.ENABLED = 1
	AND categ.m_news_type_id = /*typeId*/