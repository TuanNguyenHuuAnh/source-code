UPDATE
    m_document_detail
    SET version_current = 0
WHERE delete_by is NULL
	AND m_document_id = /*documentId*/
