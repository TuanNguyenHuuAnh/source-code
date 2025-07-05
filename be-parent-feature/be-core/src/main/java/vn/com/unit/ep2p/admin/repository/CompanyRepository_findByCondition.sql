SELECT
	*
FROM jca_company
WHERE DELETED_ID = 0
/*IF companyName != null && companyName != ''*/
	AND UPPER(NAME) = UPPER(/*companyName*/'')
/*END*/
/*IF systemCode != null && systemCode != ''*/
	AND UPPER(SYSTEM_CODE) = UPPER(/*systemCode*/'')
/*END*/
/*IF excludeId != null*/
	AND ID <> /*excludeId*/1
/*END*/