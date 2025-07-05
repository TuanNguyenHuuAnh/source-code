--
-- JcaSystemConfigRepository_getValueByKeyDefault.sql
SELECT
    setting.setting_value AS setting_value
FROM
    jca_system_setting_default setting
WHERE
     setting.setting_key = /*settingKey*/''