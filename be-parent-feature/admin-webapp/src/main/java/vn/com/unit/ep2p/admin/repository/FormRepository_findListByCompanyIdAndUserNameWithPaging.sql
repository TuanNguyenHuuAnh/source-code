SELECT DISTINCT
    frm.ID AS id,
    CASE WHEN formLang.FORM_NAME IS NULL THEN frm.NAME ELSE formLang.FORM_NAME END AS name,
    CASE WHEN formLang.FORM_NAME IS NULL THEN frm.NAME ELSE formLang.FORM_NAME END AS text,
    formLang.FORM_NAME 
FROM
    EFO_FORM frm
LEFT JOIN 
	EFO_FORM_LANG formLang
ON
	frm.ID = formLang.FORM_ID
	AND UPPER(formLang.LANG_CODE) = UPPER(/*lang*/'')
LEFT JOIN 
	VW_AUTHORITY_DETAIL autho 
ON 
	autho.FUNCTION_CODE = frm.FUNCTION_CODE 
	AND frm.COMPANY_ID = autho.COMPANY_ID 
	AND autho.FUNCTION_TYPE = 3
	AND autho.ACCESS_RIGHT = 'ACCESS'
	AND autho.USERNAME = /*userName*/
	AND autho.COMPANY_ID = /*companyId*/1
LEFT JOIN  
	JPM_BUSINESS business 
ON 
	business.ID = frm.JPM_BUSINESS_ID
WHERE 
    frm.DELETED_ID = 0 
    AND frm.ACTIVED = 1 
    AND frm.DEVICE_TYPE != 'pc'
	AND frm.COMPANY_ID = /*companyId*/1
	/*IF keySearch != null && keySearch != ''*/
	AND (UPPER(formLang.FORM_NAME) LIKE concat('%',  concat(UPPER(/*keySearch*/''), '%'))
		OR UPPER(frm.NAME) LIKE concat('%',  concat(UPPER(/*keySearch*/''), '%'))
	)
	/*END*/
    /*IF processTypeIgnores != null*/
	AND business.PROCESS_TYPE NOT IN /*processTypeIgnores*/('INTEGRATE')
	/*END*/ 
	ORDER BY frm.ID, formLang.FORM_NAME 
/*IF isPaging != 2*/
OFFSET /*page*/ ROWS FETCH NEXT /*pageSize*/ ROWS ONLY
/*END*/