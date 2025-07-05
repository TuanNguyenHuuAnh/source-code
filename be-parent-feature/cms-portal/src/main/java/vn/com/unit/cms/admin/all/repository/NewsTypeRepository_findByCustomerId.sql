SELECT
      typeCate.id 			AS id
    , typeCate.code			AS code
    , typeCateLang.label  	AS label
FROM
	m_news_type typeCate
JOIN m_news_type_language typeCateLang ON (typeCateLang.m_news_type_id = typeCate.id AND typeCateLang.delete_date IS NULL)
WHERE
	typeCate.delete_date IS NULL
	and typeCate.ENABLED = 1
	AND UPPER(typeCateLang.m_language_code) = UPPER(/*languageCode*/)
	/*IF customerId != null*/
	AND typeCate.m_customer_type_id = /*customerId*/
	/*END*/
	and typeCate.CHANNEL is null
ORDER BY label ASC, sort ASC
