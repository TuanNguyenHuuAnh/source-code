SELECT 
	tbl.id													AS faqs_id
	, tbl.code 												AS code
	, tblLang.title 										AS title
    , category.title 										AS category_name
    , ISNULL(sta.status_name, (SELECT STATUS_NAME FROM JPM_STATUS_COMMON_LANG WHERE STATUS_COMMON_ID = '1000' AND LANG_CODE = /*searchDto.languageCode*/'VI')) AS status_name
    , ISNULL(sta.status_code, '000')                        AS status_code
  	--, ISNULL(statusLang.STATUS_NAME, CLANG.STATUS_NAME)   AS status_name
    , tbl.enabled											as enabled
    , tbl.create_date 										AS create_date
    , tbl.create_by											AS create_by
    , tbl.update_date 										AS update_date
    , tbl.update_by											AS update_by
    , tblLang.content										AS content
    --, C.STATUS_CODE										AS status_code
    ,categoryFAQ.ITEM_FUNCTION_CODE                         AS item_function_code
FROM m_faqs tbl
INNER JOIN m_faqs_language tblLang 
	ON tbl.id = tblLang.m_faqs_id
LEFT JOIN m_faqs_category_language category 
	ON category.m_faqs_category_id = tbl.m_faqs_category_id 
		AND category.m_language_code = tblLang.m_language_code
		AND category.delete_date is null
LEFT JOIN m_faqs_category categoryFAQ
    ON categoryFAQ.id = tbl.m_faqs_category_id 
LEFT JOIN JPM_TASK TASK WITH (NOLOCK)
    ON TASK.DOC_ID = tbl.DOC_ID
LEFT JOIN JPM_STEP_DEPLOY STEP WITH (NOLOCK)
    ON STEP.ID = TASK.STEP_DEPLOY_ID
LEFT JOIN JPM_STATUS_LANG_DEPLOY statusLang
	ON statusLang.STATUS_DEPLOY_ID = STEP.STATUS_DEPLOY_ID
	AND statusLang.LANG_CODE = tblLang.m_language_code
LEFT JOIN JPM_PROCESS_INST_ACT ACT WITH (NOLOCK)
    ON ACT.REFERENCE_ID = tbl.DOC_ID
LEFT JOIN JPM_STATUS_COMMON_LANG CLANG WITH (NOLOCK)
   ON CLANG.STATUS_COMMON_ID = ACT.COMMON_STATUS_ID
   AND CLANG.LANG_CODE = tblLang.m_language_code
LEFT JOIN JPM_STATUS_COMMON C WITH (NOLOCK)
    ON C.ID = ACT.COMMON_STATUS_ID
LEFT JOIN VW_PENDING_AT pending
	ON pending.DOC_ID = tbl.DOC_ID
LEFT JOIN VW_APPROVED approved
	ON approved.DOC_ID = tbl.DOC_ID
OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](tbl.DOC_ID, /*searchDto.languageCode*/'VI') STA
WHERE
	tbl.delete_date is null
	AND tbl.ENABLED = 1
    AND c.STATUS_CODE = 999



  AND
	(
		tbl.EXPIRATION_DATE IS NULL
		OR tbl.EXPIRATION_DATE >= CONVERT(DATE, GETDATE() ,103)
	)
	AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
		
	/*IF searchDto.categoryId != null*/
	AND tbl.m_faqs_category_id =  /*searchDto.categoryId*/1
	/*END*/
ORDER BY tbl.SORT ASC