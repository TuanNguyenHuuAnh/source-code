UPDATE
    m_document_detail
    SET share_token_hash = /*shareToken*/,
    	share_token_timestamp = /*timeStamp*/
WHERE delete_by is NULL
	AND m_document_id = /*documentId*/
	AND id = /*versionId*/
