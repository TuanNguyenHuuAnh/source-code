SELECT
    COUNT(1)
FROM
    jca_menu_path               AS  main
INNER JOIN jca_menu             AS  menu 
    ON main.descendant_id = menu.id
WHERE
    isnull(menu.deleted_id,0) = 0
    AND main.depth > 0
    AND main.ancestor_id = /*menuId*/1