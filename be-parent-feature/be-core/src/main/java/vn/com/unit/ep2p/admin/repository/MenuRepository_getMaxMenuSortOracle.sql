SELECT 
	max(menu."sort")
FROM
    "HSSA"."JCA_MENU_PATH" menu
WHERE 
	menu."parent_id" = /*parentId*/
	AND menu."menu_type" = /*menuType*/