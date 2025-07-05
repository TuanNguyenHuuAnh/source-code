SELECT 
		menu.id     AS id,
		mlang.name	AS NAME,
		menu.url    as LINK,
		case when ac.CHANNEL = 'AG' then item.ICON_PATH_AG
			else item.ICON_PATH end AS ICON,
		item.function_code as ITEM_ID,
		case when menu.URL like 'http%' then 'false' else 'true' end as LOCAL_LINK
	FROM
		jca_menu_path       main
		inner JOIN jca_menu            menu 
			ON main.descendant_id = menu.id and menu.ACTIVED=1
		left join JCA_MENU_LANG		mlang
			on mlang.MENU_ID = menu.id
			and mlang.LANG_ID=2
		inner join VW_GET_AUTHORITY_ACCOUNT auth
			ON menu.ITEM_ID = auth.item_id
			and auth.ACCOUNT_ID = /*accountId*/
		inner join JCA_ACCOUNT ac
			ON ac.ID = ACCOUNT_ID
		left join jca_item			  item
			ON auth.item_id = item.ID
	WHERE
		isnull(menu.deleted_id,0) = 0
		AND main.depth = 1
		and main.ancestor_id=/*menuId*/
		and menu.MENU_MODULE= (case when ac.CHANNEL = 'AG' then 'FE-AG' else 'FE' end)