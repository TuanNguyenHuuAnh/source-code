SELECT
	  *
FROM m_document_type
WHERE
	delete_date is null
ORDER BY sort_order ASC, create_date DESC
