SELECT
    version
FROM
    m_document_detail
WHERE delete_by is NULL
	AND m_document_id = /*documentId*/
	AND version_current = 1
