UPDATE
	JPM_TASK task
SET 
task.PLAN_START_DATE = /*startDate*/
,task.PLAN_DUE_DATE = /*dueDate*/
,task.PLAN_ESTIMATE_TIME = /*estimateTime*/
,task.PLAN_CALANDAR_TYPE = /*callandarType*/
,task.PLAN_ESTIMATE_UNIT_TIME = /*estimateUnitTime*/
,task.PLAN_TOTAL_TIME = /*totalTime*/
,task.SLA_CONFIG_ID = /*slaConfigId*/
WHERE
	task.ID = /*jpmTaskId*/