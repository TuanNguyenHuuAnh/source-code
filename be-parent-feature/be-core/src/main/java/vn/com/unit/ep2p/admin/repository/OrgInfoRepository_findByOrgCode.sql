SELECT
	*
FROM JCA_ORGANIZATION
WHERE DELETED_ID = 0
	AND ORG_CODE = /*orgCode*/''
	/*IF companyId!=null && companyId!=''*/
	AND COMPANY_ID = /*companyId*/
	/*END*/