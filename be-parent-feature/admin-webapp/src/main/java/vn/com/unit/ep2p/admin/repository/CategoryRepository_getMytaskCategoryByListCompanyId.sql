SELECT distinct
    ITEM.ID AS category_id
  ,ITEM.NAME AS category_name
  
FROM
	ACT_RU_TASK res
INNER JOIN
	JPM_TASK task
ON
	res.ID_ = task.ACT_TASK_ID
LEFT JOIN
	ACT_RU_IDENTITYLINK iLink
ON
	iLink.TASK_ID_ = res.ID_
LEFT JOIN
	EFO_OZ_DOC doc
ON
	task.BUSINESS_KEY = doc.DOC_UUID
LEFT JOIN 
	EFO_FORM form
ON
	form.ID = doc.FORM_ID	
LEFT JOIN
	JPM_BUSINESS business
ON
	doc.BUSINESS_ID = business.ID
LEFT JOIN
	JPM_PROCESS_DEPLOY process
ON
	doc.PROCESS_DEPLOY_ID = process.ID
LEFT JOIN
	JCA_CONSTANT constPriority
ON
	doc.PRIORITY = constPriority.note
	AND constPriority.group_code = 'PRIORITY'
LEFT JOIN
	JPM_STATUS_LANG_DEPLOY status
ON
	doc.PROCESS_STATUS_CODE = status.STATUS_ID
	AND UPPER(status.LANG_CODE) = UPPER(/*languageCode*/'')
LEFT JOIN
	JCA_ACCOUNT account
ON
	account.ID = task.SUBMITTER_ID
LEFT JOIN
	jca_company company
ON
	task.COMPANY_ID = company.ID
LEFT JOIN EFO_CATEGORY ITEM
ON 
	form.CATEGORY_ID = ITEM.ID 
LEFT JOIN EFO_CATEGORY_LANG LANG ON ITEM.ID = LANG.CATEGORY_ID AND UPPER(LANG.LANG_CODE) = UPPER(/*languageCode*/'')
WHERE
	form.DELETED_ID = 0
	AND task.DELETED_ID = 0

	AND task.DOC_TYPE = 'EFO_OZ_DOC'
	AND res.ASSIGNEE_ =  /*userId*/
	OR (
		res.ASSIGNEE_ is null
		AND iLink.TYPE_ = 'candidate' 
		AND 
		(
				iLink.USER_ID_ = /*userId*/
				OR iLink.GROUP_ID_ IN /*groupIdList*/()
		)
	)

	AND task.COMPANY_ID = /*companyId*/
ORDER BY ITEM.ID
	