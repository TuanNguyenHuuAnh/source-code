SELECT
      term.id 			AS id
    , term.code			AS code
    , term.term_value	AS term_value
	, term.term_type	AS term_type		
    , termLang.title  	AS title
FROM
	m_term term
	LEFT JOIN m_term_language termLang ON (termLang.m_term_id = term.id AND termLang.delete_date IS NULL)
	
WHERE
	term.delete_date IS NULL
	AND UPPER(termLang.m_language_code) = UPPER(/*languageCode*/) 
ORDER BY term.create_date