SELECT	grpc.ID							AS ID
		,grpc.CODE                      AS CODE
		,grpc.DISPLAY_ORDER             AS DISPLAY_ORDER
		,grpc.COMPANY_ID                AS COMPANY_ID
FROM JCA_GROUP_CONSTANT grpc

WHERE 
	grpc.DELETED_ID = 0
	/*IF jcaGroupConstantSearchDto.companyId != null && jcaGroupConstantSearchDto.companyId != 0*/
	AND grpc.COMPANY_ID = /*jcaGroupConstantSearchDto.companyId*/1
	/*END*/
	
	/*BEGIN*/
	AND
	(
		/*IF jcaGroupConstantSearchDto.code != null && jcaGroupConstantSearchDto.code != ''*/
	    OR UPPER(grpc.CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaGroupConstantSearchDto.code*/''), '%' ))
	    /*END*/
	    

    )
    /*END*/
    

