SELECT
	m_document_category.id,
	m_document_category.code,
	m_document_category.parent_id,
	m_document_category.sort_order,
	m_document_category.create_date,
	m_document_category.m_document_type_id as document_type_id,
	m_document_category.m_customer_type_id as str_customer_type_id,
	m_parent_category.name as parent_name
FROM
	m_document_category LEFT JOIN m_document_category m_parent_category ON (m_document_category.parent_id = m_parent_category.id)
WHERE 
	m_document_category.delete_date is NULL
	/*BEGIN*/
	AND(
		/*IF categoryId != null && categoryId != ''*/
	 	OR m_document_category.id = /*categoryId*/
		/*END*/
	)
	/*END*/ 
ORDER BY m_document_category.sort_order ASC