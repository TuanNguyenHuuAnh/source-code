SELECT
	m_document_category.code 
FROM
	m_document_category
WHERE 
	delete_date is NULL
	/*BEGIN*/
	AND(
		/*IF categoryId != null && categoryId != ''*/
	 	OR m_document_category.id = /*categoryId*/
		/*END*/
	)
	/*END*/ 