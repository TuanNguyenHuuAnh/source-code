UPDATE jca_account_org
SET ORG_ID = /*orgId*/
	,ORG_CODE = null
WHERE ORG_CODE = /*orgCode*/
	AND COMPANY_ID = /*companyId*/