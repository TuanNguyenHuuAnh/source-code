SELECT *
FROM
    jca_m_org
WHERE
    DELETED_ID = 0
    /*IF orgType!= null && orgType!=''*/
    	AND org_type = /*orgType*/''
    /*END*/
    AND company_id = /*companyId*/
ORDER BY 
    org_level
    , parent_org_id
    , order_by