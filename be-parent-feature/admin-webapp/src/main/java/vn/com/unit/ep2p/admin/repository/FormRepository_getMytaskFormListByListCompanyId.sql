SELECT DISTINCT



	company.NAME 			AS company_name
	,company.ID				AS company_id
	,company.SYSTEM_CODE
	,company.SYSTEM_NAME
	,company.LOGO_MINI_REPO_ID	AS logo_repo_id
	,company.LOGO_MINI			AS	logo_path
	
	,ITEM.ID 				AS category_id
	
FROM 
	FN_TASK_TODO(/*accountId*/1802,null) task
LEFT JOIN
	EFO_OZ_DOC doc
ON 
	task.BUSINESS_KEY = doc.DOC_UUID 
LEFT JOIN 
	EFO_FORM form
ON
	form.ID = doc.FORM_ID	
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
	AND doc.DELETED_ID = 0
  AND task.COMPANY_ID IN /*companyList*/()
ORDER BY company.ID	
 /*IF pageSize!=null*/
		OFFSET /*page*/ ROWS FETCH NEXT  /*pageSize*/ ROWS ONLY
/*END*/

 