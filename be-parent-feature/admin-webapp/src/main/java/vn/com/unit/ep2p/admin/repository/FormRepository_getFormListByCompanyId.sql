SELECT
    FR.ID 				AS ID,
    CASE WHEN formLang.FORM_NAME IS NULL THEN FR.NAME ELSE formLang.FORM_NAME END AS NAME,
    CASE WHEN formLang.FORM_NAME IS NULL THEN FR.NAME ELSE formLang.FORM_NAME END AS TEXT
FROM
    EFO_FORM FR
LEFT JOIN 
	EFO_FORM_LANG formLang
ON
	FR.ID = formLang.FORM_ID
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
	AU.FUNCTION_CODE = FR.FUNCTION_CODE
WHERE 
	FR.DELETED_ID = 0
/*IF companyId != null*/
	AND FR.COMPANY_ID = /*companyId*/1
/*END*/
/*IF keySearch != null && keySearch != ''*/
	AND (FN_CONVERT_TO_VN(UPPER(formLang.FORM_NAME)) LIKE concat('%',  concat(FN_CONVERT_TO_VN(UPPER(/*keySearch*/'')), '%'))
	OR FN_CONVERT_TO_VN(UPPER(FR.NAME)) LIKE concat('%',  concat(FN_CONVERT_TO_VN(UPPER(/*keySearch*/'')), '%'))
	)
/*END*/
ORDER BY 
	NAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/