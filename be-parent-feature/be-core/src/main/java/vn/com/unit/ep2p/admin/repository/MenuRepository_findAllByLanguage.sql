-- MenuRepository_findAllByLanguage.sql
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
        AND upper(menu_lan.lang_code) = upper(/*languageCode*/'')
    LEFT JOIN jca_item          item  with(nolock)
    	ON menu.item_id = item.id
WHERE
    menu.menu_type IN ('1','3')
    AND main.depth = 1
    AND menu.actived = '1'
    AND isnull(menu.deleted_id,0) = 0
    AND menu.company_id = /*companyId*/1
ORDER BY
    main.ancestor_id,
    menu.display_order