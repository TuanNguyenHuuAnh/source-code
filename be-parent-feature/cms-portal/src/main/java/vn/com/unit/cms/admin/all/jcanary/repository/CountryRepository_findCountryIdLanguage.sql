SELECT
	*
FROM
	jca_m_country_language 			    
WHERE 
	delete_by IS NULL
	/*IF countryId != null */
	AND m_country_id =/*countryId*/
	/*END*/
	order by create_date DESC