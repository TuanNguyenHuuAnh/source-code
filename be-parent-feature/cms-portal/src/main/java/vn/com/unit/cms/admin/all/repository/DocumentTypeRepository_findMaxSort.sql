SELECT
	 max(sort_order)
FROM m_document_type
WHERE
	delete_date is null
