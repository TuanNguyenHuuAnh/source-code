SELECT TOP 1
	*
FROM m_document_detail WITH (NOLOCK)
WHERE
	delete_by is NULL
	AND m_document_id = /*documentId*/5
	/*IF version != null*/
	AND VERSION = /*version*/1
	/*END*/
ORDER BY VERSION DESC