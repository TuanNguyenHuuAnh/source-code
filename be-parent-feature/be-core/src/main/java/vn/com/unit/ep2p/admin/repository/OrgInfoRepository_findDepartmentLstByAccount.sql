SELECT 
    orgInfo.*
FROM 
    account_department accDept
LEFT JOIN
    jca_m_org orgInfo
ON
    accDept.org_id = orgInfo.org_id
WHERE 
    accDept.account_id = /*accountId*/
    AND orgInfo.org_type = 'S'
    AND GETDATE() BETWEEN CONVERT(datetime, accDept.effected_date, 101)
                  AND CONVERT(datetime, accDept.expired_date, 101)
