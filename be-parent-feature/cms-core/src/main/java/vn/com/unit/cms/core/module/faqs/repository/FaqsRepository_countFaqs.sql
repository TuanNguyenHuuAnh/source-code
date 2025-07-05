WITH TBL_DATA AS (
	SELECT 
		tbl.id																		AS id
		, tbl.code 																	AS code
		, tblLang.title 															AS title
	    , category.title 															AS category_name
		, sta.status_name															AS status_name
		, sta.status_code															AS status_code
	    , tbl.enabled																AS enabled
	    , tbl.create_date 															AS create_date
	    , tbl.create_by																AS create_by
	    , tbl.update_date 															AS update_date
	    , tbl.update_by																AS update_by
	    , tblLang.content															AS content
	    , tbl.sort																	AS sort
	    , tbl.posted_date															AS posted_date
	    , tbl.expiration_date														AS expiration_date
	FROM m_faqs tbl WITH (NOLOCK)
	INNER JOIN m_faqs_language tblLang WITH (NOLOCK)
		ON tbl.id = tblLang.m_faqs_id
		
	LEFT JOIN m_faqs_category_language category WITH (NOLOCK)
		ON category.m_faqs_category_id = tbl.m_faqs_category_id 
			AND category.m_language_code = tblLang.m_language_code
			AND category.delete_date is null
			
	LEFT JOIN VW_PENDING_AT pending
		ON pending.DOC_ID = tbl.DOC_ID
		
	LEFT JOIN VW_APPROVED approved
		ON approved.DOC_ID = tbl.DOC_ID
		
	OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](tbl.DOC_ID, /*searchDto.languageCode*/'VI') STA
		
	WHERE
		tbl.delete_date is null
		AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
		
)
SELECT COUNT(*)
FROM TBL_DATA TBL
WHERE
	1 = 1
	
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/
	)
	/*END*/