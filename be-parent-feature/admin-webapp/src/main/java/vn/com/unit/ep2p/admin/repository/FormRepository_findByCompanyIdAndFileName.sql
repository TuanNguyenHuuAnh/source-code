SELECT
	*
FROM EFO_FORM
WHERE DELETED_ID = 0
/*IF companyId != null*/
	AND COMPANY_ID = /*companyId*/1
/*END*/
/*IF fileNameList != null && fileNameList.size() > 0*/
	AND UPPER(OZ_FILE_PATH) IN /*fileNameList*/()
/*END*/