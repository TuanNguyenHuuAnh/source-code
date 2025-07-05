SELECT 
	tbl.id													AS id
	, tbl.code 												AS code
	, tblLang.title 										AS title
    , tbl.MEMO_CODE 										AS memo_code
  	, ISNULL(statusLang.STATUS_NAME, CLANG.STATUS_NAME)		AS status_name
    , tbl.enabled											as enabled
    , tbl.start_date										AS start_date
    , tbl.end_date											AS end_date
    , tbl.hot												AS hot
    , tbl.create_date 										AS create_date
    , tbl.create_by											AS create_by
    , tbl.update_date 										AS update_date
    , tbl.update_by											AS update_by
    , tblLang.content										AS content
FROM m_emulate tbl
INNER JOIN m_emulate_language tblLang 
	ON tbl.id = tblLang.m_emulate_id
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
WHERE
	tbl.delete_date is null
	AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
	
	/*IF searchDto.username != null && searchDto.username != ''*/
		AND (tbl.CREATE_BY = /*searchDto.username*/''
			OR CHARINDEX(CONCAT(', ', /*searchDto.username*/'', ', ') , CONCAT(', ', approved.USERNAME_PENDING, ', ') ) > 0 
			OR CHARINDEX(CONCAT(', ', /*searchDto.username*/'', ', ') , CONCAT(', ', pending.USERNAME_PENDING, ', ') ) > 0 
		)
	/*END*/
		
	AND tbl.TYPE =  /*searchDto.type*/''
ORDER BY tbl.SORT ASC