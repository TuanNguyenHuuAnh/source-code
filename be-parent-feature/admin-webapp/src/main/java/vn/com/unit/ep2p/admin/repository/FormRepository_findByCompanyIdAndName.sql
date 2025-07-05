SELECT
	*
FROM EFO_FORM
WHERE DELETED_ID = 0
/*IF companyId != null*/
	AND COMPANY_ID = /*companyId*/1
/*END*/ 
/*IF name != null && name != ''*/
	AND UPPER(NAME) = UPPER(/*formName*/'')
/*END*/
/*IF currentFormId != null*/
	AND ID <> /*currentFormId*/1
/*END*/ 