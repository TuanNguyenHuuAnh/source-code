SELECT 
  	 /*paramId*/		AS PARAM_ID
  	, step.ID 			AS STEP_ID
 	, step.STEP_NAME 	AS STEP_NAME
 	, step.PROCESS_ID 	AS PROCESS_ID
  	, case 
  	WHEN /*paramId*/ is null THEN 1
  	when config.REQUIRED IS NULL THEN 0 
  	else config.REQUIRED END AS REQUIRED
FROM
	JPM_STEP step
/*IF null != paramId*/
LEFT JOIN 
	JPM_PARAM param
ON
	param.PROCESS_ID = step.PROCESS_ID
	AND param.DELETED_ID = 0
/*END*/
LEFT JOIN 
	JPM_PARAM_CONFIG config
ON 
	step.ID = config.STEP_ID
	AND config.PARAM_ID = /*paramId*/100
WHERE
	step.DELETED_ID = 0
	/*IF null != paramId*/
	AND param.ID = /*paramId*/100
	/*END*/
	AND step.PROCESS_ID = /*processId*/100
ORDER BY step.STEP_NO