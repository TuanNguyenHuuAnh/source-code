SELECT
    m_org.id
    , m_org.org_code
    , m_org.org_name
    , m_org.org_name_abv
    , m_org.effected_date
    , m_org.expired_date
    , m_org.parent_org_id
    , m_org.org_level
    , m_org.order_by
    , m_org.org_tree_id
    , m_org.email
    , m_org.phone
    , m_org.address   
FROM
    jca_m_org m_org
WHERE
    m_org.delete_by IS NULL
    AND m_org.expired_date > /*expired_date*/
    AND m_org.org_type = /*orgType*/
ORDER BY 
    m_org.org_level
    , m_org.parent_org_id
    , m_org.order_by