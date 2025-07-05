SELECT	grpc.ID							AS ID
		,grpc.CODE                      AS CODE
		,grpc.DISPLAY_ORDER             AS DISPLAY_ORDER
		,grpc.COMPANY_ID                AS COMPANY_ID

		,grpcl.ID                     	AS LANGUAGE_ID
FROM JCA_GROUP_CONSTANT grpc

WHERE 
	grpc.DELETED_ID = 0
	AND grpc.ID = /*id*/1