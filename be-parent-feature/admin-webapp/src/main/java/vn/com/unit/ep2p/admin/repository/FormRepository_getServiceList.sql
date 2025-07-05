SELECT
	FR.ID 							as service_id
	,FR.NAME 						as service_name
	,FR.FILE_NAME 					as path_file
	,FR.IMAGE 						as image_path
	,FR.REPOSITORY_ID 				as image_repo_id
	,FR.JPM_BUSINESS_ID				AS business_process_id
	,business.NAME					AS business_process_name
	,FR.DISPLAY_ORDER				AS 	DISPLAY_ORDER
FROM
    EFO_FORM FR
LEFT JOIN 
	JPM_BUSINESS business
ON
	business.ID = FR.JPM_BUSINESS_ID
INNER JOIN 
(	SELECT DISTINCT 
		FUNCTION_CODE 
	FROM 
		VW_GET_AUTHORITY_ACCOUNT 
	WHERE 
		ACCOUNT_ID =  /*accountId*/
		AND access_flg = 0
) AU 
ON 
	AU.FUNCTION_CODE = FR.FUNCTION_CODE
WHERE 
	FR.DELETED_ID = 0
	AND FR.ACTIVED = 1
	/*IF categoryId !=null*/	
	AND	FR.CATEGORY_ID = /*categoryId*/
	/*END*/
	/*IF companyId !=null*/
	AND	FR.COMPANY_ID = /*companyId*/
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