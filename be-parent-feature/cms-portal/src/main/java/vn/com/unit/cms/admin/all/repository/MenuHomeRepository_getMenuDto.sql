SELECT
     menu.id AS menu_id,
     menu.code AS menu_code,
     menu.label AS menu_name,
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
     menu.physical_icon AS physical_icon
     
FROM
	jca_m_menu menu
WHERE
	menu.delete_date is null
    AND menu.id = /*menuId*/ 