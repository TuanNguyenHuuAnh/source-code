SELECT
	  categ.id			AS id
	, categ.code		AS code
	, categLang.label	AS label
FROM 
	m_news_category categ
	LEFT JOIN m_news_category_language categLang ON (categLang.m_news_category_id = categ.id AND categLang.delete_date is null)
WHERE categ.delete_date is null
	and categ.ENABLED = 1
	AND UPPER(categLang.m_language_code) = UPPER(/*lang*/)
	AND categ.M_CUSTOMER_TYPE_ID = /*customerId*/
	/*IF typeId != null && typeId != -1*/
	AND categ.m_news_type_id = /*typeId*/
	/*END*/