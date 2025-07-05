SELECT 
	lang.menu_id AS menu_language_id,
    lang.lang_id AS language_id,
    lang.name AS language_name,
    lang.name_abv AS alias
FROM
    JCA_MENU_lang lang
WHERE 
	lang.menu_id = /*menuId*/''