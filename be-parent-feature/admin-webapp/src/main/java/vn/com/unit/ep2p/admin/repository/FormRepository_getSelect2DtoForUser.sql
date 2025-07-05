SELECT
    form.ID 				AS ID,
    CASE WHEN formLang.FORM_NAME IS NULL THEN form.NAME ELSE formLang.FORM_NAME END AS NAME,
    CASE WHEN formLang.FORM_NAME IS NULL THEN form.NAME ELSE formLang.FORM_NAME END AS TEXT
FROM
    EFO_FORM form
LEFT JOIN 
	EFO_FORM_LANG formLang
ON
	form.ID = formLang.FORM_ID
	AND UPPER(formLang.LANG_CODE) = UPPER(/*lang*/'')
INNER JOIN 
(	SELECT DISTINCT 
		FUNCTION_CODE 
	FROM 
		VW_GET_AUTHORITY_ACCOUNT 
	WHERE 
		ACCOUNT_ID = /*accountId*/1 
		AND access_flg = 0
) AU 
ON 
	AU.FUNCTION_CODE = form.FUNCTION_CODE
LEFT JOIN JPM_BUSINESS business 
ON 
	business.ID = form.JPM_BUSINESS_ID AND business.DELETED_ID = 0 
WHERE 
	form.DELETED_ID = 0
/*IF companyId != null*/
	AND form.COMPANY_ID = /*companyId*/1
/*END*/
/*IF keySearch != null && keySearch != ''*/
	AND (FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(formLang.FORM_NAME)) LIKE concat('%',  concat(FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(/*keySearch*/'')), '%'))
	OR FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(form.NAME)) LIKE concat('%',  concat(FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(/*keySearch*/'')), '%'))
	)
/*END*/
/*IF processTypeIgnores != null && processTypeIgnores.size() > 0*/
	AND business.PROCESS_TYPE NOT IN /*processTypeIgnores*/('INTEGRATE')
/*END*/ 
ORDER BY 
	NAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/