SELECT
	termLang.*
FROM
	m_term term
	JOIN m_term_language termLang ON(termLang.m_term_id = term.id AND termLang.delete_date is NULL)
WHERE
	UPPER(termLang.m_language_code) = UPPER(/*languageCode*/)
	AND term.delete_date is NULL
	AND term.code = /*termCode*/