SELECT 
	buttonLang.LANG_CODE       				AS LANG_CODE
	, buttonLang.BUTTON_NAME      				AS BUTTON_NAME
	, buttonLang.LANG_ID	      				AS LANG_ID
	, buttonLang.BUTTON_ID        				AS BUTTON_ID
	, buttonLang.BUTTON_NAME_IN_PASSIVE         AS BUTTON_NAME_IN_PASSIVE
FROM
	JPM_BUTTON button
LEFT JOIN
	JPM_BUTTON_LANG buttonLang
ON
	button.ID = buttonLang.BUTTON_ID
WHERE
	button.DELETED_ID = 0
  	AND buttonLang.BUTTON_NAME IS NOT NULL
	AND button.PROCESS_ID = /*processId*/