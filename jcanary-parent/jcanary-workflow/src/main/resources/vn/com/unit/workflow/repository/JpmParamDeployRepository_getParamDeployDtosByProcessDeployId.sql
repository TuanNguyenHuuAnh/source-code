SELECT
	param.ID                     AS PARAM_DEPLOY_ID
	, param.PROCESS_DEPLOY_ID    AS PROCESS_DEPLOY_ID
	, param.FIELD_NAME           AS FIELD_NAME
	, param.DATA_TYPE            AS DATA_TYPE
	, param.FORM_FIELD_NAME      AS FORM_FIELD_NAME
FROM
	JPM_PARAM_DEPLOY param
WHERE
	param.DELETED_ID = 0
	AND param.PROCESS_DEPLOY_ID = /*processDeployId*/