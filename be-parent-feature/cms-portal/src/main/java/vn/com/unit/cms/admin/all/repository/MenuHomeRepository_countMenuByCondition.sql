SELECT
	count(*)
FROM
	jca_m_menu menu
	LEFT JOIN jca_m_menu_language mlvi ON menu.id = mlvi.m_menu_id AND mlvi.m_language_id=1
	LEFT JOIN jca_m_menu_language mlen ON menu.id = mlen.m_menu_id AND mlen.m_language_id=2
	LEFT JOIN jca_m_language lang ON lang.code = /*languageCode*/
	LEFT JOIN jca_m_menu_language mlParent ON menu.parent_id = mlParent.m_menu_id AND mlParent.m_language_id=lang.id
	LEFT JOIN jca_constant_display cd1 ON cd1.type = 'M06' AND cd1.cat = menu.status_code
	LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = menu.m_customer_type_id 
											AND customerType.m_language_code = lang.code
											AND customerType.delete_date is null)
WHERE
	menu.delete_date is null
    AND menu.menu_type IN /*menuDto.menuTypeList*/()

	/*BEGIN*/
	AND (
		/*IF menuDto.menuName != null && menuDto.menuName != ''*/
	    OR (mlvi.name LIKE CONCAT('%',/*menuDto.menuName*/,'%')
	    	OR mlen.name LIKE CONCAT('%',/*menuDto.menuName*/,'%') 
	    	OR mlParent.name LIKE CONCAT('%',/*menuDto.menuName*/,'%'))
	    /*END*/

	    /*IF menuDto.url != null && menuDto.url != ''*/	
	    OR menu.url LIKE CONCAT('%',/*menuDto.url*/,'%')
	    /*END*/
	    
	    /*IF menuDto.menuCode != null && menuDto.menuCode != ''*/	
	    OR menu.code LIKE CONCAT('%',/*menuDto.menuCode*/,'%')
	    /*END*/
	    
	    /*IF menuDto.parentId != null && menuDto.parentId != ''*/	
	    OR menu.parent_id = /*menuDto.parentId*/
	    /*END*/
	    
	    /*IF menuDto.customerTypeName != null && menuDto.customerTypeName != ''*/
		OR customerType.title LIKE concat('%',  /*menuDto.customerTypeName*/, '%')
		/*END*/
	    )
    /*END*/
    