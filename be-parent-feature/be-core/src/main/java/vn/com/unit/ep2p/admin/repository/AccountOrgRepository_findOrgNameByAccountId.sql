SELECT 
	case m_acc_org.org_id when 0 then CONCAT(N'Unknown (',CONCAT(m_acc_org.org_code, ')')) else org.org_name end as org_name
FROM
    jca_account_org m_acc_org
LEFT JOIN JCA_M_ORG org ON m_acc_org.org_id = org.id AND org.DELETED_ID = 0
WHERE
    m_acc_org.account_id = /*accountId*/
    AND m_acc_org.ASSIGN_TYPE = '1'
    AND (m_acc_org.org_id = 0 OR (m_acc_org.org_id <>0 and org.id is not null))
order by m_acc_org.id DESC
	
