SELECT 
	stepLang.LANG_ID            	  AS LANG_ID
	, stepLang.LANG_CODE      AS LANG_CODE
	, stepLang.STEP_NAME      AS STEP_NAME
	, stepLang.STEP_ID        AS STEP_ID
FROM
	JPM_STEP step
LEFT JOIN
	JPM_STEP_LANG stepLang
ON
	step.ID = stepLang.STEP_ID
WHERE
	step.DELETED_ID = 0
  	AND stepLang.STEP_NAME IS NOT NULL
	AND step.PROCESS_ID = /*processId*/