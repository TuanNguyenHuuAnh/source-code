SELECT 
	tbl.ID
	, tblLang.TITLE												AS	name
	, CONCAT(tbl.CODE, ' - ', tblLang.TITLE)					AS	text
FROM M_BANNER tbl
INNER JOIN M_BANNER_LANGUAGE tblLang
	ON tblLang.BANNER_ID = tbl.ID
WHERE
	tbl.DELETE_DATE IS NULL
	/*IF bannerDevice != null && bannerDevice != ''*/
	AND tbl.BANNER_DEVICE = /*bannerDevice*/1
	/*END*/
	/*IF bannerType != null && bannerType != ''*/
	AND tbl.BANNER_TYPE = /*bannerType*/1
	/*END*/