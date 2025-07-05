WITH TBL_TMP AS (
	SELECT 
		1													AS	id
		, 'ROOT'											AS	CODE
		, 'ROOT'											AS	title
		, 1													AS	sort
		, 0													AS	faqs_category_parent_id
		
	UNION

	SELECT 
		tbl.id
		, tbl.code
		, tblLang.title
		, tbl.SORT
		, TBL.M_FAQS_CATEROTY_PARENT_ID						AS	faqs_category_parent_id
	FROM M_FAQS_CATEGORY TBL WITH (NOLOCK)
	INNER JOIN M_FAQS_CATEGORY_LANGUAGE tblLang WITH (NOLOCK)
		ON tblLang.M_FAQS_CATEGORY_ID = TBL.ID
	WHERE
			TBL.DELETE_DATE IS NULL
		AND TBL.ENABLED = 1
		AND tblLang.M_LANGUAGE_CODE = UPPER(/*languageCode*/'VI')
)
SELECT * FROM TBL_TMP tbl
INNER JOIN M_PARENT_PATH path
	ON path.DESCENDANT_ID = tbl.ID
	AND path.TYPE = 'M_FAQS_CATEGORY'
	AND path.depth = 1
ORDER BY tbl.faqs_category_parent_id, tbl.SORT