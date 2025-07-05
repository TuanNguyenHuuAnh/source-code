SELECT
	stepDeploy.ID                   AS STEP_DEPLOY_ID
	, stepDeploy.STEP_NO            AS STEP_NO
	, stepDeploy.STEP_CODE          AS STEP_CODE
	, stepDeploy.STEP_NAME			AS STEP_NAME
	, stepDeploy.STATUS_DEPLOY_ID   AS STATUS_DEPLOY_ID
	, stepDeploy.PROCESS_DEPLOY_ID  AS PROCESS_DEPLOY_ID
	, stepDeploy.DESCRIPTION        AS DESCRIPTION
	, stepDeploy.STEP_ID            AS STEP_ID
	, stepDeploy.STEP_TYPE          AS STEP_TYPE
	, stepDeploy.STEP_KIND          AS STEP_KIND
	, stepDeploy.COMMON_STATUS_CODE AS COMMON_STATUS_CODE
	, stepDeploy.COMMON_STATUS_ID 	AS COMMON_STATUS_ID
	, stepDeploy.USE_CLAIM_BUTTON   AS USE_CLAIM_BUTTON
	, statusDeploy.STATUS_CODE      AS PROCESS_STATUS_CODE
	, statusDeploy.STATUS_CODE      AS STATUS_CODE
	, statusDeploy.ID        		AS PROCESS_STATUS_ID
FROM
	JPM_STEP_DEPLOY stepDeploy
LEFT JOIN
	JPM_STATUS_DEPLOY statusDeploy
ON
	stepDeploy.STATUS_DEPLOY_ID = statusDeploy.ID
WHERE 
    stepDeploy.DELETED_ID = 0
	AND stepDeploy.PROCESS_DEPLOY_ID = /*processDeployId*/
	AND stepDeploy.STEP_CODE = /*stepcode*/