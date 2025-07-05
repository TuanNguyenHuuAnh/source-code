SELECT 
	case when formLang.FORM_NAME IS NOT NULL
	THEN formLang.FORM_NAME
	ELSE form.NAME END	AS NAME
	, form.*
FROM
	EFO_FORM form
LEFT JOIN
	EFO_FORM_Lang formLang
ON 
	form.ID = formLang.FORM_ID
	AND UPPER(formLang.LANG_CODE) = UPPER(/*lang*/'EN')
WHERE
	form.DELETED_ID = 0
	AND form.ID = /*id*/0
