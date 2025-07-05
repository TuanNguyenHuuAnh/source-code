SELECT 
	count(*)
FROM 
(
	SELECT 
	    *
	FROM
	    JCA_ROLE
	WHERE
		DELETED_ID = 0
		/*IF roleSearchDto.companyId != null && roleSearchDto.companyId != 0*/
		AND COMPANY_ID = /*roleSearchDto.companyId*/
		/*END*/
		/*IF roleSearchDto.companyId == null*/
		AND COMPANY_ID IS NULL
		/*END*/
		/*IF roleSearchDto.companyId == 0 && !roleSearchDto.companyAdmin*/
		AND (COMPANY_ID  IN /*roleSearchDto.companyIdList*/()
		OR COMPANY_ID IS NULL)
		/*END*/
		/*BEGIN*/
		AND(
			/*IF roleSearchDto.code != null && roleSearchDto.code != ''*/
			OR UPPER(code) LIKE CONCAT( '%', CONCAT(UPPER(/*roleSearchDto.code*/), '%') )
			/*END*/
			
			/*IF roleSearchDto.name != null && roleSearchDto.name != ''*/
			OR UPPER(name) LIKE CONCAT( '%', CONCAT(UPPER(/*roleSearchDto.name*/), '%') )
			/*END*/
			
		    /*IF roleSearchDto.description != null && roleSearchDto.description != ''*/
	        OR UPPER(description) LIKE CONCAT( '%', CONCAT(UPPER(/*roleSearchDto.description*/), '%') )
	        /*END*/
        )
        /*END*/
)  TBL