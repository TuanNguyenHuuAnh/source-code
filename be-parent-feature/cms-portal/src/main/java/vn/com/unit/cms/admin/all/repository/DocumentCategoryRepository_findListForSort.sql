SELECT
    tbl.id 									AS id
    , tblLang.title 						AS title
    , tbl.enabled
    , tbl.create_date
FROM M_DOCUMENT_CATEGORY tbl
LEFT JOIN M_DOCUMENT_CATEGORY_language tblLang 
	ON tblLang.M_CATEGORY_ID = tbl.id
WHERE
	tbl.delete_date is null
	AND tbl.ENABLED = 1
	AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
	AND tbl.PARENT_ID = /*searchDto.parentId*/1
	/*IF searchDto.channel == null || searchDto.channel == ''*/
	AND isnull(TBL.CHANNEL, 'AG') = 'AG'
	/*END*/
	/*IF searchDto.channel != null && searchDto.channel == 'AG'*/
	AND isnull(TBL.CHANNEL, 'AG') = /*searchDto.channel*/
	/*END*/
	/*IF searchDto.channel != null && searchDto.channel == 'AD'*/
	AND TBL.CHANNEL = /*searchDto.channel*/
	/*END*/
order by tbl.sort ASC