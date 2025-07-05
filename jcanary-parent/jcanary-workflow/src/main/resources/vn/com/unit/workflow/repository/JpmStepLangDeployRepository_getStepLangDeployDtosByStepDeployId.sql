SELECT 
	stepLang.LANG_ID            	  AS LANG_ID
	, stepLang.LANG_CODE      AS LANG_CODE
	, stepLang.STEP_NAME      AS STEP_NAME
	, stepLang.STEP_DEPLOY_ID AS STEP_DEPLOY_ID
FROM
	JPM_STEP_LANG_DEPLOY stepLang
WHERE
	stepLang.STEP_DEPLOY_ID = /*stepDeployId*/