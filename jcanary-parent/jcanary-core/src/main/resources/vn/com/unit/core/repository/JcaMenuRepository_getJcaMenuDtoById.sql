SELECT
	menu.ID 						AS ID
	,menu.CODE						AS CODE
	,menu.DISPLAY_ORDER				AS DISPLAY_ORDER
	,menu_path.ANCESTOR_ID			AS PARENT_ID
	,menu_parent.CODE				AS PARENT_MENU_CODE
	,menu.URL						AS URL
	,menu.ITEM_ID					AS ITEM_ID
	,menu.COMPANY_ID				AS COMPANY_ID
	,menu.CLASS_NAME				AS CLASS_NAME
	,menu.MENU_TYPE					AS MENU_TYPE
	,menu.STATUS					AS STATUS
	,menu.ACTIVED					AS ACTIVED
	,menu.GROUP_FLAG				AS GROUP_FLAG
	,menuLang.NAME					AS NAME
	,menuLang.NAME_ABV				AS NAME_ABV
	,menuLang.LANG_ID				AS LANG_ID
	,menuLang.LANG_CODE				AS LANG_CODE
	,menuLang.MENU_ID				AS MENU_ID
	
FROM 
	JCA_MENU menu
LEFT JOIN 
	JCA_MENU_LANG menuLang
ON
	menuLang.MENU_ID = menu.ID
LEFT JOIN
	JCA_MENU_PATH menu_path
ON 
	menu.ID  = menu_path.DESCENDANT_ID
	AND menu_path.DEPTH = 1
LEFT JOIN
	JCA_MENU menu_parent
ON
	menu_parent.ID = menu_path.ANCESTOR_ID
WHERE  
	isnull(menu.DELETED_ID,0) = 0
	AND menu.ID = /*id*/
ORDER BY
	menu_path.ANCESTOR_ID	
	,menu.DISPLAY_ORDER	