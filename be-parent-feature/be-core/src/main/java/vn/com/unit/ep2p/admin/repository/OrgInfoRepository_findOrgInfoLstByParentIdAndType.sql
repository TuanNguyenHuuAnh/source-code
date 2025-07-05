SELECT
    * 
FROM
    jca_m_org org_info
WHERE
    org_info.deleted_date IS NULL
    /*IF parentId != null && parentId != ''*/
    AND org_info.org_tree_id LIKE '%'+ /*parentId*/ +'%' 
    /*END*/    
    /*IF orgTypeLst != null*/
    AND org_info.org_type IN /*orgTypeLst*/() 
    /*END*/
ORDER BY 
    org_info.org_level
    , org_info.parent_org_id
    , org_info.order_by