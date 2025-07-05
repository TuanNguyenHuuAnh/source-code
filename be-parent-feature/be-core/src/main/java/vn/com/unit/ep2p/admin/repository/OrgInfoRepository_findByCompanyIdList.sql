SELECT *
FROM
    jca_m_org
WHERE
    DELETED_ID = 0
    AND company_id in /*companyIds*/()
ORDER BY 
    org_level
    , parent_org_id
    , order_by