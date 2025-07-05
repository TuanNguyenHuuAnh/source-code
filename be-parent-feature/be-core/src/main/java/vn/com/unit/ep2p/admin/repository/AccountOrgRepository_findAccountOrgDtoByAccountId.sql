--
-- AccountOrgRepository_findAccountOrgDtoByAccountId.sql
SELECT
	org.CODE AS ORG_CODE
	, m_acc_org.*
	,case when org.NAME is null then CONCAT(N'Unknown (',CONCAT(m_acc_org.org_ID, ')')) else org.name end as org_name
	,m_acc_org.ACTIVED AS actived
	,case when pos.NAME is null then CONCAT(N'Unknown (',CONCAT(m_acc_org.position_ID, ')')) else pos.NAME end as position_name
FROM
    jca_account_org m_acc_org
LEFT JOIN JCA_ORGANIZATION org ON m_acc_org.org_id = org.id AND org.DELETED_ID = 0
LEFT JOIN JCA_POSITION pos ON m_acc_org.POSITION_ID = pos.ID AND pos.DELETED_ID = 0
WHERE
    m_acc_org.account_id = /*accountId*/
    AND (m_acc_org.org_id = 0 OR (m_acc_org.org_id <>0 and org.id is not null))
    AND (m_acc_org.position_id = 0 OR (m_acc_org.position_id <>0 and pos.id is not null))