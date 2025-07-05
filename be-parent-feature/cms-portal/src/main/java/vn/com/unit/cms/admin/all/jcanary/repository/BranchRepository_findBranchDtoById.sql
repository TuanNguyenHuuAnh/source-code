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
	 working_hours,
	 email,
	 active_flag
FROM
	jca_m_branch	
WHERE 
	delete_by IS NULL
	/*IF branchId != null*/
	AND id = /*branchId*/
	/*END*/
ORDER BY create_date DESC
	
	
