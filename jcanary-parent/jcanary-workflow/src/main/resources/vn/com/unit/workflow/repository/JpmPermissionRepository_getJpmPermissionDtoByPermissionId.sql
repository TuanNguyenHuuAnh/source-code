SELECT
	permission.ID                  	 AS PERMISSION_ID
	, permission.PROCESS_ID          AS PROCESS_ID
	, permission.PERMISSION_CODE     AS PERMISSION_CODE
	, permission.PERMISSION_NAME     AS PERMISSION_NAME
	, permission.PERMISSION_TYPE     AS PERMISSION_TYPE
FROM
	JPM_PERMISSION permission
WHERE
	permission.DELETED_ID = 0
	AND permission.ID = /*processPermissionId*/