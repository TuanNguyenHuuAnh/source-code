SELECT
    MAX(menu.display_order)
FROM
    jca_menu_path   main
    LEFT JOIN jca_menu        menu 
        ON main.descendant_id = menu.id
WHERE
    main.ancestor_id = /*parentId*/''
    AND menu.menu_type = /*menuType*/''