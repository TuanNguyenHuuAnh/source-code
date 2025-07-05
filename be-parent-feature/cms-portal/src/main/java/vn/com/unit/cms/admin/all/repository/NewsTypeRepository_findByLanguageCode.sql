SELECT
    typeCate.id 		AS id,
    typeCate.code		AS code,
    typeCateLang.label  AS label
FROM
	m_news_type typeCate
	LEFT JOIN m_news_type_language typeCateLang ON (typeCateLang.m_news_type_id = typeCate.id AND typeCateLang.delete_date IS NULL)
	
WHERE
	typeCate.delete_date IS NULL
	AND typeCate.ENABLED = 1
	AND UPPER(typeCateLang.m_language_code) = UPPER(/*languageCode*/) 
ORDER BY typeCate.create_date
