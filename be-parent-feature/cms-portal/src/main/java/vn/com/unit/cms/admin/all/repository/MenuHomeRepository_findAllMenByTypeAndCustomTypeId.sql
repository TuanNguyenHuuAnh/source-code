SELECT DISTINCT
	m.id AS menu_id,
	m.CODE AS menu_code,
	ml.NAME AS menu_name,
	m.url,
	m.parent_id,
	m.STATUS,
	ml.alias,
	m.sort,
	m.create_date,
	m.menu_type,
	m.icon,
	m.check_open
FROM
	jca_m_menu m
LEFT JOIN jca_m_language lang ON lang.CODE = /*languageCode*/
LEFT JOIN jca_m_menu_language ml ON m.id = ml.m_menu_id AND ml.m_language_id = lang.id
LEFT JOIN jca_m_item item ON (item.id = m.item_id)
WHERE
	m.delete_date is null
	AND m.menu_type = /*menuType*/
	/*IF customerType != null*/
		AND m.m_customer_type_id = /*customerType*/
	/*END*/
ORDER BY
	m.parent_id,
	m.sort,
	m.create_date DESC