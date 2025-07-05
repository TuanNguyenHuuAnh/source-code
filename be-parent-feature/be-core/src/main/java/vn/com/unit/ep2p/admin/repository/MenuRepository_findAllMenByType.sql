SELECT DISTINCT
    m.id               AS menu_id,
    m.code             AS menu_code,
    ml.name            AS menu_name,
    m.url,
    main.ancestor_id   AS parent_id,
    m.status,
    ml.name_abv 	   AS alias,
    m.display_order    AS sort,
    m.created_date,
    m.menu_type,
    m.class_name 	   AS icon
FROM
    jca_menu_path       main
    LEFT JOIN jca_menu            m 
    	ON main.descendant_id = m.id
    LEFT JOIN jca_menu_lang   ml 
	    ON m.id = ml.menu_id
	    AND upper(ml.lang_code) = upper(/*languageCode*/'')
WHERE
    ISNULL(m.deleted_id, 0) = 0
    AND main.depth = 1
    AND m.menu_type = /*menuType*/''
    AND m.company_id = /*companyId*/''
    AND m.actived = '1'
ORDER BY
    main.ancestor_id,
    m.display_order,
    m.created_date DESC