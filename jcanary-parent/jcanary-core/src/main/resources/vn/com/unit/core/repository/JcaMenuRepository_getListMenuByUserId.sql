--
-- JcaMenuRepository_getListMenuByUserId.sql
set nocount on;
DROP TABLE IF EXISTS #VW_ACCOUNT;
SELECT DISTINCT *
INTO #VW_ACCOUNT
FROM VW_GET_AUTHORITY_ACCOUNT
WHERE ACCOUNT_ID = /*userId*/2
;with tmp as (
	SELECT
	    menu.ID 						AS ID
		,menu.CODE						AS CODE
		,menu.DISPLAY_ORDER				AS DISPLAY_ORDER
		,main.ANCESTOR_ID			    AS PARENT_ID
		,menu.URL						AS URL
		,menu.ITEM_ID					AS ITEM_ID
		,menu.COMPANY_ID				AS COMPANY_ID
		,menu.CLASS_NAME				AS CLASS_NAME
		,menu.MENU_TYPE					AS MENU_TYPE
		,menu.STATUS					AS STATUS
		,menu.ACTIVED					AS ACTIVED
		,menu.GROUP_FLAG				AS HEADER_FLAG
		,menu_lan.NAME					AS NAME
		,menu_lan.NAME_ABV				AS ALIAS
		,menu_lan.LANG_ID				AS LANG_ID
		,menu_lan.LANG_CODE				AS LANG_CODE
	FROM
	    jca_menu_path       main
	    LEFT JOIN jca_menu            menu
	        ON main.descendant_id = menu.id
	    LEFT JOIN jca_menu_lang   menu_lan
	        ON menu.id = menu_lan.menu_id
	        AND upper(menu_lan.lang_code) = upper(/*langCode*/'EN')
	    LEFT JOIN jca_item          item
	    	ON menu.item_id = item.id
	WHERE main.ANCESTOR_ID = 0
	and main.DESCENDANT_ID = 1
	union
	SELECT
	   menu.ID 						AS ID
		,menu.CODE						AS CODE
		,menu.DISPLAY_ORDER				AS DISPLAY_ORDER
		,main.ANCESTOR_ID			AS PARENT_ID
		,menu.URL						AS URL
		,menu.ITEM_ID					AS ITEM_ID
		,menu.COMPANY_ID				AS COMPANY_ID
		,menu.CLASS_NAME				AS CLASS_NAME
		,menu.MENU_TYPE					AS MENU_TYPE
		,menu.STATUS					AS STATUS
		,menu.ACTIVED					AS ACTIVED
		,menu.GROUP_FLAG				AS HEADER_FLAG
		,menu_lan.NAME					AS NAME
		,menu_lan.NAME_ABV				AS ALIAS
		,menu_lan.LANG_ID				AS LANG_ID
		,menu_lan.LANG_CODE				AS LANG_CODE
	FROM
	    jca_menu_path       main
	    LEFT JOIN jca_menu            menu 
	        ON main.descendant_id = menu.id
	    LEFT JOIN jca_menu_lang   menu_lan 
	        ON menu.id = menu_lan.menu_id
	        AND upper(menu_lan.lang_code) = upper(/*langCode*/'EN')
	    LEFT JOIN jca_item          item 
	    	ON menu.item_id = item.id
		LEFT JOIN #VW_ACCOUNT VW_ACC WITH(NOLOCK)
		ON(ITEM.FUNCTION_CODE = VW_ACC.function_code)
	WHERE
	    menu.menu_type = '2'
	    AND main.depth = 1
	    AND menu.actived = '1'
	    AND ISNULL(menu.deleted_id, 0) = 0
	    AND menu.company_id = /*companyId*/1
		AND( VW_ACC.access_flag = 0 or isnull(menu.item_id,0) = 0)
		AND
			(
				ITEM.FUNCTION_CODE is not NULL
				OR ISNULL(menu.ITEM_ID, 0) = 0
			)
)

select t.*, parent.CODE as PARENT_MENU_CODE
from tmp t
left join JCA_MENU parent
on(parent.ID = t.PARENT_ID)
ORDER BY t.PARENT_ID
    ,  t.DISPLAY_ORDER
    , t.NAME ;
    
set nocount off;