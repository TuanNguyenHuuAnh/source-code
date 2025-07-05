SELECT DISTINCT 
	COUNT (*)
FROM (SELECT DISTINCT form.ID
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
		
	/*IF processTypeIgnores != NULL && processTypeIgnores.size()>0*/
		AND business.PROCESS_TYPE NOT IN /*processTypeIgnores*/()
	/*END*/
)