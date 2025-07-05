SELECT DISTINCT
	m.id AS menu_id,
	m.CODE AS menu_code,
	ml.NAME AS menu_name,
	m.url,
	m.parent_id,
	m.STATUS,
	ml.alias,
	m.sort,
	m.CREATED_DATE,
	m.menu_type,
	m.icon,
	m.check_open
FROM
	JCA_MENU_PATH m
LEFT JOIN JCA_LANGUAGE lang ON lang.CODE = /*languageCode*/
LEFT JOIN JCA_MENU_language ml ON m.id = ml.m_menu_id AND ml.m_language_id = lang.id
LEFT JOIN JCA_ITEM item ON (item.id = m.item_id)
WHERE
	m.DELETED_ID = 0
	AND m.menu_type = /*menuType*/
	/*IF listFunctionCode != null*/
		AND (item.function_code IN /*listFunctionCode*/())
	/*END*/
ORDER BY
	m.parent_id,
	m.sort,
	m.CREATED_DATE DESC