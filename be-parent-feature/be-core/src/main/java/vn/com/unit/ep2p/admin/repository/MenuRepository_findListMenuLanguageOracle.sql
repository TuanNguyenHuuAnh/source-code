SELECT 
	lang."id" AS menu_language_id,
    lang."name" AS language_name,
    lang."m_language_id" AS language_id,
    lang."alias"
FROM
    "HSSA"."JCA_MENU_language" lang
WHERE 
	lang."DELETED_BY" IS NULL
	AND lang."m_menu_id" = /*menuId*/