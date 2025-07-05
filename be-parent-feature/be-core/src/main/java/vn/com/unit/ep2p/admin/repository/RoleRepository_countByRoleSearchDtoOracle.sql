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
		/*IF !roleSearchDto.companyAdmin*/
		AND (company_id is null or company_id = /*roleSearchDto.companyId*/1)
		/*END*/
		/*BEGIN*/
		AND(
			/*IF roleSearchDto.code != null && roleSearchDto.code != ''*/
			OR replace(code,' ','') LIKE CONCAT( '%', CONCAT(/*roleSearchDto.code*/ , '%') )
			/*END*/
			
			/*IF roleSearchDto.name != null && roleSearchDto.name != ''*/
			OR replace(name,' ','') LIKE CONCAT( '%', CONCAT(/*roleSearchDto.name*/ , '%') )
			/*END*/
			
		    /*IF roleSearchDto.description != null && roleSearchDto.description != ''*/
	        OR replace(description,' ','') LIKE CONCAT( '%', CONCAT(/*roleSearchDto.description*/ , '%') )
	        /*END*/
        )
        /*END*/
)  TBL