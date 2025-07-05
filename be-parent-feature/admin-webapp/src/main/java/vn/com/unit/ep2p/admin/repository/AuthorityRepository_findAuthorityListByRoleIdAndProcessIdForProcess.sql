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
    JCA_ITEM m_item
LEFT JOIN
    vw_authority_pivot m_author_pivot
ON
    m_author_pivot.item_id = m_item.id
    AND m_author_pivot.role_id = /*roleId*/
WHERE
    m_item.deleted_date IS NULL
    --AND m_item.display_flag = '1'
    AND m_item.function_type = /*functionType*/
    AND m_item.process_id = /*processId*/
	AND m_item.sub_type = /*subType*/
ORDER BY
	m_item.display_order ASC
    , m_item.function_name ASC
