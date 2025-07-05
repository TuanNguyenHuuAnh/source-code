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
	/*BEGIN*/
	AND (
    /*IF branchSearchDto.code != null && branchSearchDto.code != ''*/
	OR code LIKE concat('%',  /*branchSearchDto.code*/, '%')
	/*END*/
	/*IF branchSearchDto.name != null && branchSearchDto.name != ''*/
	OR name LIKE concat('%',  /*branchSearchDto.name*/, '%')
	/*END*/
	/*IF branchSearchDto.address != null && branchSearchDto.address != ''*/
	OR address LIKE concat('%',  /*branchSearchDto.address*/, '%')
	/*END*/
	/*IF branchSearchDto.phone != null && branchSearchDto.phone != ''*/
	OR phone LIKE concat('%',  /*branchSearchDto.phone*/, '%')
	/*END*/
	)
	/*END*/
	ORDER BY create_date DESC
	
	LIMIT  /*sizeOfPage*/ OFFSET  /*offset*/ 