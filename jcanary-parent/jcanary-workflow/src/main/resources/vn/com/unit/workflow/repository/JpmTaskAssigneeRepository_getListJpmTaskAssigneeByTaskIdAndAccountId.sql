SELECT
	*
FROM
	JPM_TASK_ASSIGNEE	taskAssign
WHERE
	taskAssign.TASK_ID = /*taskId*/
	AND taskAssign.STEP_DEPLOY_ID = /*stepDeployId*/
	AND taskAssign.PROCESS_DEPLOY_ID = /*processDeployId*/
	AND taskAssign.ACCOUNT_ID = /*accountId*/
	