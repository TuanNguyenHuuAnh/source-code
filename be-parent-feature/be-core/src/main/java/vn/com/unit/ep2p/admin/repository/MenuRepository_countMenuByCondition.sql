SELECT
    COUNT(1)
FROM
    jca_menu_path       main
    LEFT JOIN jca_menu            menu 
        ON main.descendant_id = menu.id
    LEFT JOIN jca_menu_lang   mlvi 
        ON menu.id = mlvi.menu_id
        AND upper(mlvi.lang_code) = upper(/*languageCode*/'')
    LEFT JOIN jca_menu_lang   mlparent 
        ON main.ancestor_id = mlparent.menu_id
        AND upper(mlparent.lang_code) = upper(/*languageCode*/'')
	
WHERE
    isnull(menu.deleted_id,0) = 0
    AND main.depth = 1
    AND menu.menu_type IN /*menuDto.menuTypeList*/('')
    AND menu.company_id = /*menuDto.companyId*/''
	/*BEGIN*/
    AND (
		/*IF menuDto.menuName != null && menuDto.menuName != ''*/
		  OR (upper(mlvi.name) LIKE concat('%', concat(upper(/*menuDto.menuName*/1), '%')))
	    /*END*/

	    /*IF menuDto.url != null && menuDto.url != ''*/
          OR upper(menu.url) LIKE concat('%', concat(upper(/*menuDto.url*/''), '%'))
	    /*END*/
	    
	    /*IF menuDto.menuCode != null && menuDto.menuCode != ''*/
          OR upper(menu.code) LIKE concat('%', concat(upper(/*menuDto.menuCode*/''), '%'))
	    /*END*/

	    /*IF menuDto.menuType != null && menuDto.menuType != ''*/
          OR upper(menu.menu_type) LIKE concat('%', concat(upper(/*menuDto.menuType*/''), '%'))
	    /*END*/
	    
	    /*IF menuDto.parentId != null*/
          OR main.ancestor_id = /*menuDto.parentId*/''
	    /*END*/
	    
	    /*IF menuDto.menuId != null*/
          OR menu.id = /*menuDto.menuId*/''
		/*END*/ )
    /*END*/