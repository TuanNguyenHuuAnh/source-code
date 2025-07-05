SELECT
    MAX(pos.DISPLAY_ORDER)
FROM
    jca_position_path   main
    LEFT JOIN jca_position        pos 
        ON main.descendant_id = pos.id
WHERE
    main.ancestor_id = /*parentId*/''
    AND pos.DELETED_ID = 0