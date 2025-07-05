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
	/*IF branchDto.id != null && branchDto.id != ''*/
	AND id = /*branchDto.id*/
	/*END*/	
	/*IF branchDto.code != null && branchDto.code != ''*/
	AND code = /*branchDto.code*/
	/*END*/	
	/*IF branchDto.type != null && branchDto.type != ''*/
	AND type = /*branchDto.type*/
	/*END*/
	/*IF branchDto.city != null && branchDto.city != ''*/
	AND city LIKE concat('%', /*branchDto.city*/ , '%')
	/*END*/
	/*IF branchDto.district != null && branchDto.district != ''*/
	AND district LIKE concat('%', /*branchDto.district*/ , '%')
	/*END*/
	/*IF branchDto.address != null && branchDto.address != ''*/
	AND address LIKE concat('%', /*branchDto.address*/ , '%')
	/*END*/
	/*IF branchDto.latitude != null && branchDto.latitude != ''*/
	AND latitude LIKE concat('%', /*branchDto.latitude*/ , '%')
	/*END*/
	/*IF branchDto.longtitude != null && branchDto.longtitude != ''*/
	AND longtitude LIKE concat('%', /*branchDto.longtitude*/ , '%')
	/*END*/
	
ORDER BY create_date DESC