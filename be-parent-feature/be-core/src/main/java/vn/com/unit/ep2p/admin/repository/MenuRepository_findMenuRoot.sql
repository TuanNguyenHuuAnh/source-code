SELECT
    main.ancestor_id     AS parent_id,
    main.descendant_id   AS id
FROM
    jca_menu_path       main
    LEFT JOIN jca_menu            menu 
        ON main.descendant_id = menu.id
WHERE
    main.ancestor_id = 0
    /*IF companyId != null*/
		AND menu.COMPANY_ID = /*companyId*/1
	/*END*/
    