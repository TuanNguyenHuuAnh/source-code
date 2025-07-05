SELECT
    m.id as menu_id,    
    m.code as menu_code,
    ml.name as menu_name,
    m.url,
    m.parent_id,
    m.status
FROM
    JCA_MENU_PATH m
    LEFT JOIN JCA_LANGUAGE lang ON lang.code = /*languageCode*/
    LEFT JOIN JCA_MENU_language ml ON m.id = ml.m_menu_id AND ml.m_language_id = lang.id
WHERE 
	m.DELETED_ID = 0
	AND m.parent_id = /*parentId*/