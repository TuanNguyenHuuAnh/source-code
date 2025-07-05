SELECT
	m_category.id AS id
	,m_category.code AS code
	,m_parent_category.code AS parent_code
FROM m_document_category m_category LEFT JOIN m_document_category m_parent_category
										ON m_category.parent_id = m_parent_category.id
AND m_category.delete_by IS NULL
										