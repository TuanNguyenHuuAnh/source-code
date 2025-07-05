SELECT
    MENU_ID
	,LANG_ID
	,LANG_CODE
	,NAME
	,NAME_ABV
    
FROM
    jca_menu_lang
WHERE MENU_ID = /*menuId*/
	AND LANG_ID = /*langId*/

