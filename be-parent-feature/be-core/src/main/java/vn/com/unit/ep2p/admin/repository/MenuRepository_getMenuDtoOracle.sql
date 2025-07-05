SELECT
	menu.id AS menu_id,
	menu.code AS menu_code,
	menu.url,
	menu.parent_id,
	menu.status,
	menu.sort,
	menu.icon,
	menu.menu_type,
	menu.check_open,
	menu.item_id AS item_id,
	menu.status_code,
	menu.process_id,
	menu.id AS reference_id,
	menu.m_customer_type_id AS customer_type_id,
	menu.icon_img AS icon_img,
	menu.physical_icon AS physical_icon,
	menu.positions AS menu_type_postion,
	menu.active_menu,
	menu.company_id
FROM
	JCA_MENU_PATH menu
WHERE
	menu.DELETED_ID = 0
    AND menu.id = /*menuId*/ 