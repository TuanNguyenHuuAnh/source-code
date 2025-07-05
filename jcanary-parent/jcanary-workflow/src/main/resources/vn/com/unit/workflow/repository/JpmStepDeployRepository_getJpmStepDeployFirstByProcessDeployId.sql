SELECT 
	step.ID		                   AS ID
	, step.STEP_NO                 AS STEP_NO
	, step.STEP_CODE               AS STEP_CODE
	, step.STEP_NAME               AS STEP_NAME
	, step.STATUS_DEPLOY_ID        AS STATUS_DEPLOY_ID
	, step.PROCESS_DEPLOY_ID       AS PROCESS_DEPLOY_ID
	, step.DESCRIPTION             AS DESCRIPTION
	, step.STEP_ID                 AS STEP_ID
	, step.STEP_TYPE               AS STEP_TYPE
	, step.STEP_KIND               AS STEP_KIND
	, step.COMMON_STATUS_CODE      AS COMMON_STATUS_CODE
	, step.COMMON_STATUS_ID        AS COMMON_STATUS_ID
	, step.USE_CLAIM_BUTTON        AS USE_CLAIM_BUTTON
FROM 
	JPM_STEP_DEPLOY step
WHERE
	step.DELETED_ID = 0
	AND step.PROCESS_DEPLOY_ID = /*processDeployId*/0
	AND ROWNUM = 1
ORDER BY
	step.STEP_NO









