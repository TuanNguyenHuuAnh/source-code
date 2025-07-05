SELECT
    m.id 			AS menu_id,    
    m.code 			AS menu_code,
    ml.name 		AS menu_name,
    m.url,
    main.ancestor_id AS parent_id,
    m.status,
    m.CREATED_DATE
FROM
    jca_menu_path       main
    LEFT JOIN jca_menu            m
        ON main.descendant_id = m.id
    LEFT JOIN JCA_MENU_LANG ml 
	    ON m.id = ml.menu_id 
	    and UPPER(ml.lang_code) = UPPER(/*languageCode*/'') 
WHERE 
	m.DELETED_id = 0
	AND main.ancestor_id = /*parentId*/''
	AND main.depth = 1
ORDER BY m.display_order