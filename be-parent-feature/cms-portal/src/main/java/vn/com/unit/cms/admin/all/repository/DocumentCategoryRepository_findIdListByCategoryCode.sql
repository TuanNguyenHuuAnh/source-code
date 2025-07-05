SELECT
	m_document_category.id
FROM
	m_document_category
WHERE 
	delete_date is NULL
	/*BEGIN*/
	AND(
		/*IF categoryCode != null && categoryCode != ''*/
	 	OR m_document_category.code LIKE CONCAT('%',/*categoryCode*/,'%')
		/*END*/
	)
	/*END*/ 
ORDER BY m_document_category.create_date ASC