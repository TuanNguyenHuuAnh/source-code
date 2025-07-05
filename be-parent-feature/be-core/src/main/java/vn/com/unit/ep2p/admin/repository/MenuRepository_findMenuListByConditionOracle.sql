SELECT
     menu.id AS menu_id,
     menu.code AS menu_code,
     menu.label AS menu_name,
     menu.url,
     menu.parent_id,
     menu.status,
     mlvi.name AS vi_name,
     mlParent.name AS parent_name,
     menu.sort,
     menu.CREATED_DATE,
     menu.icon,
     menu.check_open,
     menu.status_code,
     menu.process_id,
     menu.id AS reference_id,	 
	 cd1.code AS status_code_disp,
	 menu.menu_type AS menu_type
FROM
	JCA_MENU_PATH menu
	LEFT JOIN JCA_MENU_language mlvi ON menu.id = mlvi.m_menu_id AND UPPER(mlvi.LANGuage_CODE)= UPPER(/*languageCode*/)
	LEFT JOIN JCA_MENU_language mlParent ON menu.parent_id = mlParent.m_menu_id AND UPPER(mlParent.LANGuage_CODE)= UPPER(/*languageCode*/)
	LEFT JOIN JCA_CONSTANT cd1 ON cd1.type = 'M06' AND cd1.cat = menu.status_code
WHERE
	menu.DELETED_ID = 0
    AND menu.menu_type IN /*menuDto.menuTypeList*/()
	AND menu.company_id = /*menuDto.companyId*/1
	/*BEGIN*/
	AND (
		/*IF menuDto.menuName != null && menuDto.menuName != ''*/
	    OR (UPPER(mlvi.name) LIKE CONCAT( '%', CONCAT( UPPER(/*menuDto.menuName*/), '%' ) ) )
	    /*END*/

	    /*IF menuDto.url != null && menuDto.url != ''*/	
	    OR UPPER(menu.url) LIKE CONCAT( '%', CONCAT( UPPER(/*menuDto.url*/), '%' ) )
	    /*END*/
	    
	    /*IF menuDto.menuCode != null && menuDto.menuCode != ''*/	
	    OR UPPER(menu.code) LIKE CONCAT( '%', CONCAT( UPPER(/*menuDto.menuCode*/) , '%' ) )
	    /*END*/
	    
	    /*IF menuDto.parentId != null*/	
	    OR menu.parent_id = /*menuDto.parentId*/
	    /*END*/
	    
	    /*IF menuDto.menuId != null*/	
		OR menu.id = /*menuDto.menuId*/
		/*END*/
		)
    /*END*/
ORDER BY menu.parent_id, 
	menu.sort