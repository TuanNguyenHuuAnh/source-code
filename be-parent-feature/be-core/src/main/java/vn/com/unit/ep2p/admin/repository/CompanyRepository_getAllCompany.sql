SELECT
	company.SYSTEM_CODE 	AS SYSTEM_CODE
	,company.NAME			AS COMPANY_NAME
FROM jca_company company
WHERE 
	DELETED_ID = 0
	/*IF limitCompany != null*/
		AND company.ID > /*limitCompany*/
	/*END*/
ORDER BY company.id