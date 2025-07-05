SELECT 
	*
FROM 
	"HSSA"."JCA_MENU_language" ml
WHERE 
	ml."DELETED_BY" IS NULL
	AND ml."m_menu_id" = /*menuId*/