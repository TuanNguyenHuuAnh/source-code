--
-- JcaRuleSettingRepository_getListJcaRuleSettingDtoByBusinessId.sql
SELECT
    t_rule.*,
    org.name   AS org_name,
    pos.name   AS position_name
FROM
    jca_rule_setting   t_rule
    LEFT JOIN jca_organization   org 
        ON t_rule.org_id = org.id
        AND org.deleted_id = 0
    LEFT JOIN jca_position       pos 
        ON t_rule.position_id = pos.id
        AND pos.deleted_id = 0
WHERE
    business_id = /*businessId*/21