SELECT acc.*
	   , position.name_abv as position_name
FROM
    jca_account_org m_acc_org
INNER JOIN JCA_ACCOUNT acc ON m_acc_org.ACCOUNT_ID = acc.id AND acc.DELETED_ID = 0 AND acc.ENABLED = 1
LEFT JOIN jca_position position ON m_acc_org.position_id = position.id AND position.DELETED_ID = 0

WHERE m_acc_org.ORG_ID = /*orgId*/
ORDER BY
	acc.company_id,
    acc.username ASC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY