SELECT DISTINCT
	form.ID					AS SERVICE_ID
	,CASE WHEN  
		formLang.FORM_NAME 	IS NULL THEN form.NAME ELSE formLang.FORM_NAME END	AS SERVICE_NAME
	,form.FORM_TYPE			AS SERVICE_TYPE
	,form.FILE_NAME			AS PATH_FILE
	,form.IMAGE 			as image_path
	,form.REPOSITORY_ID 	as image_repo_id 
	,form.JPM_BUSINESS_ID	AS business_process_id
	,business.NAME			AS business_process_name
	,form.DISPLAY_ORDER		AS DISPLAY_ORDER
	,business.PROCESS_TYPE AS PROCESS_TYPE
FROM
	EFO_FORM form
LEFT JOIN 
	EFO_FORM_LANG formLang 
ON 
	form.ID = formLang.FORM_ID AND UPPER(formLang.LANG_CODE) = UPPER(/*langCode*/'')
LEFT JOIN 
	jca_company company
ON	
	company.ID = form.COMPANY_ID
LEFT JOIN EFO_CATEGORY	ITEM
ON	
	ITEM.ID = form.CATEGORY_ID
LEFT JOIN 
	JPM_BUSINESS business
ON
	business.ID = form.JPM_BUSINESS_ID
INNER JOIN VW_GET_AUTHORITY_ACCOUNT role
ON
	role.FUNCTION_CODE = form.FUNCTION_CODE
WHERE
	form.DELETED_ID = 0
	AND form.DEVICE_TYPE != 'pc'
	/*IF companyId != null*/
	AND form.COMPANY_ID = /*companyId*/
	/*END*/
	/*IF categoryId !=null*/
	AND form.CATEGORY_ID = /*categoryId*/
	/*END*/
	AND
		role.ACCOUNT_ID = /*accountId*/
	AND
		ACCESS_FLG = 0
	/*IF processTypeIgnores != NULL && processTypeIgnores.size()>0*/
		AND business.PROCESS_TYPE NOT IN /*processTypeIgnores*/()
	/*END*/
/*IF categoryId == 0*/
UNION
(
SELECT
	0 											AS service_id,
    constDisplayLang.CAT_ABBR_NAME 				AS service_name
    ,null 										as path_file
	,null 										as image_path
	,null 										as image_repo_id
	,business.ID								AS business_process_id
	,business.NAME								AS business_process_name
	,null										AS DISPLAY_ORDER
    
    FROM 
    	JCA_CONSTANT constDisplay
	LEFT JOIN
		JPM_BUSINESS	business
	ON
		business.ID = 1
	WHERE 
		constDisplay.K = 'J_BUS_SERVICE_001'
		AND UPPER(constDisplayLang.LANG_CODE) = UPPER(/*langCode*/'EN')		
OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY
)
/*END*/
ORDER BY DISPLAY_ORDER
	,service_name
/*IF pageSize!=null*/
		OFFSET /*page*/ ROWS FETCH NEXT  /*pageSize*/ ROWS ONLY
/*END*/