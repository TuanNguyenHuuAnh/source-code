SELECT setting.* FROM SLA_SETTING setting 
LEFT JOIN SLA_STEP step ON step.ID = setting.SLA_STEP_ID 
WHERE setting.DELETED_BY IS NULL AND step.DELETED_BY IS NULL AND setting.SLA_STEP_ID = /*stepId*/0 AND step.PROCESS_ID = /*oldProcessDeployId*/0 
ORDER BY setting.ID asc