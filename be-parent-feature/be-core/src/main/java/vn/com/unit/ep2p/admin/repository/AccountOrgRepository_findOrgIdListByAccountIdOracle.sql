SELECT
    DISTINCT m_acc_org."org_id"  
FROM
    "HSSA"."jca_account_org" m_acc_org
WHERE
    m_acc_org."account_id" = /*accountId*/
    AND m_acc_org."DELETED_BY" IS NULL
	
