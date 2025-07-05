SELECT
     jpmAuthority.PROCESS_DEPLOY_ID
    ,jpmAuthority.PERMISSION_DEPLOY_ID
    ,jpmAuthority.ROLE_ID
FROM
	JPM_AUTHORITY jpmAuthority
WHERE
	jpmAuthority.PERMISSION_DEPLOY_ID IN /*permissionIds*/()