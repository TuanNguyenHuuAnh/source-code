SELECT
    typeCate.id 		AS id,
    typeCate.code		AS code,
    typeCateLang.title  AS title,
    typeCate.link_alias AS link_alias
FROM
	m_faqs_type typeCate
	LEFT JOIN m_faqs_type_language typeCateLang 
	ON (typeCateLang.m_faqs_type_id = typeCate.id and typeCateLang.m_language_code = UPPER(/*lang*/) and typeCateLang.delete_date is null)
	
WHERE
	typeCate.delete_date is null
	AND typeCate.ENABLED = 1
ORDER BY typeCate.create_date
