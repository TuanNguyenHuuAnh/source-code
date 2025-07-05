SELECT 
    tbl.ID                              						AS  ID
    , tblLang.TITLE                            					AS  NAME
    , CONCAT(tbl.CODE, ' - ', tblLang.TITLE  )					AS  TEXT
FROM M_DOCUMENT_CATEGORY tbl
INNER JOIN M_DOCUMENT_CATEGORY_LANGUAGE tblLang
	ON tblLang.M_CATEGORY_ID = tbl.ID
	AND tblLang.M_LANGUAGE_CODE = UPPER(/*lang*/'VI')
WHERE 
	tbl.ENABLED = 1
	AND tbl.DELETE_DATE IS NULL
	/*IF channel == null || channel == ''*/
	AND isnull(tbl.CHANNEL, 'AG') = 'AG'
	/*END*/
	/*IF channel != null && channel == 'AG'*/
	AND isnull(tbl.CHANNEL, 'AG') = /*channel*/
	/*END*/
	/*IF channel != null && channel == 'AD'*/
	AND tbl.CHANNEL = /*channel*/
	/*END*/