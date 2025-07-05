SELECT
	*	
FROM
	jca_m_city_language 			    
WHERE 
	delete_date IS NULL 	
    AND m_language_code = UPPER(/*languageCode*/)