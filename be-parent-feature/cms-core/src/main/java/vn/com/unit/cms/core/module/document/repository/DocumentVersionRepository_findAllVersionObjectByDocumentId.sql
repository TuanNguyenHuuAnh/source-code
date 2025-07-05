SELECT
	id AS id,
    file_name 								AS file_name,
    version_current 						AS current_version,
    version 								AS version
FROM
    m_document_detail
WHERE delete_by is NULL
	AND m_document_id = /*documentId*/
ORDER BY version_current DESC, version DESC
