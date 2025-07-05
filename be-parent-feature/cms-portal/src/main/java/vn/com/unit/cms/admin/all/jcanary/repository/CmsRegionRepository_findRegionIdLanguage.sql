SELECT
	*	
FROM
	jca_m_region_language 			    
WHERE 
	delete_by IS NULL
	/*IF regionId != null */
	AND m_region_id =/*regionId*/
	/*END*/
	ORDER BY create_date DESC