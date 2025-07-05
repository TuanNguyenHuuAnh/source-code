SELECT 
	*
	, content								AS	content_byte
FROM M_EMULATE_LANGUAGE
WHERE
	DELETE_DATE IS NULL
	AND M_EMULATE_ID = /*id*/1
	/*IF lang != null && lang != ''*/
	AND M_LANGUAGE_CODE = /*lang*/'VI'
	/*END*/