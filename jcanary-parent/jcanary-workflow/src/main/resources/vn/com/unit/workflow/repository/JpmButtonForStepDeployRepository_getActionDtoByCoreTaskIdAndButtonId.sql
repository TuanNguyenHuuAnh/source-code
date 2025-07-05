SELECT
    func.permission_code                AS func_code,
    buttonstep.option_save_form         AS is_save,
    buttonstep.option_authenticate      AS is_authenticate,
    buttonstep.option_signed            AS is_sign,
    buttonstep.option_export_pdf        AS is_export_pdf,
    buttonstep.option_show_history      AS display_history,
    buttonstep.option_fill_to_eform     AS field_sign,
    button.id                           AS button_id,
    button.button_value                 AS button_value,
    button.button_type                  AS button_type,
    CASE
        WHEN /*commonStatusCode*/'' = '000' THEN
            1
        ELSE
            0
    END AS is_submit
FROM
    jpm_step_deploy              step
    LEFT JOIN jpm_button_for_step_deploy   buttonstep ON step.id = buttonstep.step_deploy_id
    LEFT JOIN jpm_permission_deploy        func ON buttonstep.permission_deploy_id = func.id
    LEFT JOIN jpm_button_deploy            button ON button.id = buttonstep.button_deploy_id
/*IF commonStatusCode !='000'*/
    LEFT JOIN jpm_task                     task ON task.STEP_DEPLOY_ID = step.ID
/*END*/
WHERE
    step.DELETED_ID = 0
    AND button.id = /*buttonId*/0
    AND step.process_deploy_id = /*processDeployId*/0
	
	/*IF commonStatusCode !='000'*/
    AND task.core_task_id = /*coreTaskId*/1
	/*END*/
	
	/*IF commonStatusCode == '000'*/
    AND step.step_no = 1
	/*END*/;