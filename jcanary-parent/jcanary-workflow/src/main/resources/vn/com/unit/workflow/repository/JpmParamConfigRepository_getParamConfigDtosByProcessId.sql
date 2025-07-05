SELECT
	paramConfig.PROCESS_ID    AS PROCESS_ID
	, paramConfig.PARAM_ID      AS PARAM_ID
	, paramConfig.STEP_ID       AS STEP_ID
	, paramConfig.REQUIRED      AS REQUIRED
FROM
	JPM_PARAM_CONFIG paramConfig
WHERE
	paramConfig.PROCESS_ID = /*processId*/