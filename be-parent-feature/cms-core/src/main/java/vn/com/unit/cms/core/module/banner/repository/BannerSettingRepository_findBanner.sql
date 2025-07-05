SELECT 
		tbl.ID																AS bannerId
	, tblLang.TITLE												AS	name
	, CONCAT(tbl.CODE, ' - ', tblLang.TITLE)					AS	text
	, tblLang.BANNER_IMG  								AS img
FROM M_BANNER tbl
INNER JOIN M_BANNER_LANGUAGE tblLang
	ON tblLang.BANNER_ID = tbl.ID
	AND tblLang.M_LANGUAGE_CODE = /*lang*/'VI'
WHERE
	tbl.DELETE_DATE IS NULL
	AND tbl.id = /*bannerId*/''
	
	/*END*/


