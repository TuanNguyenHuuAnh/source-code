SELECT
	/*IF dbType == 'ORACLE'*/
    TO_CHAR(button.id)    AS button_id,
    /*END*/
    /*IF dbType == 'MSSQL'*/
    CONVERT(VARCHAR(50), button.id)    AS button_id,
    /*END*/
    button.button_value   AS button_value,
    button.button_type    AS button_type
FROM
    jpm_button_deploy button
WHERE
    button.DELETED_ID = 0
    AND button.process_deploy_id = /*processDeployId*/1
    AND button.id = /*buttonDeployId*/1
