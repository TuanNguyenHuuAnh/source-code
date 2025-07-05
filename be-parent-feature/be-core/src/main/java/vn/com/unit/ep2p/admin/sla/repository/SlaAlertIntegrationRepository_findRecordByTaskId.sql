SELECT *
from INTEG_SLA_ALERT
WHERE 
	DELETED_BY is null 
	AND ROWNUM = 1 
	/*IF taskId != null*/
	AND TASK_ID = /*taskId*/0 
	/*END*/ 
	ORDER BY ID asc 