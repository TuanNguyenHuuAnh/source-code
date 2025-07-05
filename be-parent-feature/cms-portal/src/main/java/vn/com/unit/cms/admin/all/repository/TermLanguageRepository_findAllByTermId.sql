SELECT
	id,
	m_term_id,
	m_language_code,
	title
FROM
	 m_term_language
WHERE
	m_term_id = /*termId*/
	AND
	delete_date is NULL