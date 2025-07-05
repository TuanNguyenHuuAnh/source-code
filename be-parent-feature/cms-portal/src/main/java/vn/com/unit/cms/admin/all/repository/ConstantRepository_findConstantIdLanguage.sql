SELECT
	*	
FROM
	m_constant_language 			    
WHERE 
	delete_by IS NULL
	/*IF constantCode != null && constantCode != ''*/
	AND m_constant_code =/*constantCode*/
	/*END*/
	ORDER BY create_date DESC