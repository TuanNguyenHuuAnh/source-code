SELECT
	typeLang.LABEL, ntype.id
FROM m_news_type ntype
	JOIN m_news_type_language typeLang ON (ntype.id = typeLang.m_news_type_id AND typeLang.delete_date is null)
	LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = ntype.m_customer_type_id 
											AND customerType.m_language_code = typeLang.m_language_code
											AND customerType.delete_date is null)
WHERE
	ntype.delete_date is null	
	AND UPPER(typeLang.m_language_code) = UPPER('VI')
	AND (typeLang.M_NEWS_TYPE_ID IN /*typeIds*/())