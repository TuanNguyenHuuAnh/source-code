--
-- JcaSystemConfigRepository_getValueByKey.sql
SELECT
    setting.setting_value AS setting_value
FROM
    jca_system_setting setting
WHERE
     setting.setting_key = /*settingKey*/''
    AND setting.company_id = /*companyId*/1