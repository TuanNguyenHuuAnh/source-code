SELECT
	m_document_category.parent_id   
FROM
	m_document_category
WHERE 
	delete_by is NULL
	/*BEGIN*/
	AND(
		/*IF categoryId != null && categoryId != ''*/
	 	OR m_document_category.id = /*categoryId*/
		/*END*/
	)
	/*END*/ 