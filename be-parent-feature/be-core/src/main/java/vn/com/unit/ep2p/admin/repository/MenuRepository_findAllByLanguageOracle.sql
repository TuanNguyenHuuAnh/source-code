SELECT 
	menu."id" 			AS menu_id,
	menu_lan."name"		AS menu_name,
	menu_lan."alias"	AS alias,
	menu."code"			AS menu_code,
	menu."url"			AS url,
	menu."icon"			AS icon,
	menu."check_open"	AS check_open,
	menu."parent_id"	AS parent_id,
	menu."sort"			AS sort
FROM "HSSA"."JCA_MENU_PATH" menu
LEFT JOIN "HSSA"."JCA_MENU_language" menu_lan ON (menu."id" = menu_lan."m_menu_id")
LEFT JOIN "HSSA"."JCA_LANGUAGE" lan ON (menu_lan."m_language_id" = lan."id")
WHERE 
	menu."menu_type" = 1 AND menu."active_menu" = 1
	AND lan."code" = /*languageCode*/
ORDER BY 
	menu."parent_id", 
	menu."sort"