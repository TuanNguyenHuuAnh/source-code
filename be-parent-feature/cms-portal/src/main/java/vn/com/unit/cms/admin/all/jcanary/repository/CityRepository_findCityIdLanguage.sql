SELECT
	*	
FROM
	jca_m_city_language 			    
WHERE 
	delete_by IS NULL
	/*IF cityId != null */
	AND m_city_id =/*cityId*/
	/*END*/
	ORDER BY create_date DESC