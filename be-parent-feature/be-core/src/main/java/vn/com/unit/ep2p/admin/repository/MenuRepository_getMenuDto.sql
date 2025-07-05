SELECT
     menu.id 						AS menu_id,
     menu.code 						AS menu_code,
     menu.url,
     main.ancestor_id               AS parent_id,
     menu.status,
     menu.display_order             AS sort,
     menu.class_name 			    AS icon,
     menu.menu_type,
     item.FUNCTION_NAME 			AS item_name,
     menu.item_id 					AS item_id,
     menu.status                    AS status_code,
     menu.id 						AS reference_id,
     menu.actived 					AS active_menu,
     menu.company_id,
     menu.menu_module
FROM
	jca_menu_path       main
    LEFT JOIN jca_menu            menu 
        ON main.descendant_id = menu.id
    LEFT JOIN JCA_ITEM item 
        ON menu.item_id = item.id 
        AND isnull(item.DELETED_ID,0) = 0
        AND item.ACTIVED = 1
WHERE
	isnull(menu.DELETED_ID,0) = 0
	AND main.depth = 1
    AND menu.id = /*menuId*/''