SELECT 
  ACCOUNT_ID
FROM
  JPM_TASK_ASSIGNEE taskAssign
WHERE
	taskAssign.TASK_ID = /*taskId*/''
	/*IF type == -1*/
	AND taskAssign.ASSIGNEE_FLAG = 1
	/*END*/
	/*IF type == -2*/
	AND taskAssign.SUBMITTED_FLAG = 1
	/*END*/