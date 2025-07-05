SELECT
    ss.*,
    case when ss.company_id is null then 0 else ss.company_id end order_company
FROM
    jca_system_setting ss
WHERE
    /*IF companyId != null*/
	(ss.company_id = /*companyId*/1 or ss.company_id is null)
	/*END*/
ORDER BY order_company DESC, ss.setting_key