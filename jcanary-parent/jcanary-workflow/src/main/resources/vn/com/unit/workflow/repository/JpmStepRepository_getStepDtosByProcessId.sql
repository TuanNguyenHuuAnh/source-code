SELECT
	step.ID                       	AS STEP_ID
	, step.STEP_NO                  AS STEP_NO
	, step.STEP_CODE                AS STEP_CODE
	, step.STEP_NAME                AS STEP_NAME
	, step.STATUS_ID                AS STATUS_ID
	, step.PROCESS_ID               AS PROCESS_ID
	, step.DESCRIPTION              AS DESCRIPTION
	, step.STEP_TYPE                AS STEP_TYPE
	, step.STEP_KIND                AS STEP_KIND
	, step.COMMON_STATUS_CODE      AS COMMON_STATUS_CODE
	, step.COMMON_STATUS_ID        AS COMMON_STATUS_ID
	, step.USE_CLAIM_BUTTON         AS USE_CLAIM_BUTTON
FROM
	JPM_STEP step
WHERE
	step.DELETED_ID = 0
	AND step.PROCESS_ID = /*processId*/