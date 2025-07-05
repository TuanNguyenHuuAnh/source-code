SELECT 
    tbl.ID                              						AS  ID
    , tblLang.TITLE                            					AS  NAME
    , CONCAT(tbl.CODE, ' - ', tblLang.TITLE  )					AS  TEXT
FROM M_FAQS_CATEGORY tbl
INNER JOIN M_FAQS_CATEGORY_LANGUAGE tblLang
	ON tblLang.M_FAQS_CATEGORY_ID = tbl.ID
	AND tblLang.M_LANGUAGE_CODE = UPPER(/*lang*/'VI')
WHERE 
	tbl.ENABLED = 1
	AND tbl.DELETE_DATE IS NULL