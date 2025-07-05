SELECT
    *
FROM
    jca_m_org
WHERE     
    deleted_date IS NULL
    /*IF orgInfoCondition.orgCode != null && orgInfoCondition.orgCode != ''*/
    AND org_code LIKE '%'+ /*orgInfoCondition.orgCode*/ +'%' 
    /*END*/
    
    /*IF orgInfoCondition.orgName != null && orgInfoCondition.orgName != ''*/
    AND org_name LIKE '%'+ /*orgInfoCondition.orgName*/ +'%' 
    /*END*/
    
    /*IF orgInfoCondition.orgNameAbv != null && orgInfoCondition.orgNameAbv != ''*/
    AND org_name_abv LIKE '%'+ /*orgInfoCondition.orgNameAbv*/ +'%' 
    /*END*/
    
    /*IF orgInfoCondition.parentOrgId != null && orgInfoCondition.parentOrgId != ''*/
    AND org_info.org_tree_id LIKE '%'+ /*orgInfoCondition.parentOrgId*/ +'%' 
    /*END*/
    
    /*IF orgInfoCondition.orgType != null*/
    AND org_info.org_type = /*orgInfoCondition.orgType*/
    /*END*/
ORDER BY 
    org_level
    , parent_org_id
    , order_by