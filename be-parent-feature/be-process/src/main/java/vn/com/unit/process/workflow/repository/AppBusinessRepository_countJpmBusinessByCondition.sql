SELECT 
	COUNT(*)
FROM 
	JPM_BUSINESS
WHERE
	DELETED_ID = 0
	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND ( COMPANY_ID = /*searchDto.companyId*/ OR COMPANY_ID IS NULL )
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND ( COMPANY_ID  IN /*searchDto.companyIdList*/() OR COMPANY_ID IS NULL )
	/*END*/
	/*BEGIN*/
	AND (
	    /*IF searchDto.code != null && searchDto.code != ''*/
		UPPER(CODE) LIKE CONCAT(CONCAT('%', UPPER(/*searchDto.code*/'')), '%')
		/*END*/
	
		/*IF searchDto.name != null && searchDto.name != ''*/
		OR UPPER(NAME) LIKE CONCAT(CONCAT('%', UPPER(/*searchDto.name*/'')), '%')
		/*END*/
	)
	/*END*/
