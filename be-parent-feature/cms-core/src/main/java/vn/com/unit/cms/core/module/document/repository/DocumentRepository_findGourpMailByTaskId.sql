
  SELECT 
  taskAssign.ACCOUNT_ID as ACCOUNT_ID,
  account.USERNAME AS USERNAME,
  account.EMAIL AS EMAIL

FROM
  JPM_TASK_ASSIGNEE taskAssign
  INNER JOIN JCA_ACCOUNT account
  ON(taskAssign.ACCOUNT_ID = account.ID)
WHERE
	taskAssign.TASK_ID = /*jpmTaskId*/''
	AND taskAssign.ASSIGNEE_FLAG = 1
	AND taskAssign.SUBMITTED_FLAG = 0