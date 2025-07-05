--
-- MenuRepository_findMenuByModuleAndLanguage
-- Clone from MenuRepository_findAllByLanguage.sql

drop table if exists #TMP_MENU;

select
	distinct
	menu.ID
	, menu_path.ANCESTOR_ID as PARENT_ID
	, menu.CODE
    , menu.MENU_MODULE
into #TMP_MENU
from JCA_MENU menu
left join JCA_MENU_PATH menu_path
	on menu.ID = menu_path.DESCENDANT_ID
where
	menu.COMPANY_ID = /*companyId*/1

;

with menu_tree
as (
	select
		menu.ID
		, menu_path.ANCESTOR_ID as PARENT_ID
	from #TMP_MENU menu
	left join JCA_MENU_PATH menu_path
		on menu.ID = menu_path.DESCENDANT_ID
	where
       menu.menu_module = 'ROOT'
	   OR menu.menu_module IN /*menuModule*/()
	   
	union all
	
	select
		menu.ID
		, menu.PARENT_ID
	from #TMP_MENU menu
	join menu_tree
		on menu_tree.ID = menu.PARENT_ID
		and menu.ID <> menu.PARENT_ID
)
SELECT
    menu.id              AS menu_id,
    menu_lan.name        AS menu_name,
    menu_lan.name_abv    AS alias,
    menu.code            AS menu_code,
    menu.url             AS url,
    menu.class_name      AS icon,
    main.ancestor_id     AS parent_id,
    menu.display_order   AS sort,
    menu.item_id         AS item_id,
    item.function_code   AS function_code,
    menu.menu_type
FROM
    jca_menu_path       main with(nolock)
    LEFT JOIN jca_menu            menu  with(nolock)
        ON main.descendant_id = menu.id
    LEFT JOIN jca_menu_lang   menu_lan  with(nolock)
        ON menu.id = menu_lan.menu_id
        AND upper(menu_lan.lang_code) = upper(/*languageCode*/'en')
    LEFT JOIN jca_item          item  with(nolock)
    	ON menu.item_id = item.id
WHERE
    menu.actived = '1'
    AND (
        menu.menu_module = 'ROOT'
	    /*IF menuModule != null && menuModule.size() > 0*/
	    OR menu.menu_module IN /*menuModule*/()
	    /*END*/
    )
    AND main.depth = 1
    AND isnull(menu.deleted_id,0) = 0
    AND menu.company_id = /*companyId*/1
	AND (
		menu.id in (select ID from menu_tree)
		OR
		menu.id in (select PARENT_ID from menu_tree)
	)
ORDER BY
    main.ancestor_id,
    menu.display_order