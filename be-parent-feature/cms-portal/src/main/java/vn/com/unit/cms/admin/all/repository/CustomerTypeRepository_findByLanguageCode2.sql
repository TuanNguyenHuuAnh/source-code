SELECT
    typeCate.id 		AS id,
    typeCate.code		AS code,
    typeCateLang.title  AS title
FROM
	m_customer_type typeCate
	LEFT JOIN m_customer_type_language typeCateLang ON (typeCateLang.m_customer_type_id = typeCate.id AND typeCateLang.delete_date IS NULL)
	
WHERE
	typeCate.delete_date IS NULL
	AND UPPER(typeCateLang.m_language_code) = UPPER(/*languageCode*/'vi')
	AND typeCate.id = /*customerTypeId*/9
	AND enabled = 1
ORDER BY typeCate.create_date
