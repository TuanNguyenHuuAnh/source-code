SELECT
	*	
FROM
	jca_m_district_language 			    
WHERE 
	delete_by IS NULL
	/*IF districtId != null */
	AND m_district_id =/*districtId*/
	/*END*/