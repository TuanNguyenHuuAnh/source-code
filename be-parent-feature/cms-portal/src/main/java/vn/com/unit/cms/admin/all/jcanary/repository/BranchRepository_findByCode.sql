SELECT
	  id,
     code,
     name,
	 note,
	 address,
	 latitude,
	 longtitude,
	 is_primary,
	 type,
	 icon,
	 phone,
	 fax,
	 district,
	 city,	 
	 create_date, 
	 email
FROM
	jca_m_branch 		
WHERE 
	delete_by IS NULL
	/*IF code != null && code != ''*/
	AND code = /*code*/
	/*END*/	
	ORDER BY create_date DESC
	

	
	
