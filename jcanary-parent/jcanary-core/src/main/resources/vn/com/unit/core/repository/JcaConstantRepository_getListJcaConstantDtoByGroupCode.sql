SELECT DISTINCT	con.ID				   AS ID
		,con.CODE                      AS CODE
		,con.GROUP_CODE                AS GROUP_CODE
		,con.DISPLAY_ORDER             AS DISPLAY_ORDER
		,con.LANG_CODE                AS LANG_CODE
FROM JCA_CONSTANT con
LEFT JOIN JCA_GROUP_CONSTANT grpc
	ON con.GROUP_CODE = grpc.CODE

WHERE 
	con.ACTIVED = 1
	AND con.GROUP_CODE = /*groupCode*/'1'
