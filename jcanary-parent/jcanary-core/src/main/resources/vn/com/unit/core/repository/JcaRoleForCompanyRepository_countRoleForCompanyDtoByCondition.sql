SELECT
    /*roleId*/                                     AS role_id
    , m_item.id                                    AS item_id
    , m_item.business_code                         AS business_code
    , m_author_pivot.can_access_flg                AS can_access_flg
    , m_author_pivot.can_disp_flg                  AS can_disp_flg
    , m_author_pivot.can_edit_flg                  AS can_edit_flg
    , m_item.function_type                         AS authority_type
    , m_item.function_name                         AS function_name
    , m_item.function_code                         AS function_code
    , m_item.menu_type 			as menu_type
FROM
    jca_item m_item
LEFT JOIN
    vw_authority_pivot m_author_pivot
ON
    m_author_pivot.item_id = m_item.id
    AND m_author_pivot.role_id = /*roleId*/
WHERE
    m_item.deleted_date IS NULL
    --AND m_item.display_flag = '1'
    /*IF companyId != null*/
	AND (m_item.company_id is null or m_item.company_id = /*companyId*/1)
	/*END*/
	/*IF companyId == null*/
	AND m_item.company_id is null
	--AND m_item.sub_type IN ('LINK', 'FUNC')
	/*END*/
	AND m_item.function_type in /*functionTypes*/()
ORDER BY
	m_item.display_order ASC
    ,m_item.function_name ASC