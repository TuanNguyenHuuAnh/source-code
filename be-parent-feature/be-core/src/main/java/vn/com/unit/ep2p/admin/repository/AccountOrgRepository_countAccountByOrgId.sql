SELECT count(*)
FROM
    jca_account_org m_acc_org
INNER JOIN jca_account acc ON m_acc_org.ACCOUNT_ID = acc.id AND acc.DELETED_ID = 0 AND acc.ENABLED = 1
WHERE m_acc_org.ORG_ID = /*orgId*/