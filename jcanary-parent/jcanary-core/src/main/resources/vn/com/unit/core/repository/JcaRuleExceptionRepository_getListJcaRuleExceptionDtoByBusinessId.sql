--
-- JcaRuleExceptionRepository_getListJcaRuleExceptionDtoByBusinessId.sql
SELECT
    t_rule.*,
    org.name   AS org_name,
    acc.FULLNAME   AS user_name
FROM
    JCA_RULE_EXCEPTION   t_rule
    LEFT JOIN jca_organization   org 
        ON t_rule.org_id = org.id
        AND org.deleted_id = 0
    LEFT JOIN JCA_ACCOUNT       acc 
        ON t_rule.ACCOUNT_ID = acc.id
        AND acc.deleted_id = 0
WHERE
    business_id = /*businessId*/21