SELECT  
	form.COMPANY_ID 			AS company_id
	,company.NAME 				AS company_name
	,company.SYSTEM_CODE
	,company.SYSTEM_NAME
	,company.LOGO_MINI_REPO_ID	AS logo_repo_id
	,company.LOGO_MINI			AS	logo_path
	,form.CATEGORY_ID			as category_id
FROM
	EFO_FORM form
LEFT JOIN 
	jca_company company
ON	company.ID = form.COMPANY_ID
LEFT JOIN EFO_CATEGORY	ITEM
ON	ITEM.ID = form.CATEGORY_ID
LEFT JOIN 
	JPM_BUSINESS business
ON
	business.ID = form.JPM_BUSINESS_ID
INNER JOIN VW_GET_AUTHORITY_ACCOUNT role
ON
	role.FUNCTION_CODE = form.FUNCTION_CODE
WHERE 
	form.DELETED_ID = 0
	AND 
		form.COMPANY_ID IN /*companyIdList*/()
	AND
		role.ACCOUNT_ID = /*accountId*/
	AND
		ACCESS_FLG = 0
	/*IF keySearch != null*/
	AND	(
			UPPER(ITEM.NAME) LIKE CONCAT('%',CONCAT(UPPER(/*keySearch*/''),'%'))
		)
	/*END*/
	/*IF processTypeIgnores != NULL && processTypeIgnores.size()>0*/
		AND business.PROCESS_TYPE NOT IN /*processTypeIgnores*/()
	/*END*/
ORDER BY form.COMPANY_ID,ITEM.ID
/*IF pageSize!=null*/
		OFFSET /*page*/ ROWS FETCH NEXT  /*pageSize*/ ROWS ONLY
/*END*/