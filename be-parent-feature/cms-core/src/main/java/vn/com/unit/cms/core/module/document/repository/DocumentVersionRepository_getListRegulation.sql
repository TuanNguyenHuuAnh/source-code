

WITH TBL_DATA AS (
SELECT 
		tbl.id													AS id
		, tbl.code 												AS code
		, tblLang.title 										AS title
		, category.title 										AS category_name
		, detail.physical_file_name							    AS physical_file_name
		, detail.FILE_TYPE										AS FILE_TYPE
		, detail.VERSION										AS version
		, sta.status_name										AS status_name
		, sta.status_code										AS status_code
		, tbl.enabled											as enabled
		, tbl.posted_date										AS posted_date
		, tbl.expiration_date									AS expiration_date
		, tbl.create_date 										AS create_date
		, tbl.create_by											AS create_by
		, tbl.update_date 										AS update_date
		, tbl.update_by											AS update_by
		, TBL.SORT												AS SORT
		, cate.CODE												AS CATEGORY_CODE
		, cate.CATEGORY_TYPE									as CATEGORY_TYPE
		
	FROM M_DOCUMENT tbl WITH (NOLOCK)
	INNER JOIN M_DOCUMENT_language tblLang WITH (NOLOCK)
		ON tbl.id = tblLang.M_DOCUMENT_id

	left join M_DOCUMENT_DETAIL detail
	on detail.m_document_id = tbl.id
	--AND detail.version_current =1

	LEFT JOIN M_DOCUMENT_CATEGORY_LANGUAGE category WITH (NOLOCK)
		ON category.M_CATEGORY_ID = tbl.M_CATEGORY_ID 
			AND category.m_language_code = tblLang.m_language_code
			AND category.delete_date is null
			AND category.KEYWORDS_SEO ='quy-dinh-tham-du-hoc-va-thi'
	INNER JOIN M_DOCUMENT_CATEGORY cate WITH (NOLOCK)
		ON cate.id = category.M_CATEGORY_ID
	LEFT JOIN VW_APPROVED approved
		ON approved.DOC_ID = tbl.DOC_ID
	OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](tbl.DOC_ID, /*languageCode*/'VI') STA
	WHERE UPPER(tblLang.m_language_code) = /*languageCode*/'VI'
	AND STA.STATUS_CODE = '999'
	AND tbl.ENABLED = 1
)
SELECT *
FROM TBL_DATA TBL
WHERE
	1 = 1
	AND tbl.CATEGORY_TYPE = 'LBR2113.0001'
	AND tbl.posted_date <= GETDATE()
	AND ISNULL(tbl.expiration_date, DATEADD(day, 1, GETDATE())) >= GETDATE()
ORDER BY ISNULL(tbl.update_date, tbl.create_date) DESC, tbl.ENABLED DESC


