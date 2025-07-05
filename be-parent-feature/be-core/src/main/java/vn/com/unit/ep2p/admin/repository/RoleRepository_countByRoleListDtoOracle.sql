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
		/*BEGIN*/
		AND(
			/*IF roleSearchDto.code != null && roleSearchDto.code != ''*/
			OR replace(UPPER(code),' ','') LIKE ( '%' || UPPER(TRIM(/*roleSearchDto.code*/)) || '%' )
			/*END*/
			
			/*IF roleSearchDto.name != null && roleSearchDto.name != ''*/
			OR replace(UPPER(name),' ','') LIKE ( '%' || UPPER(TRIM(/*roleSearchDto.name*/)) || '%' )
			/*END*/
			
		    /*IF roleSearchDto.description != null && roleSearchDto.description != ''*/
	        OR replace(UPPER(description),' ','') LIKE ( '%' || UPPER(TRIM(/*roleSearchDto.description*/)) || '%' )
	        /*END*/
        )
        /*END*/
)  TBL