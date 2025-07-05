SELECT DISTINCT	
		con.CODE                      AS CODE
		,con.GROUP_CODE                AS GROUP_CODE
		,con.DISPLAY_ORDER             AS DISPLAY_ORDER
		,con.LANG_CODE                AS LANG_CODE

FROM JCA_CONSTANT con
LEFT JOIN JCA_GROUP_CONSTANT grpc
	ON con.GROUP_CODE = grpc.CODE

WHERE 
	con.ACTIVED = 1
	/*BEGIN*/
	AND
	(
		/*IF jcaGroupConstantSearchDto.code != null && jcaGroupConstantSearchDto.code != ''*/
	    OR UPPER(grpc.CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaGroupConstantSearchDto.code*/''), '%' ))
	    /*END*/
	 
    )
    /*END*/