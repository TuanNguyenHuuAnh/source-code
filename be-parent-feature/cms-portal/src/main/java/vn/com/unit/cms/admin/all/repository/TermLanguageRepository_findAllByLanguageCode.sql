SELECT
	id,
	m_term_id,
	m_language_code,
	title
FROM
	 m_term_language
WHERE
	UPPER(m_language_code) = UPPER(/*languageCode*/)
	AND
	delete_date is NULL