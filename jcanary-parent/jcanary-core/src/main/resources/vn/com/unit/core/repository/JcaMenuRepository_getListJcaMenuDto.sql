SELECT
	menu.ID 						AS ID
	,menu.CODE						AS CODE
	,menu.DISPLAY_ORDER				AS MENU_ORDER
	,menu_path.ANCESTOR_ID			AS PARENT_ID
	,menu.URL						AS URL
	,menu.ITEM_ID					AS ITEM_ID
	,menu.COMPANY_ID				AS COMPANY_ID
	,menu.CLASS_NAME				AS ICON
	,menu.MENU_TYPE					AS MENU_TYPE
	,menu.STATUS					AS STATUS
	,menu.ACTIVED					AS ACTIVED
	,menu.GROUP_FLAG				AS HEADER_FLAG
	,menuLang.NAME					AS NAME
	,menuLang.NAME_ABV				AS ALIAS
	,menuLang.LANG_ID				AS LANG_ID
	,menuLang.LANG_CODE				AS LANG_CODE

FROM 
	JCA_MENU menu
JOIN
	JCA_MENU_PATH menu_path
ON 
	menu.ID  = menu_path.DESCENDANT_ID
	AND menu_path.DEPTH = 1
LEFT JOIN 
	JCA_MENU_LANG menuLang
ON
	menuLang.MENU_ID = menu.ID

WHERE  
	isnull(menu.DELETED_ID,0) = 0
	AND menu.company_id = /*companyId*/1

ORDER BY
	menu_path.ANCESTOR_ID	
	,menu.DISPLAY_ORDER	