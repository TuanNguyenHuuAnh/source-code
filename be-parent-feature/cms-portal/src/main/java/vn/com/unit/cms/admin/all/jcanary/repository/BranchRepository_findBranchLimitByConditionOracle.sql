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
	OR UPPER(code) LIKE ('%'|| UPPER(/*branchSearchDto.code*/) ||'%')
	/*END*/
	/*IF branchSearchDto.name != null && branchSearchDto.name != ''*/
	OR UPPER(name) LIKE ('%'|| UPPER(/*branchSearchDto.name*/) ||'%')
	/*END*/
	/*IF branchSearchDto.address != null && branchSearchDto.address != ''*/
	OR UPPER(address) LIKE ('%'|| UPPER(/*branchSearchDto.address*/) ||'%')
	/*END*/
	/*IF branchSearchDto.phone != null && branchSearchDto.phone != ''*/
	OR UPPER(phone) LIKE ('%'|| UPPER(/*branchSearchDto.phone*/) ||'%')
	/*END*/
	)
	/*END*/
	ORDER BY create_date DESC

	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY