SELECT *
FROM m_news_type_language
WHERE delete_date is null
	AND m_news_type_id = /*typeId*/
	AND UPPER(m_language_code) = UPPER(/*languageCode*/)