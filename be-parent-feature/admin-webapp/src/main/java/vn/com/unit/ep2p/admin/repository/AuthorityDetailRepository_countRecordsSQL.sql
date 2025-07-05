SELECT 
	COUNT(*) 
FROM 
	VW_AUTHORITY_DETAIL
WHERE 0 = 0
/*BEGIN*/
	AND (
		/*IF authorityDetailDto.username != null*/
			OR UPPER(USERNAME) LIKE CONCAT('%',UPPER( /*authorityDetailDto.username*/ ),'%')
		/*END*/
		/*IF authorityDetailDto.fullname != null*/
			OR UPPER(FULLNAME) LIKE CONCAT('%',UPPER( /*authorityDetailDto.fullname*/ ),'%')
		/*END*/
		/*IF authorityDetailDto.email != null*/
			OR UPPER(EMAIL) LIKE CONCAT('%',UPPER( /*authorityDetailDto.email*/ ),'%')
		/*END*/
		/*IF authorityDetailDto.groupCode != null*/
			OR UPPER(GROUP_CODE) LIKE CONCAT('%',UPPER( /*authorityDetailDto.groupCode*/ ),'%')
			OR UPPER(GROUP_NAME) LIKE CONCAT('%',UPPER( /*authorityDetailDto.groupCode*/ ),'%')
		/*END*/
		/*IF authorityDetailDto.roleCode != null*/
			OR UPPER(ROLE_CODE) LIKE CONCAT('%',UPPER( /*authorityDetailDto.roleCode*/ ),'%')
			OR UPPER(ROLE_NAME) LIKE CONCAT('%',UPPER( /*authorityDetailDto.roleCode*/ ),'%')
		/*END*/
		/*IF authorityDetailDto.functionCode != null*/
			OR UPPER(FUNCTION_CODE) LIKE CONCAT('%',UPPER( /*authorityDetailDto.functionCode*/ ),'%')
			OR UPPER(FUNCTION_NAME) LIKE CONCAT('%',UPPER( /*authorityDetailDto.functionCode*/ ),'%')
		/*END*/
	)
/*END*/
	
/*BEGIN*/
	AND (
		/*IF authorityDetailDto.companyId != null*/
			OR COMPANY_ID = /*authorityDetailDto.companyId*/1
		/*END*/
	)
/*END*/
	