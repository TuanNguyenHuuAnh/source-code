SELECT

    setting.setting_key     AS setting_key,
    setting.setting_value   AS setting_value,
    setting.company_id      AS company_id,
    setting.group_code        AS group_code,
    setting.created_date    AS created_date,
    setting.created_id      AS created_id,
    setting.updated_date    AS updated_date,
    setting.updated_id      AS updated_id,
    setting.deleted_date    AS deleted_date,
    setting.deleted_id      AS deleted_id,

FROM
    jca_system_setting         setting

WHERE
    setting.DELETED_ID = 0
    AND group_s.DELETED_ID = 0
    AND setting.company_id = /*companyId*/1
    AND setting.group_code = /*groupCode*/''
ORDER BY setting.setting_value