SELECT 
	button.ID AS ID
	, lang.BUTTON_NAME AS NAME
	, lang.BUTTON_NAME AS TEXT
FROM
	JPM_BUTTON button
LEFT JOIN 
	JPM_BUTTON_LANG lang
ON
	button.ID = lang.BUTTON_ID
	and UPPER(lang.LANG_CODE) = UPPER(/*lang*/'')
WHERE
	button.DELETED_ID = 0
	AND button.PROCESS_ID = /*processId*/0
ORDER BY 
	button.DISPLAY_ORDER