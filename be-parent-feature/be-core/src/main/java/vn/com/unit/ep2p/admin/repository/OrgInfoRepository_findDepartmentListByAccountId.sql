SELECT 
    m_org.*
FROM 
    jca_m_account_org m_acc_org
LEFT JOIN
    jca_m_org m_org
ON
    m_acc_org.org_id = m_org.id
WHERE 
    m_acc_org.account_id = /*accountId*/
    AND m_acc_org.DELETED_ID = 0
    AND m_org.org_type = 'S'
    AND ( /*CurrentDate*/ BETWEEN m_acc_org.effected_date AND m_acc_org.expired_date )
