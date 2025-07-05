SELECT
    *
FROM
	m_faqs_type typeCate
WHERE
	typeCate.delete_date is null
	
	AND typeCate.ENABLED = 1
	
	AND typeCate.code = /*code*/