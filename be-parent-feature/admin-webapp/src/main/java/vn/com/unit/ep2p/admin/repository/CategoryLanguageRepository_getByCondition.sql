SELECT
    *
FROM EFO_CATEGORY_LANG
WHERE category_id = /*categoryID*/
	/*IF languageCode != null && languageCode != ''*/
	AND UPPER(LANGUAGE_CODE) = UPPER(/*languageCode*/'')
	/*END*/
