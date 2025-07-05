WITH TBL_TMP AS (
	SELECT 
		1													AS	id
		, 'ROOT'											AS	CODE
		, 'ROOT'											AS	title
		, 1													AS	sort
		, 0													AS	PARENT_ID
		
	UNION

	SELECT 
		tbl.id
		, tbl.code
		, tblLang.title
		, tbl.SORT
		, TBL.PARENT_ID						AS	PARENT_ID
	FROM M_DOCUMENT_CATEGORY TBL WITH (NOLOCK)
	INNER JOIN M_DOCUMENT_CATEGORY_LANGUAGE tblLang WITH (NOLOCK)
		ON tblLang.M_CATEGORY_ID = TBL.ID
	WHERE
			TBL.DELETE_DATE IS NULL
		AND TBL.ENABLED = 1
		AND tblLang.M_LANGUAGE_CODE = UPPER(/*languageCode*/'VI')
		/*IF idIgnore != null*/
		AND tbl.ID <> /*idIgnore*/8
		/*END*/
		/*IF channel == null || channel == ''*/
		AND isnull(TBL.CHANNEL, 'AG') = 'AG'
		/*END*/
		/*IF channel != null && channel == 'AG'*/
		AND isnull(TBL.CHANNEL, 'AG') = /*channel*/
		/*END*/
		/*IF channel != null && channel == 'AD'*/
		AND TBL.CHANNEL = /*channel*/
		/*END*/
)
SELECT * FROM TBL_TMP tbl
INNER JOIN M_PARENT_PATH path
	ON path.DESCENDANT_ID = tbl.ID
	AND path.TYPE = 'M_DOCUMENT_CATEGORY'
	AND path.depth = 1
ORDER BY tbl.PARENT_ID, tbl.SORT