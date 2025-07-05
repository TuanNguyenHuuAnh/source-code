SELECT
	stepDeployLang.STATUS_NAME         AS STATUS_NAME,	-- curent_step
    task.ACTION_ID                     AS ACTION_ID,
    CONVERT(VARCHAR(10), task.ACTUAL_END_DATE, 103) + ' ' + CONVERT(VARCHAR(8), task.ACTUAL_END_DATE, 108)                AS ACTION_TIME,
    step.STEP_NAME                     AS STEP_NAME,	-- prev_step
    task.NOTE                          AS NOTE,
    button.BUTTON_NAME_IN_PASSIVE      AS ACTION_NAME,	-- button name
	button.BUTTON_NAME                 AS BUTTON_NAME,
    task.CORE_task_ID                  AS ACT_task_ID,
    task.STEP_DEPLOY_CODE              AS STEP_DEPLOY_CODE,
    task.SUBMITTED_ID                  AS SUBMITTED_ID,
    task.SUBMITTED_DATE                AS SUBMITTED_DATE,
    task.PLAN_DUE_DATE                 AS PLAN_DUE_DATE,
    task.ACTUAL_END_DATE               AS ACTUAL_END_DATE,
    task.JSON_DATA					   AS JSON_DATA,
	task.COMPLETED_ID					AS COMPLETED_ID
FROM
    JPM_HI_TASK task
    LEFT JOIN JPM_BUTTON_LANG_DEPLOY   button 
        ON button.BUTTON_DEPLOY_ID = task.ACTION_ID
        AND button.LANG_CODE = UPPER(/*LANG*/'VI')
    LEFT JOIN JPM_STEP_LANG_DEPLOY     step 
        ON step.STEP_DEPLOY_ID = task.STEP_DEPLOY_ID
        AND step.LANG_CODE = UPPER(/*LANG*/'VI')
    LEFT JOIN JPM_STEP_DEPLOY          stepDeploy 
        ON task.STEP_DEPLOY_ID = stepDeploy.ID
    LEFT JOIN JPM_STATUS_LANG_DEPLOY   stepDeployLang 
        ON stepDeploy.STATUS_DEPLOY_ID = stepDeployLang.STATUS_DEPLOY_ID
        AND stepDeployLang.LANG_CODE = UPPER(/*LANG*/'VI')
WHERE
    task.DOC_ID = /*docId*/'1'
ORDER BY
    task.UPDATED_DATE DESC, task.CREATED_DATE DESC;