SELECT alertto.SLA_SETTING_ID, alertto.USER_TYPE, alertto.ACCOUNT_ID, alertto.GROUP_ID, alertto.CREATED_BY, alertto.CREATED_DATE, alertto.EMAIL, alertto.COMPANY_ID, 
    alertto.UPDATED_BY, alertto.UPDATED_DATE, alertto.DELETED_BY, alertto.DELETED_DATE, alertto.SEND_MAIL_TYPE 
    FROM SLA_SETTING_ALERT_TO alertto 
    LEFT JOIN SLA_SETTING setting ON setting.ID = alertto.SLA_SETTING_ID 
    LEFT JOIN SLA_STEP step ON step.ID = setting.SLA_STEP_ID 
    WHERE alertto.DELETED_BY IS NULL AND setting.DELETED_BY IS NULL AND step.DELETED_BY IS NULL AND alertto.SLA_SETTING_ID = /*settingId*/0 AND step.PROCESS_ID = /*oldProcessDeployId*/0 
    ORDER BY alertto.ID asc