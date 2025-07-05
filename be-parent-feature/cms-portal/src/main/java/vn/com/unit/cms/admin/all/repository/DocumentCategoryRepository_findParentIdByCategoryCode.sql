SELECT
	m_document_category.parent_id   
FROM
	m_document_category
WHERE 
	delete_by is NULL
	/*BEGIN*/
	AND(
		/*IF categoryCode != null && categoryCode != ''*/
	 	OR m_document_category.code = /*categoryCode*/
		/*END*/
	)
	/*END*/ 
ORDER BY m_document_category.create_date DESC